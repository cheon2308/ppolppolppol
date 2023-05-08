import 'package:animated_theme_switcher/animated_theme_switcher.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:line_awesome_flutter/line_awesome_flutter.dart';
import 'package:ppol/login/constancts.dart';
import 'package:ppol/login/homePage.dart';
import 'package:ppol/login/loginPage.dart';
import 'package:ppol/widgets/profile_list_item.dart';

void main() {
  runApp(const MyApp());
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
            // home: homePage(),
            home: loginPage(),
          );
        },
      ),
    );
  }
}

