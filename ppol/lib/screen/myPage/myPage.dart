import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:ppol/constant/auth_dio.dart';
import 'package:ppol/models/articleModel.dart';
import 'package:ppol/models/infoModel.dart';
import 'package:ppol/screen/articleDetailPage.dart';
import 'package:ppol/screen/articlePage.dart';
import 'package:ppol/screen/myPage/myPageArticlePage.dart';

class myPage extends StatefulWidget {
  final userId;

  const myPage({Key? key, required this.userId}) : super(key: key);

  @override
  State<myPage> createState() => _myPageState();
}

class _myPageState extends State<myPage> {
  late Future<InfoModel> _futureInfo;
  late Future<List<ArticleModel>> _futureArticles;

  @override
  void initState() {
    _futureInfo = getMyInfo();
    _futureArticles = fetchArticles(widget.userId);
    super.initState();
  }

  Future<List<ArticleModel>> fetchArticles(userId) async {
    // print("유저 아이디 : ${userId}");
    Dio dio = await authDio(context);
    final response = await dio.get("/article-service/articles/users/${userId}");
    if (response.statusCode == 200) {
      final dynamic responseData = (response.data);
      final List<dynamic> jsonList = responseData['data'];
      // final List<dynamic> jsonList = jsonDecode(response.body)['data'];
      final List<ArticleModel> articles =
          jsonList.map((json) => ArticleModel.fromJson(json)).toList();
      return articles;
    } else {
      throw Exception('Failed to load articles');
    }
  }

  Future<InfoModel> getMyInfo() async {
    try {
      Dio dio = await authDio(context);
      final response = await dio.get("/user-service/users");
      if (response.statusCode == 200) {
        // 성공적인 응답 처리
        print('GET 요청 성공');
        print('응답 본문;;;: ${response}');

        InfoModel info = InfoModel.fromJson(response.data);

        return info;
      } else {
        // 실패한 응답 처리
        print('GET 요청 실패');
        print('응답 상태 코드: ${response.statusCode}');
        throw Exception('Failed to load articles');
      }
    } catch (error) {
      print("뭐지 ${error}");
      throw Exception('Failed to load articles');
    }
  }

