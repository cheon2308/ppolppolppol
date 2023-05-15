import 'dart:async';
import 'dart:io';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:ppol/constant/auth_dio.dart';
import 'package:ppol/widgets/customInputField.dart';
import 'package:ppol/widgets/switch.dart';
// import 'package:video_player/video_player.dart';

class articleAdd extends StatefulWidget {const articleAdd({super.key, this.title});

  final String? title;

  @override
  State<articleAdd> createState() => _articleAddState();
}
class _articleAddState extends State<articleAdd> {
  List<XFile>? _imageFileList;

  void _setImageFileListFromFile(XFile? value) {
    _imageFileList = value == null ? null : <XFile>[value];
  }

  dynamic _pickImageError;
  bool isVideo = false;
  TextEditingController content = TextEditingController();

  bool isPrivate = false;

  // void ChangePublicState() {
  //   setState(() {
  //     if(isPirvate=="PUBLIC"){
  //       isPirvate = "PRIVATE";
  //     }
  //     else if(isPirvate == "PRIVATE"){
  //       isPirvate = "PUBLIC";
  //     }
  //   });
  // }

  final MaterialStateProperty<Icon?> thumbIcon =
  MaterialStateProperty.resolveWith<Icon?>(
        (Set<MaterialState> states) {
      // Thumb icon when the switch is selected.
      if (states.contains(MaterialState.selected)) {
        return Icon(Icons.check);
      }
      return Icon(Icons.close);
    },
  );
  bool light1 = true;

  // VideoPlayerController? _controller;
  // VideoPlayerController? _toBeDisposed;
  String? _retrieveDataError;

  final ImagePicker _picker = ImagePicker();

  // Future<dynamic> patchUserProfileImage(dynamic input) async {
  //   print("프로필 사진을 서버에 업로드 합니다.");
  //   var dio = new Dio();
  //   try {
  //     dio.options.contentType = 'multipart/form-data';
  //     dio.options.maxRedirects.isFinite;
  //
  //     dio.options.headers = {'token': token};
  //     var response = await dio.patch(        baseUri + '/users/profileimage',
  //       data: input,
  //     );
  //     print('성공적으로 업로드했습니다');
  //     return response.data;
  //   } catch (e) {
  //     print(e);
  //   }
  // }



  Future<void> uploadFiles(List<XFile> files) async {
    final storage = new FlutterSecureStorage();
    // 업로드할 파일들의 경로를 HTTP 요청의 바디(body)에 추가하기 위한 멀티파트 요청 객체 생성
    // Dio dio = await authDio(context);
    // final request = await dio.post("/article-service/articles");
    // FormData formData = FormData();
    // for (var file in files) {
    //   formData.files.add(MapEntry(
    //     'imageList',
    //     await MultipartFile.fromFile(file.path, filename: file.path.split('/').last),
    //   ));
    // }
    // formData.fields.add(
    //   MapEntry('content', content.text),
    // );
    // formData.fields.add(
    //   MapEntry('openStatus', isPrivate ? "private" : "public"),
    // );
    //
    // Response response = await dio.post('/article-service/articles', data: formData);
    var request = http.MultipartRequest('POST', Uri.parse('http://k8e106.p.ssafy.io:8000/article-service/articles'));

    Future<String?> accessTokenFuture  = storage.read(key: 'accessToken'); // 예시로 Future<String?> 변수를 선언
    String? accessToken = await accessTokenFuture; // Future가 완료될 때까지 기다리고 결과를 추출


    // print("${storage.read(key: 'accessToken').toString()}");
    // Authorization 헤더에 인증 정보를 추가


    request.headers.addAll({
      'Authorization': accessToken.toString(), //내 token 넣기 원래라면
    });
    // 파일 목록을 반복하면서 요청 객체에 파일을 추가
    for (var file in files) {
      // 파일을 바이트 스트림으로 변환
      var stream= new http.ByteStream(
          Stream.castFrom(File(file.path).openRead())
          // DelegatingStream.typed(File(file.path).openRead())
      );
      //ㅎㅇㅎㅇ

      var length = await File(file.path).length();
      // 요청 객체에 파일 추가
      request.files.add(
          http.MultipartFile('imageList', stream, length, filename: File(file.path).path.split("/").last));
      request.fields['content'] = content.text;
      request.fields['openStatus'] = isPrivate?"private":"public";
    }

    // 서버에 요청 보내기
    var response = await request.send();

    // 응답 코드 확인
    print("accessToken : ${accessToken}");
    print("뭐양 ${response.statusCode}");
    if (response.statusCode == 201) {
      print('Files uploaded successfully!');
      Navigator.pop(context);
    } else {
      print('Failed to upload files: ${response.statusCode}');
    }
  }

