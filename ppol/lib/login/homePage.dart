import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter_speed_dial/flutter_speed_dial.dart';
import 'package:ppol/main.dart';

class homePage extends StatelessWidget {
  const homePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      floatingActionButtonLocation:FloatingActionButtonLocation.startFloat,
      floatingActionButtonAnimator: FloatingActionButtonAnimator.scaling,
      floatingActionButton:
      SpeedDial(
        backgroundColor: Colors.black,
        tooltip: "메뉴",
        direction: SpeedDialDirection.up,
        spaceBetweenChildren: Checkbox.width,
        animatedIcon: AnimatedIcons.home_menu,
        childrenButtonSize: Size(70,70),
        buttonSize: Size(60, 60),
        children: [
          // SpeedDialChild(
          //   child: Icon(Icons.menu_outlined),
          //   label: 'Menu',
          // ),
          SpeedDialChild(
            child: Icon(Icons.add),
            label: 'Upload',
            onTap: () => {print("하이1")},
          ),
          SpeedDialChild(
            child: Icon(Icons.message_outlined),
            label: 'Message',
            onTap: () => {print("하이2")},
          ),
          SpeedDialChild(
            child: Icon(Icons.account_circle),
            label: 'account',
            onTap: () => {
              // Navigator.pushNamed(context, '/profile')
              // print("프로필 나와라"),
            Navigator.push(
            context, MaterialPageRoute(builder: (c) => ProfileScreen())),
            },
          ),
        ]

      ),
    );
  }
}
