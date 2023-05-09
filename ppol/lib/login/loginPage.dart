import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:ppol/widgets/customInputField.dart';
import 'package:ppol/login/homePage.dart';
import 'package:ppol/login/registPage.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(MaterialApp(title: 'Login App', home: loginPage()));
}

class loginPage extends StatefulWidget {
  @override
  State<loginPage> createState() => _loginPageState();
}

class _loginPageState extends State<loginPage> {
  final String imageLogoName = "assets/main_logo/ppol_logo.png";

  // final main_Color=Color( 0xff000000);
  final main_Color = Color(0xffADD8E6);

  TextEditingController controller1 = TextEditingController();
  TextEditingController controller2 = TextEditingController();

  changeController(TextEditingController controller, input) {
    setState(() {
      controller.text = input;
    });
  }

  Future<void> fetchData() async {
    var data = {
      "accountId" : controller1.text,
      "password" : controller2.text,
      "provider":"EMAIL"
    };
    var body = json.encode(data);
    // var apiUrl = 'http://k8e106.p.ssafy.io:8000/user-service/users';
    var apiUrl = Uri.parse('http://k8e106.p.ssafy.io:8000/auth-service/login');
    try {
      final response = await http.post(
        apiUrl,
        headers: {"Content-Type": "application/json"},
        body: body,
      );
      final storage = FlutterSecureStorage();
      if (response.statusCode == 200) {
        // The API call was successful, and you can parse the response body here.
        print('Response data: ${response.body}');
        var responseData = json.decode(response.body);
        var accessToken = responseData['data']['accessToken'];
        var refreshToken = responseData['data']['refreshToken'];
        // AccessToken과 refreshToken을 저장
        // await storage.write(key: 'accessToken', value: accessToken);
        await storage.write(key: 'accessToken', value: "1");
        // await storage.write(key: 'refreshToken', value: refreshToken);

        Navigator.push(context,MaterialPageRoute(
                builder: (c) => homePage()));
      } else {
        // The API call failed. You can handle the error here.
        print('Failed to load data. Status code: ${response.statusCode}');
      }
    } catch (e) {
      // An exception occurred during the API call. You can handle the exception here.
      print('Error: $e');
    }
  }


  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      body: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        color: Colors.white,
        child: Stack(
          children: <Widget>[
            Align(
              alignment: Alignment.bottomRight,
              widthFactor: 0.6,
              heightFactor: 0.6,
              child: Material(
                borderRadius: BorderRadius.all(Radius.circular(200)),
                // color: Color.fromRGBO(255, 255, 255, 0.2),
                color: main_Color,
                child: Container(
                  width: 400,
                  height: 400,
                ),
              ),
            ),
            Center(
              child: Container(
                width: 400,
                height: 600,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Material(
                        elevation: 10.0,
                        borderRadius: BorderRadius.all(Radius.circular(70.0)),
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Image.asset(
                            imageLogoName,
                            width: 150,
                            height: 150,
                          ),
                        )),
                    customInputField(Icon(Icons.person, color: Colors.white),
                        'ID', controller1, main_Color),
                    customInputField(Icon(Icons.lock, color: Colors.white),
                        'PASSWORD', controller2, main_Color),
                    Padding(
                      padding: const EdgeInsets.fromLTRB(0, 0, 0, 0),
                      child: Container(
                        height: 50,
                        width: 250,
                        decoration: BoxDecoration(
                            color: main_Color,
                            borderRadius: BorderRadius.circular(20)),
                        child: TextButton(
                          onPressed: () {
                            if (controller1.text != "" &&
                                controller2.text != "") {
                              // print(
                              //     "ID : ${controller1.text} PW : ${controller2.text} 로그인했음");
                              // //테스트입니다
                              // // 테스트 2
                              fetchData();
                            } else if (controller1.text == "" &&
                                controller2.text != "") {
                              showSnackBar(
                                  context, Text('아이디를 입력해주세요'), main_Color);
                            } else if (controller1.text != "" &&
                                controller2.text == "") {
                              showSnackBar(
                                  context, Text('패스워드를 입력해주세요'), main_Color);
                            } else if (controller1.text == "" &&
                                controller2.text == "") {
                              showSnackBar(
                                  context, Text('정보를 입력해주세요'), main_Color);
                            }
                          },
                          child: Text(
                            'Login',
                            style: TextStyle(color: Colors.white, fontSize: 25),
                          ),
                        ),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.fromLTRB(0, 0, 0, 0),
                      child: Container(
                        height: 50,
                        width: 250,
                        decoration: BoxDecoration(
                            color: main_Color,
                            borderRadius: BorderRadius.circular(20)),
                        child: TextButton(
                          onPressed: () {
                            // Navigator.push(
                            //     context, MaterialPageRoute(builder: (_) => homePage()));
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (c) => registPage()));
                            print("회원가입");
                          },
                          child: Text(
                            '회원가입',
                            style: TextStyle(color: Colors.white, fontSize: 25),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

void showSnackBar(BuildContext context, Text text, Color color) {
  final snackBar = SnackBar(
    content: text,
    backgroundColor: color.withOpacity(0.7),
  );

// Find the ScaffoldMessenger in the widget tree
// and use it to show a SnackBar.
  ScaffoldMessenger.of(context).showSnackBar(snackBar);
}

class NextPage extends StatelessWidget {
  const NextPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container();
  }
}
