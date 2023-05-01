import 'dart:async';
import 'package:flutter/material.dart';


class landingPage extends StatelessWidget {
  const landingPage({Key? key}) : super(key: key);


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: MediaQuery.of(context).size.width,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Image.asset(
              'assets/main_logo/ppol_logo.png',
            ),
            Image.asset(
              'assets/main_logo/pjt_name_black.png',
              scale: 1.2,
            ),
            Image.asset(
              'assets/main_logo/with_us.png',
              scale: 2,
            ),
          ],
        ),
      ),
    );
  }
}

class landingPage2 extends StatefulWidget {
  const landingPage2({Key? key}) : super(key: key);

  @override
  void initState(context) {
    Timer(Duration(milliseconds: 1500), () {
      Navigator.push(context, MaterialPageRoute(
          builder: (context) => landingPage()
      )
      );
    });
  }

  @override
  State<landingPage2> createState() => _landingPage2State();
}

class _landingPage2State extends State<landingPage2> {


  @override
  Widget build(BuildContext context) {
    final String imageLogoName = "assets/main_logo/ppol_logo.png";
    final String pjtname = "assets/main_logo/pjt_name_black.png";
    final String withus = "assets/main_logo/with_us.png";

    var screenHeight = MediaQuery.of(context).size.height;
    var screenWidth = MediaQuery.of(context).size.width;

    return WillPopScope(
      onWillPop: () async => false,
      child: MediaQuery(
        data: MediaQuery.of(context).copyWith(textScaleFactor:1.0),
        child: new Scaffold(
          // backgroundColor: Colors.pink,
          body: new Container(
            //height : MediaQuery.of(context).size.height,
            //color: kPrimaryColor,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                SizedBox(height: screenHeight * 0.184375),
                Container(
                  child: Image.asset(
                    imageLogoName,
                    width: screenWidth * 0.56666,
                    // height: screenHeight * 0.0859375,
                  ),
                ),
                Expanded(child: SizedBox(
                  child: Column(
                    children: [
                      Container(
                        margin: EdgeInsets.fromLTRB(0,40,0,0),
                        child: Image.asset(
                          pjtname,
                          width: screenWidth * 0.56179,
                        ),
                      ),
                      Image.asset(
                        withus,
                        width: screenWidth * 0.39062,
                      ),
                    ],
                  ),
                )),
                Align(
                  child: Text("© Copyright 2023, 압도적무력(power overwhelming)",
                      style: TextStyle(
                        fontSize: screenWidth*( 14/360), color: Color.fromRGBO(0, 0, 0, 0.6),)
                  ),
                ),
                SizedBox( height: MediaQuery.of(context).size.height*0.0625,),
              ],
            ),
          ),
        ),
      ),
    );
  }
}


// @override
// Widget build(BuildContext context) {
//   final String imageLogoName = 'assets/images/public/PurpleLogo.svg';
//
//   var screenHeight = MediaQuery.of(context).size.height;
//   var screenWidth = MediaQuery.of(context).size.width;
//
//   return WillPopScope(
//     onWillPop: () async => false,
//     child: MediaQuery(
//       data: MediaQuery.of(context).copyWith(textScaleFactor:1.0),
//       child: new Scaffold(
//         backgroundColor: hexToColor('#6F22D2'),
//         body: new Container(
//           //height : MediaQuery.of(context).size.height,
//           //color: kPrimaryColor,
//           child: Column(
//             mainAxisAlignment: MainAxisAlignment.start,
//             children: <Widget>[
//               SizedBox(height: screenHeight * 0.384375),
//               Container(
//                 child: SvgPicture.asset(
//                   imageLogoName,
//                   width: screenWidth * 0.616666,
//                   height: screenHeight * 0.0859375,
//                 ),
//               ),
//               Expanded(child: SizedBox()),
//               Align(
//                 child: Text("© Copyright 2020, 내방니방(MRYR)",
//                     style: TextStyle(
//                       fontSize: screenWidth*( 14/360), color: Color.fromRGBO(255, 255, 255, 0.6),)
//                 ),
//               ),
//               SizedBox( height: MediaQuery.of(context).size.height*0.0625,),
//             ],
//           ),
//
//         ),
//       ),
//     ),
//   );
// }