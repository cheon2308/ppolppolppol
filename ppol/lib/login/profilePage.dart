import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/cupertino.dart';
import 'package:http_parser/http_parser.dart';
import 'package:dio/dio.dart';
import 'package:image_picker/image_picker.dart';

import 'package:animated_theme_switcher/animated_theme_switcher.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:line_awesome_flutter/line_awesome_flutter.dart';
import 'package:ppol/constant/auth_dio.dart';
import 'package:ppol/constant/constants.dart';
import 'package:ppol/screen/myPage/myPage.dart';
import 'package:ppol/widgets/profile_list_item.dart';
import 'package:http/http.dart' as http;

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  String username="";
  String userimage="";
  @override
  void initState() {
    super.initState();
    getMyInfo();
  }
  final ImagePicker _picker = ImagePicker();
  dynamic _pickImageError;

  Future<void> getMyInfo() async {

    // 요청 URL 설정
    Dio dio= await authDio(context);
    final response = await dio.get("/user-service/users");

    if (response.statusCode == 200) {
      print('GET 요청 성공');
      print('응답 본문: ${response.data}');
      final responseData = (response.data);
      print('Response data: $responseData');
      setState(() {
        username = responseData['data']['username'];
        userimage = responseData['data']['image'];
      });

      print("username 뭘까용 ? :  ${username}");
      print("userimage 뭘까용 ? :  ${userimage}");


    } else {
      // 실패한 응답 처리
      print('GET 요청 실패');
      print('응답 상태 코드: ${response.statusCode}');
    }
  }

  Future<void> myProfileImageChange() async {
      try {
        final XFile? pickedFile = await _picker.pickImage(source: ImageSource.gallery,);
        if (pickedFile != null) {
          FormData formData = FormData.fromMap({"image": await MultipartFile.fromFile(pickedFile.path, filename: "image",contentType: MediaType('image','*')),
          });
          Dio dio= await authDio(context);
          var response = await dio.put(
              '/user-service/users/profile-image',
              data: formData,
          );
          if(response.statusCode==200){
            print("수정 성공 : ${response.data}");
            setState(() {
              userimage=response.data['data']['image'];
            });
          }
          else{
            print("수정실패 ${response.statusCode}");
          }
        }
      } catch (e) {
        print("이미지 업로드 오류: $e");
        setState(() {
          _pickImageError = e;
        });
      }
  }
  CrossFadeState _crossFadeState = CrossFadeState.showSecond;
  changeState() {
    setState(() {
      if (_crossFadeState == CrossFadeState.showFirst) {
        _crossFadeState = CrossFadeState.showSecond;
      } else {
        _crossFadeState = CrossFadeState.showFirst;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(
      context,
      designSize: Size(414, 896),
    );

    var profileInfo = Expanded(
      child: Column(
        children: <Widget>[
          Container(
            height: kSpacingUnit.w * 10,
            width: kSpacingUnit.w * 10,
            margin: EdgeInsets.only(top: kSpacingUnit.w * 3),
            child: Stack(
              children: <Widget>[
                CircleAvatar(
                  radius: kSpacingUnit.w * 5,
                  // backgroundImage: AssetImage('assets/main_logo/ppol_logo.png'),
                  backgroundImage: NetworkImage(userimage.toString()),
                  backgroundColor: Colors.white,
                ),
                Align(
                  alignment: Alignment.bottomRight,
                  child: Container(
                    height: kSpacingUnit.w * 2.5,
                    width: kSpacingUnit.w * 2.5,
                    decoration: BoxDecoration(
                      color: Theme.of(context).colorScheme.secondary,
                      shape: BoxShape.circle,
                    ),
                    child: Center(
                      heightFactor: kSpacingUnit.w * 1.5,
                      widthFactor: kSpacingUnit.w * 1.5,
                      child: IconButton(
                        onPressed: () {
                          //프로필수정 호출
                          print("수정클릭");
                          myProfileImageChange();

                        },
                        icon: Icon(LineAwesomeIcons.pen),
                        // color: kDarkPrimaryColor,
                        color: kLightPrimaryColor,
                        iconSize: ScreenUtil().setSp(kSpacingUnit.r * 1.0),
                        alignment: AlignmentDirectional.center,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
          SizedBox(height: kSpacingUnit.w * 2),
          Text(
            username,
            // style: kTitleTextStyle,
            style: TextStyle(fontSize: 20),
          ),
          SizedBox(height: kSpacingUnit.w * 0.5),
          SizedBox(height: kSpacingUnit.w * 2),
        ],
      ),
    );

    var themeSwitcher = ThemeSwitcher(
      builder: (context) {
        return AnimatedCrossFade(
          duration: Duration(milliseconds: 200),
          crossFadeState: _crossFadeState,
          firstChild: GestureDetector(
            child: Icon(
              LineAwesomeIcons.sun,
              size: ScreenUtil().setSp(kSpacingUnit.w * 3),
              color: Colors.black.withOpacity(0),
            ),
          ),
          secondChild: GestureDetector(
            child: Icon(
              LineAwesomeIcons.moon,
              size: ScreenUtil().setSp(kSpacingUnit.w * 3),
              color: Colors.black.withOpacity(0),
            ),
          ),
        );
      },
    );

    var header = Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        SizedBox(width: kSpacingUnit.w * 3),
        IconButton(
            onPressed: () {
              Navigator.pop(context);
            },
            icon: Icon(LineAwesomeIcons.arrow_left)),
        profileInfo,
        themeSwitcher,
        SizedBox(width: kSpacingUnit.w * 3),
      ],
    );

    return ThemeSwitchingArea(
      child: Builder(
        builder: (context) {
          return Scaffold(
            body: Column(
              children: <Widget>[
                SizedBox(height: kSpacingUnit.w * 5),
                header,
                Expanded(
                  child: ListView(
                    children: <Widget>[
                      ProfileListItem(
                        icon: LineAwesomeIcons.user_shield,
                        text: '마이페이지',
                        fun: (){
                          print("내정보");
                          Navigator.push(context, CupertinoPageRoute(builder: (context) => myPage(),));
                        },
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.history,
                        text: '기록',
                        fun: (){
                          print("기록");
                        },
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.question_circle,
                        text: '도움',
                        fun: (){
                          print("도움");
                        },
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.cog,
                        text: '설정',
                        fun: (){
                          print("설정");
                        },
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.user_plus,
                        text: '초대',
                        fun: (){
                          print("초대");
                        },
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.alternate_sign_out,
                        text: '로그아웃',
                        hasNavigation: false,
                        fun: (){
                          // Navigator.pop(context);
                          // Navigator.push(context, MaterialPageRoute(builder: (context) => loginPage(),));
                          // Navigator.popUntil(context, (route) => true);
                          var count = 0;
                          Navigator.popUntil(context, (route) {
                            return count++ == 2;
                          });
                          // Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => loginPage(),));
                          print("로그아웃");

                        },
                      ),
                    ],
                  ),
                )
              ],
            ),
          );
        },
      ),
    );
  }
}