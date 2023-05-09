import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:line_awesome_flutter/line_awesome_flutter.dart';
import 'package:ppol/constant/auth_dio.dart';
import 'package:ppol/login/constancts.dart';
import 'package:ppol/login/homePage.dart';
import 'package:http/http.dart' as http;

class myInfo extends StatefulWidget {
  const myInfo({Key? key}) : super(key: key);

  @override
  State<myInfo> createState() => _myInfoState();
}

class _myInfoState extends State<myInfo> {


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
        ],
      ),
    );
  }
}
