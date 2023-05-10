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

    return Scaffold(
      body: Column(
        children: [
          SizedBox(height: kSpacingUnit.w * 5),
          header,
          Text(username),
          CustomScrollView(
            slivers: [
              SliverToBoxAdapter(child: ProfileHeader()),
              SliverGrid(
                  delegate: SliverChildBuilderDelegate(
                        (c,i) => Container(color : Colors.grey),
                    childCount: 3,
                  ),
                  gridDelegate: SliverGridDelegateWithFixedCrossAxisCount( crossAxisCount: 2 ))
            ],
          )
        ],
      ),
    );
  }


}

class Profile extends StatelessWidget {
  const Profile({Key? key, this.changeFollow,this.username}) : super(key: key);
  final changeFollow;
  final username;


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(username),),
      body: CustomScrollView(
        slivers: [
          SliverToBoxAdapter(
            child: ProfileHeader(),
          ),
          SliverGrid(delegate: SliverChildBuilderDelegate(
                (c, i) =>Image.network(context.watch<Store1>().profileImage[i]),
            childCount: context.watch<Store1>().profileImage.length,
          ),
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 2),
          )
        ],
      ),

    );
  }
}

class ProfileHeader extends StatelessWidget {
  const ProfileHeader({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: [
        CircleAvatar(radius: 20,backgroundColor: Colors.grey,),
        Text('팔로워 '
            // '${context.watch<Store1>().follower.toString()}'
        '1'

            '명',style: TextStyle(fontSize: 20),),
        ElevatedButton(onPressed: (){
          // context.read<Store1>().plusFoloower();
        }, child: Text(
            // context.watch<Store1>().follow
          "follow"
        )
        ),
        ElevatedButton(onPressed: (){
          // context.read<Store1>().getData();
        }, child: Text('사진가져오기')),
      ],
    );
  }
}
