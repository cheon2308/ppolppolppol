import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:line_awesome_flutter/line_awesome_flutter.dart';
import 'package:ppol/constant/auth_dio.dart';
import 'package:ppol/constant/constants.dart';
import 'package:ppol/login/homePage.dart';
import 'package:http/http.dart' as http;

class myPage extends StatefulWidget {
  const myPage({Key? key}) : super(key: key);

  @override
  State<myPage> createState() => _myPageState();
}

class _myPageState extends State<myPage> {


  var username="";
  var userimage="";

  @override
  void initState() {
    sendGetRequestWithAuthorization();
    super.initState();
  }

  Future<void> sendGetRequestWithAuthorization() async {
    try{
      Dio dio= await authDio(context);
      final response = await dio.get("/user-service/users");
      // 응답 처리

      if (response.statusCode == 200) {
        // 성공적인 응답 처리
        print('GET 요청 성공');
        print('응답 본문;;;: ${response.data}');
        var responseData = (response.data);

        setState(() {
          username = responseData['data']['username'];
          userimage = responseData['data']['image'];
        });

        // print("username 뭘까용 ? :  ${username}");
        // print("userimage 뭘까용 ? :  ${userimage}");
      } else {
        // 실패한 응답 처리
        print('GET 요청 실패');
        print('응답 상태 코드: ${response.statusCode}');
      }


    }
    catch(error){

    }

    // 요청 URL 설정
    final url = Uri.parse('http://k8e106.p.ssafy.io:8000/user-service/users');

    // GET 요청 보낼 때 Authorization 헤더에 값을 추가
    final response = await http.get(
      url,
      headers: {'Authorization': '1'}, //내 토큰 넣기
    );

  }



  @override
  Widget build(BuildContext context) {

    var header = Row(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        SizedBox(width: kSpacingUnit.w * 3),
        IconButton(
            onPressed: () {
              Navigator.pop(context);
            },
            icon: Icon(LineAwesomeIcons.arrow_left)),
        // profileInfo,
        // themeSwitcher,
        SizedBox(width: kSpacingUnit.w * 3),
      ],
    );
    return Profile(username: username,userimage: userimage,);

    // return Scaffold(
    //   body: Column(
    //     children: [
    //       // SizedBox(height: kSpacingUnit.w * 5),
    //       // header,
    //       // Text(username),
    //       Profile(),
    //       // CustomScrollView(
    //       //   slivers: [
    //       //     SliverToBoxAdapter(child: ProfileHeader()),
    //       //     SliverGrid(
    //       //         delegate: SliverChildBuilderDelegate(
    //       //               (c,i) => Container(color : Colors.black),
    //       //           childCount: 3,
    //       //         ),
    //       //         gridDelegate: SliverGridDelegateWithFixedCrossAxisCount( crossAxisCount: 3 ))
    //       //   ],
    //       // )
    //     ],
    //   ),
    // );
  }


}


class Profile extends StatelessWidget {
  const Profile({Key? key, this.username, this.userimage}) : super(key: key);
  final userimage;
  final username;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: AppBar(title:
      // // Text(context.watch<Store2>().name)
      //   Text("야야야")
      // ),
      body: CustomScrollView(
        slivers : [
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
            title: Text(username),
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

            child: ProfileHeader(userimage: userimage,),
          ),
          SliverGrid(
            delegate: SliverChildBuilderDelegate(
                  (c, i) => Container(
                color: Colors.grey,
                margin: EdgeInsets.all(3),
              ),
              childCount: 30,
            ),
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 3),
          ),
        ],
      ),
    );
  }
}

class ProfileHeader extends StatelessWidget {
  const ProfileHeader({Key? key,this.userimage}) : super(key: key);
  final userimage;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 200,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          CircleAvatar(
            backgroundImage: NetworkImage(userimage),
            radius:50,
            // backgroundColor: Colors.grey,
          ),
          Text('팔로워 '
          // '${context.watch<Store2>().follower}'
              '1'
              '명'),
          ElevatedButton(onPressed: (){
            // context.read<Store2>().addFollower();
          }, child: Text('팔로우')),
          ElevatedButton(onPressed: (){
            // context.read<Store2>().getData();
          }, child: Text('사진가져오기'))
        ],
      ),
    );
  }
}
