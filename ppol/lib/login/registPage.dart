import 'package:flutter/material.dart';
import 'package:ppol/login/customInputField.dart';
import 'package:ppol/login/loginPage.dart';

class registPage extends StatefulWidget {
  const registPage({Key? key}) : super(key: key);

  @override
  State<registPage> createState() => _registPageState();
}

class _registPageState extends State<registPage> {

  // final main_Color=Colors.brown;
  final main_Color=  Color(0xffADD8E6);

  TextEditingController NAME =TextEditingController();
  TextEditingController ID =TextEditingController();
  TextEditingController PASSWORD =TextEditingController();
  TextEditingController NICKNAME =TextEditingController();

  @override
  Widget build(BuildContext context) {
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
                    width: 300,
                    height: 300,
                  ),
                ),
              ),
              Center(
                widthFactor: 10,
                heightFactor: 10,
                child: Container(
                  width: 400,
                  height: 600,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
                      Text("로그인",
                        style: TextStyle(
                          fontSize: 30,
                          fontWeight: FontWeight.w800,
                        ),
                      ),                      
                      customInputField(
                          Icon(Icons.person, color: Colors.white), 'ID', ID ,main_Color),
                      customInputField(
                          Icon(Icons.lock, color: Colors.white), 'PW', PASSWORD ,main_Color),
                      customInputField(
                          Icon(Icons.drive_file_rename_outline, color: Colors.white), '닉네임/이름', NICKNAME ,main_Color),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: [
                          Padding(
                            padding: const EdgeInsets.fromLTRB(0,0,0,0),
                            child: Container(
                              height: 50,
                              width: 130,
                              decoration: BoxDecoration(
                                  color: main_Color, borderRadius: BorderRadius.circular(20)),
                              child: TextButton(
                                onPressed: () {
                                  // Navigator.push(
                                  //     context, MaterialPageRoute(builder: (_) => homePage()));
                                  Navigator.push(
                                      context, MaterialPageRoute(builder: (c) => registPage()));
                                  print("회원가입");
                                },
                                child: Text(
                                  '가입하기',
                                  style: TextStyle(color: Colors.white, fontSize: 25),
                                ),
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.fromLTRB(0,0,0,0),
                            child: Container(
                              height: 50,
                              width: 130,
                              decoration: BoxDecoration(
                                  color: main_Color, borderRadius: BorderRadius.circular(20)),
                              child: TextButton(
                                onPressed: () {
                                  Navigator.pop(context);
                                },
                                child: Text(
                                  '취소하기',
                                  style: TextStyle(color: Colors.white, fontSize: 25),
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        )
    );
  }
}
