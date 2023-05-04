import 'dart:async';
import 'dart:io';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:http/http.dart' as http;
// import 'package:video_player/video_player.dart';

class articleAddTest extends StatefulWidget {const articleAddTest({super.key, this.title});

  final String? title;

  @override
  State<articleAddTest> createState() => _articleAddTestState();
}
class _articleAddTestState extends State<articleAddTest> {
  List<XFile>? _imageFileList;

  void _setImageFileListFromFile(XFile? value) {
    _imageFileList = value == null ? null : <XFile>[value];
  }

  dynamic _pickImageError;
  bool isVideo = false;

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
    // 업로드할 파일들의 경로를 HTTP 요청의 바디(body)에 추가하기 위한 멀티파트 요청 객체 생성
    var request = http.MultipartRequest('POST', Uri.parse('http://k8e106.p.ssafy.io:8000/article-service/articles'));

    // Authorization 헤더에 인증 정보를 추가
    request.headers.addAll({
      'Authorization': '1',
    });
    // 파일 목록을 반복하면서 요청 객체에 파일을 추가
    for (var file in files) {
      // 파일을 바이트 스트림으로 변환
      var stream= new http.ByteStream(
          Stream.castFrom(File(file.path).openRead())
          // DelegatingStream.typed(File(file.path).openRead())
      );

      var length = await File(file.path).length();

      // 요청 객체에 파일 추가
      request.files.add(
          http.MultipartFile('imageList', stream, length, filename: File(file.path).path.split("/").last));
      request.fields['content'] = '하이';
      request.fields['openStatus'] = 'PUBLIC';
    }

    // 서버에 요청 보내기
    var response = await request.send();

    // 응답 코드 확인
    if (response.statusCode == 200) {
      print('Files uploaded successfully!');
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
        label: 'image_picker_example_picked_images',
        child: _Carse(),
      );
    } else if (_pickImageError != null) {
      return Text(
        'Pick image error: $_pickImageError',
        textAlign: TextAlign.center,
      );
    } else {
      return 
        TextButton(
          child: Text('You have not yet picked an image.',textAlign: TextAlign.center,),
          onPressed: () {
            isVideo = false;
            _onImageButtonPressed(
              ImageSource.gallery,
              context: context,
              isMultiImage: true,
            );
          },
      );
    }
  }
  
  Widget _Carse(){
    return CarouselSlider.builder(

      options: CarouselOptions(
          height: 350,
          enableInfiniteScroll: false
      ),
      itemCount: _imageFileList!.length,
      itemBuilder: (context,index,realIndex){
        if((index+1)==(_imageFileList?.length)){
          print("난 여기");
          var urlImage = _imageFileList?[index].path;
          return Container(
            margin: EdgeInsets.symmetric(horizontal: 10),
            color: Colors.grey,
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
                backgroundColor: Colors.transparent,
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
          // print("이건뭔가요 ${urlImage}");
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
        title: Text(widget.title!),
      ),
      body: Container(
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
      floatingActionButton: Column(
        mainAxisAlignment: MainAxisAlignment.end,
        children: <Widget>[
          Semantics(
            label: 'image_picker_example_from_gallery',
            child: FloatingActionButton(
              onPressed: () {
                isVideo = false;
                _onImageButtonPressed(ImageSource.gallery, context: context);
              },
              heroTag: 'image0',
              tooltip: 'Pick Image from gallery',
              child: const Icon(Icons.photo),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 16.0),
            child: FloatingActionButton(
              onPressed: () {
                isVideo = false;
                _onImageButtonPressed(
                  ImageSource.gallery,
                  context: context,
                  isMultiImage: true,
                );
              },
              heroTag: 'image1',
              tooltip: 'Pick Multiple Image from gallery',
              child: const Icon(Icons.photo_library),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 16.0),
            child: FloatingActionButton(
              onPressed: () {
                _imageFileList?.removeLast();
                uploadFiles(_imageFileList!);
              },
              heroTag: 'image1',
              tooltip: '전송한다',
              child: const Icon(Icons.send),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 16.0),
            child: FloatingActionButton(
              onPressed: () {
                isVideo = false;
                _onImageButtonPressed(ImageSource.camera, context: context);
              },
              heroTag: 'image2',
              tooltip: 'Take a Photo',
              child: const Icon(Icons.camera_alt),
            ),
          ),
        ],
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
    color: Colors.grey,
    child: Image.file(
      File(urlImage),
      fit: BoxFit.cover,
    ),
  );
}
