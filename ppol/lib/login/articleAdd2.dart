import 'package:flutter/material.dart';
import 'package:multi_image_picker/multi_image_picker.dart';

class articleAdd extends StatefulWidget {
  const articleAdd({Key? key}) : super(key: key);

  @override
  State<articleAdd> createState() => _articleAddState();
}

class _articleAddState extends State<articleAdd> {
  List<Asset> imageList = [];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("widget.title"),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            //새로이 추가된 부분!!! 사진을 화면에 그려주기 위한 부분이다.
            imageList.isEmpty
                ? Container()
                : Container(
              height: 400,
              width: MediaQuery.of(context).size.width,
              child: ListView.builder(
                  scrollDirection: Axis.horizontal,
                  itemCount: imageList.length,
                  itemBuilder: (BuildContext context, int index) {
                    Asset asset = imageList[index];
                    return AssetThumb(
                        asset: asset, width: 300, height: 300);
                  }),
            ),
            OutlinedButton(
              // borderSide: BorderSide(color: Colors.blue[200], width: 3),
              child: Container(
                alignment: Alignment.center,
                height: 30,
                width: 250,
                child: Text(
                  '갤러리',
                  style: TextStyle(fontSize: 20),
                ),
              ),
              onPressed: () {
                getImage();
              },
            )
          ],
        ),
      ),
    );
  }

  getImage() async {
    List<Asset> resultList = [];
    resultList =
    await MultiImagePicker.pickImages(maxImages: 10, enableCamera: true);
    setState(() {
      imageList = resultList;
    });
  }
}