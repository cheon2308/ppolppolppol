import 'package:animated_theme_switcher/animated_theme_switcher.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:line_awesome_flutter/line_awesome_flutter.dart';
import 'package:ppol/login/constancts.dart';
import 'package:ppol/login/homePage.dart';
import 'package:ppol/widgets/profile_list_item.dart';

void main() {
  runApp(const MyApp()  )  ;
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});
  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    // return MaterialApp(
    //     debugShowCheckedModeBanner: false,
    //     // home:loginPage()
    //     home:ProfileScreen()
    // );
    return ThemeProvider(
      // initTheme: kDarkTheme,
      initTheme: kLightTheme,
      child: Builder(
        builder: (context) {
          return MaterialApp(
            debugShowCheckedModeBanner: false,
            theme: kLightTheme,
            home: homePage(),
          );
        },
      ),
    );
  }
}






class ProfileScreen extends StatefulWidget {
  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {

  CrossFadeState _crossFadeState = CrossFadeState.showSecond;
  changeState(){
    setState(() {
      if(_crossFadeState==CrossFadeState.showFirst){
        _crossFadeState=CrossFadeState.showSecond;
      }
      else{
        _crossFadeState=CrossFadeState.showFirst;
      }
    });
  }
  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
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
                  backgroundImage: AssetImage('assets/main_logo/ppol_logo.png'),
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
                        onPressed: (){
                          print("수정클릭");
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
            '찬희',
            // style: kTitleTextStyle,
            style: TextStyle(fontSize: 20),
          ),
          SizedBox(height: kSpacingUnit.w * 0.5),
          Text(
            'wndjf11@naver.com',
            // style: kCaptionTextStyle,
            style: TextStyle(fontSize: 20),

          ),
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
        IconButton(onPressed: (){Navigator.push(context,MaterialPageRoute(builder: (context) => homePage(),));}, icon: Icon(LineAwesomeIcons.arrow_left)),
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
                        text: '내정보',
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.history,
                        text: '기록',
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.question_circle,
                        text: '도움',
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.cog,
                        text: '설정',
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.user_plus,
                        text: '초대',
                      ),
                      ProfileListItem(
                        icon: LineAwesomeIcons.alternate_sign_out,
                        text: '로그아웃',
                        hasNavigation: false,
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