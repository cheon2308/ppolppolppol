import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter_unity_widget/flutter_unity_widget.dart';
import 'package:ppol/screen/myPage/myPage.dart';

class gameScreen extends StatefulWidget {
  @override
  _gameScreenState createState() => _gameScreenState();
}

class _gameScreenState extends State<gameScreen> {static final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
UnityWidgetController? _unityWidgetController;
double _sliderValue = 0.0;
List<String> npcNames = ['zaza', 'hyohyo', '', '', ''];
List<String> albumNames = ['bookbook1', 'bookbook2', '', '', ''];

@override
void initState() {
  super.initState();
}

@override
Widget build(BuildContext context) {
  return MaterialApp(
    home: Scaffold(
      key: _scaffoldKey,
      body: Card(
        margin: const EdgeInsets.all(0),
        clipBehavior: Clip.antiAlias,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10.0),
        ),
        child: Stack(
          children: <Widget>[
            UnityWidget(
              onUnityCreated: onUnityCreated,
              onUnityMessage: onUnityMessage,
              onUnitySceneLoaded: onUnitySceneLoaded,
              fullscreen: false,
            ),
            Positioned(
              bottom: 20,
              left: 20,
              right: 20,
              child: Card(
                elevation: 10,
                child: Column(
                  children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.only(top: 20),
                      child: Text("json보내기:"),
                    ),
                    Slider(
                      onChanged: (value) {
                        setState(() {
                          _sliderValue = value;
                        });
                        SetJson();
                      },
                      value: _sliderValue,
                      min: 0,
                      max: 20,
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    ),
  );
}

// Communcation from Flutter to Unity
void SetJson() {
  _unityWidgetController?.postJsonMessage(
    'GameManager', // Object 이름
    // 'SetUp', // Object에 붙은 스크립트의 함수
    // 함수에 보낼 변수
    'setJson',
    {
      "roomType": 2,
      "isMyRoom": 1,
      "roomOwner": {"name": "민정", "meshType": 1, "color": 1, "faceType": 1},
      "player": {"name": "민정", "meshType": 2, "color": 5, "faceType": 6},
      "npc": [
        {"name": "ㅎㅎ", "meshType": 1, "color": 10, "faceType": 1},
        {"name": "진수zl존", "meshType": 2, "color": 8, "faceType": 5},
        {"name": "송정삼정", "meshType": 3, "color": 6, "faceType": 3},
        {"name": "ssafy", "meshType": 1, "color": 4, "faceType": 1},
        {"name": "dty", "meshType": 2, "color": 2, "faceType": 2}
      ],
      "albums": [
        {"title": "민정이의 하루", "color": 3},
        {"title": "여행 사진", "color": 2},
        {"title": "송정삼정탈출기", "color": 3},
        {"title": "배고픈 저녁", "color": 3},
        {"title": "ㅎㅎ", "color": 1}
      ]
    }, // 함수에 보낼 변수
  );
}

// Communication from Unity to Flutter
void onUnityMessage(message) {
  print('Received message from unity: ${message.toString()}');
  
  if(message.toString()=="BOOKS01"){
    print("앨범");
    // Navigator.push(context, MaterialPageRoute(builder: (context) => myPage(),));
  }
}

// Callback that connects the created controller to the unity controller
void onUnityCreated(controller) {
  this._unityWidgetController = controller;
}

// Communication from Unity when new scene is loaded to Flutter
void onUnitySceneLoaded(SceneLoaded? sceneInfo) {
  print('Received scene loaded from unity: ${sceneInfo?.name}');
  print(
      'Received scene loaded from unity buildIndex: ${sceneInfo?.buildIndex}');
}
}
