import 'package:flutter/material.dart';
import 'package:ppol/models/articleModel.dart';
import 'package:ppol/screen/commentScreen.dart';

class ArticleDetailScreen extends StatelessWidget {
  final ArticleModel article;
  const ArticleDetailScreen({Key? key, required this.article}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('게시물 상세보기'),
      ),
      body: ListView(
        children: [
          if (article.imageList.isNotEmpty)
            Image.network(
              article.imageList.first,
              fit: BoxFit.cover,
              height: 300,
            ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: Text(
              article.content,
            ),
          ),
          // CommentSection(),
        ],
      ),
    );
  }
}