  @override
  Widget build(BuildContext context) {
    var profile = Scaffold(
      body: FutureBuilder(
          future: _futureArticles,
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              final articles = snapshot.data!;
              return FutureBuilder(
                  future: _futureInfo,
                  builder: (context, snapshot) {
                    if (snapshot.hasData) {
                      final Info = snapshot.data!;
                      return CustomScrollView(
                        shrinkWrap: true, // 높이를 자식 위젯 크기에 맞게 조정
                        slivers: [
                          SliverAppBar(
                            //SliverAppBar의 높이 설정
                            toolbarHeight: 50.0,
                            foregroundColor: Colors.black,
                            //SliverAppBar의 backgroundcolor
                            backgroundColor: Colors.white,
                            //하단 List를 최상단으로 올렸을때의 SliverAppBar의 Default height
                            //expandedHeight를 사용하면 스크롤을 내리면 toolbarheight높이까지 줄어든다.
                            expandedHeight: 50.0,
                            //SliverAppBar의 그림자 정도
                            elevation: 0.0,
                            //SliverAppBar title
                            title: Text("${Info.data?.username}"),
                            //SliverAppBar 영역을 고정시킨다. default false
                            pinned: true,
                            // AppBar가 하단 List 내렸을 때 바로 보여야 한다 -> true
                            // List를 최상단으로 올렸을 때만 나와야 한다. -> false
                            floating: true,
                            //title이 아니라 icon, image를 사용하여 flexible하게 조정하고 싶을땐 하단과 같이 코딩하면
                            //원하는 출력물 구현이 가능하다.
                            flexibleSpace: FlexibleSpaceBar(
                              titlePadding: EdgeInsetsDirectional.only(
                                top: 20,
                                bottom: 0,
                              ),
//make title centered.
                              centerTitle: true,
                              title: Column(
                                crossAxisAlignment: CrossAxisAlignment.center,
                                mainAxisAlignment: MainAxisAlignment.center,
                                mainAxisSize: MainAxisSize.min,
                                children: <Widget>[
                                  // Image.asset("assets/image/food.jpg",
                                  //   fit: BoxFit.fitWidth,
                                  //   height: AppBar().preferredSize.height * 0.5,
                                  //   width: 50,
                                  // ),
                                ],
                              ),
                            ),
                          ),
                          SliverToBoxAdapter(
                            child: SizedBox(
                              height: 200,
                              child: Column(
                                children: [
                                  Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceAround,
                                    children: [
                                      CircleAvatar(
                                        backgroundImage:
                                            NetworkImage("${Info.data?.image}"),
                                        radius: 50,
                                        // backgroundColor: Colors.grey,
                                      ),
                                      Column(
                                        mainAxisAlignment:
                                            MainAxisAlignment.center,
                                        children: [
                                          Text('팔로워'),
                                          Text("${Info.data?.followerCount}명")
                                        ],
                                      ),
                                      Column(
                                        mainAxisAlignment:
                                            MainAxisAlignment.center,
                                        children: [
                                          Text('팔로잉'),
                                          Text("${Info.data?.followingCount}명")
                                        ],
                                      ),

                                      ElevatedButton(
                                          onPressed: () {
                                            // context.read<Store2>().addFollower();
                                          },
                                          child: Text('팔로우')),
                                    ],
                                  ),
                                  Row(
                                    children: [
                                      Text("${Info.data?.intro}"),
                                    ],
                                  )
                                ],
                              ),
                            )
                            // (userimage: userimage, followerCount: followerCount,followingCount: followingCount,follow: follow,intro: intro,phone: phone)
                            ,
                          ),
                          SliverGrid(
                            delegate: SliverChildBuilderDelegate(
                              (c, i) => GestureDetector(
                                onTap: () {
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                      builder: (context) => ArticleDetailScreen(
                                          article: articles[i]),
                                    ),
                                  );
                                },
                                child: MyPageArticleCard(
                                    article: articles[i]),
                                //높이 : 380
                              ),
                              childCount: articles.length,
                            ),
                            gridDelegate:
                                SliverGridDelegateWithFixedCrossAxisCount(
                                    crossAxisCount: 2),
                          ),
                        ],
                      );
                    } else if (snapshot.hasError) {
                      return Center(
                        child: Text('${snapshot.error}'),
                      );
                    } else {
                      return Center(
                        child: CircularProgressIndicator(),
                      );
                    }
                  });
            } else if (snapshot.hasError) {
              return Center(
                child: Text('${snapshot.error}'),
              );
            } else {
              return Center(
                child: CircularProgressIndicator(),
              );
            }
          }),
    );

    // var header = Row(
    //   mainAxisAlignment: MainAxisAlignment.start,
    //   crossAxisAlignment: CrossAxisAlignment.start,
    //   children: <Widget>[
    //     SizedBox(width: kSpacingUnit.w * 3),
    //     IconButton(
    //         onPressed: () {
    //           Navigator.pop(context);
    //         },
    //         icon: Icon(LineAwesomeIcons.arrow_left)),
    //     // profileInfo,
    //     // themeSwitcher,
    //     SizedBox(width: kSpacingUnit.w * 3),
    //   ],
    // );
    return profile
        // (username: username,userimage: userimage,followingCount: followingCount,followerCount: followerCount,follow: follow,phone: phone,intro: intro)
        ;
  }
}

// class Profile extends StatelessWidget {
//   const Profile({Key? key, this.username, this.userimage,this.followerCount,this.followingCount,this.follow,this.intro,this.phone}) : super(key: key);
//   final userimage;
//   final username;
//   final followerCount;
//   final followingCount;
//   final follow;
//   final intro;
//   final phone;
//   @override
//   Widget build(BuildContext context) {
//     return
//   }
// }

// class ProfileHeader extends StatelessWidget {
//   const ProfileHeader({Key? key,this.userimage,this.followerCount,this.followingCount,this.follow,this.intro,this.phone}) : super(key: key);
//   final userimage;
//   final followerCount;
//   final followingCount;
//   final follow;
//   final intro;
//   final phone;
//
//   @override
//   Widget build(BuildContext context) {
//     return
//   }
// }