  Future<void> _onImageButtonPressed(ImageSource source,
      {BuildContext? context, bool isMultiImage = false}) async {
    if (isMultiImage) {
      {
        try {
          final List<XFile> pickedFileList = await _picker.pickMultiImage();          
          setState(() {
            _imageFileList = pickedFileList;
            _imageFileList?.add(XFile('assets/plus_picture.png'));
            print("pickedFileList입니다 ${_imageFileList}");

          });
        } catch (e) {
          setState(() {
            _pickImageError = e;
          });
        }
      };
    } else {
      {
        try {
          final XFile? pickedFile = await _picker.pickImage(source: source,);
          setState(() {
            _setImageFileListFromFile(pickedFile);
          });
          if (pickedFile != null) {
            dynamic sendData = pickedFile.path;
            print("sendData입니다 ${sendData}");
            var formData = FormData.fromMap({'image': await MultipartFile.fromFile(sendData)});

          }
        } catch (e) {
          setState(() {
            _pickImageError = e;
          });
        }
      };
    }
  }

  Widget _previewImages() {
    final Text? retrieveError = _getRetrieveErrorWidget();
    if (retrieveError != null) {
      return retrieveError;
    }
    if (_imageFileList != null) {
      return Semantics(
        child: Column(
          children: [
            Container(
              margin: EdgeInsets.fromLTRB(0, 30, 0, 0),
              child: _Carse(),
            ),
            Container(
              margin: EdgeInsets.fromLTRB(40, 20, 40, 0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  Text("비공개"),
                  Switch(
                    // thumbIcon: thumbIcon,
                    thumbIcon: thumbIcon,
                    value: isPrivate,
                    onChanged: (bool value) {
                      print(value);
                      setState(() {
                        isPrivate = value;
                      });
                    },
                  ),
                ],
              ),
            ),
            Container(
              width: 300,
              height: 200,
              padding: EdgeInsets.all(30),              
              decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.black38,
                  width: 1,
                ),
                borderRadius: BorderRadius.circular(10),
              ),
              child: TextField(
                // scrollPadding: EdgeInsets.all(20),
                showCursor: true,
                controller: content,
                // cursorHeight: ,
                maxLines: 8,
                maxLength: 2000,
                style: TextStyle(
                  fontSize: 20,
                ),
                decoration: InputDecoration(
                  border: InputBorder.none,
                  hintText: "게시물 입력",
                ),
              ),
            )
          ],
        ),
      );
    } else if (_pickImageError != null) {
      return Text(
        'Pick image error: $_pickImageError',
        textAlign: TextAlign.center,
      );
    } else {
      return
        Container(
          padding: EdgeInsets.fromLTRB(0,30,0,0),
          alignment: Alignment.topCenter,
          decoration: BoxDecoration(
            color: Colors.transparent,  // 투명 배경색 설정
          ),
          child: ElevatedButton(
            child:Image.asset('assets/plus_picture.png',fit: BoxFit.cover),
            style: ElevatedButton.styleFrom(
              primary: Colors.transparent,  // 버튼의 배경색을 투명으로 설정
              elevation: 0,  // 그림자 효과 제거
              // shadowColor: Colors.transparent,  // 그림자 색상을 투명으로 설정
              backgroundColor: Colors.transparent
            ),
            onPressed: () {
              isVideo = false;
              _onImageButtonPressed(
                ImageSource.gallery,
                context: context,
                isMultiImage: true,
              );
            },
      ),
        );
    }
  }
  
  Widget _Carse(){
    return CarouselSlider.builder(
      options: CarouselOptions(
          height: 350,
          enableInfiniteScroll: false,
      ),
      itemCount: _imageFileList!.length,
      itemBuilder: (context,index,realIndex){
        if((index+1)==(_imageFileList?.length)){
          print("난 여기");
          var urlImage = _imageFileList?[index].path;
          return Container(
            margin: EdgeInsets.symmetric(horizontal: 10),
            child: ElevatedButton(
              onPressed: () {
                isVideo = false;
                _onImageButtonPressed(
                  ImageSource.gallery,
                  context: context,
                  isMultiImage: true,
                );
              },
              style: ElevatedButton.styleFrom(
                  elevation: 0,  // 그림자 효과 제거
                  backgroundColor: Colors.transparent
              ),
              child: Image.asset(
                urlImage.toString(),
                fit: BoxFit.cover,
              ),
            ),
          );
        }
        else{
          var urlImage = _imageFileList?[index].path;
          return buildImage(urlImage!,index);
        }
      },
    );
  }

  Widget _handlePreview() {
    // if (isVideo) {
    //   return _previewVideo();
    // } else {
    //
    return _previewImages();
    // }
  }

  Future<void> retrieveLostData() async {
    final LostDataResponse response = await _picker.retrieveLostData();
    if (response.isEmpty) {
      return;
    }
    if (response.file != null) {
      if (response.type == RetrieveType.video) {
        isVideo = true;
        // await _playVideo(response.file);
      } else {
        isVideo = false;
        setState(() {
          if (response.files == null) {
            _setImageFileListFromFile(response.file);
          } else {
            _imageFileList = response.files;
          }
        });
      }
    } else {
      _retrieveDataError = response.exception!.code;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        // title: Text(widget.title!),
        backgroundColor: Colors.white,
        shadowColor: Colors.transparent,
        foregroundColor: Colors.black,
        actions: [
          IconButton(
            icon: Icon(Icons.check),
            onPressed: () {
              print(content.text);
              print(isPrivate.toString());
              print(_imageFileList);
              _imageFileList?.removeLast();
              uploadFiles(_imageFileList!);
            },
          ),
        ],
      ),
      body: SingleChildScrollView(
        child: Container(
          child: !kIsWeb && defaultTargetPlatform == TargetPlatform.android
              ? FutureBuilder<void>(
            future: retrieveLostData(),
            builder: (BuildContext context, AsyncSnapshot<void> snapshot) {
              switch (snapshot.connectionState) {
                case ConnectionState.none:
                case ConnectionState.waiting:
                  return const Text(
                    'You have not yet picked an image.',
                    textAlign: TextAlign.center,
                  );
                case ConnectionState.done:
                  return _handlePreview();
                case ConnectionState.active:
                  if (snapshot.hasError) {
                    return Text(
                      'Pick image/video error: ${snapshot.error}}',
                      textAlign: TextAlign.center,
                    );
                  } else {
                    return const Text(
                      'You have not yet picked an image.',
                      textAlign: TextAlign.center,
                    );
                  }
              }
            },
          )
              : _handlePreview(),
        ),
      ),
    );
  }

  Text? _getRetrieveErrorWidget() {
    if (_retrieveDataError != null) {
      final Text result = Text(_retrieveDataError!);
      _retrieveDataError = null;
      return result;
    }
    return null;
  }
  
  
  Widget buildImage(String urlImage, int inddex)=>Container(
    margin: EdgeInsets.symmetric(horizontal: 10),
    color: Colors.transparent,
    child: Image.file(
      File(urlImage),
      fit: BoxFit.cover,
    ),
  );
}