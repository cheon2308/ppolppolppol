import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:flutter_speed_dial/flutter_speed_dial.dart';
import 'package:ppol/login/articleAdd.dart';
import 'package:ppol/login/profilePage.dart';
import 'package:ppol/main.dart';
import 'package:ppol/screen/gameScreen.dart';

import '../screen/articlePage.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class homePage extends StatelessWidget {
  const homePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      body: gameScreen(),
      backgroundColor: Colors.white,
      floatingActionButtonLocation: FloatingActionButtonLocation.endFloat,
      floatingActionButtonAnimator: FloatingActionButtonAnimator.scaling,
      floatingActionButton: SpeedDial(
          backgroundColor: Colors.black,
          tooltip: "메뉴",
          direction: SpeedDialDirection.up,
          spaceBetweenChildren: Checkbox.width,
          animatedIcon: AnimatedIcons.home_menu,
          childrenButtonSize: Size(70, 70),
          buttonSize: Size(60, 60),
          children: [
            SpeedDialChild(
              child: Icon(Icons.add),
              label: 'Upload',
              onTap: () => {Navigator.push(context, MaterialPageRoute(builder: (context) => articleAdd(),))},
            ),
            SpeedDialChild(
              child: Icon(Icons.message_outlined),
              label: 'Message',
              onTap: () => {print("하이2")},
            ),
            SpeedDialChild(
              child: Icon(Icons.add_alert_sharp),
              label: 'alrams',
              onTap: () => {print("하이2")},
            ),
            SpeedDialChild(
              child: Icon(Icons.article_sharp),
              label: 'article',
              onTap: () => {
                Navigator.push(
                    context, MaterialPageRoute(builder: (c) => ArticleScreen()))
              },
            ),
            SpeedDialChild(
              child: Icon(Icons.account_circle),
              label: 'account',
              onTap: () => {
                // Navigator.pushNamed(context, '/profile')
                // print("프로필 나와라"),
                Navigator.push(context,
                    MaterialPageRoute(builder: (c) => ProfileScreen())),
              },
            ),
          ]),
    );
  }
}
