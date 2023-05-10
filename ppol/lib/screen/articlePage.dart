import 'package:flutter/material.dart';
import 'package:ppol/models/articleModel.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:ppol/screen/articleDetailPage.dart';

Future<List<ArticleModel>> fetchArticles() async {
  final response = await http.get(
    Uri.parse('http://k8e106.p.ssafy.io:8000/article-service/articles'),
    headers: {
      'Authorization': '1',
      'Accept-Charset': 'utf-8',
    },
  );
  if (response.statusCode == 200) {
    final List<dynamic> jsonList = jsonDecode(response.body)['data'];
    final List<ArticleModel> articles =
    jsonList.map((json) => ArticleModel.fromJson(json)).toList();
    return articles;
  } else {
    throw Exception('Failed to load articles');
  }
}

class ArticleCard extends StatefulWidget {
  final ArticleModel article;

  const ArticleCard({Key? key, required this.article}) : super(key: key);

  @override
  _ArticleCardState createState() => _ArticleCardState();
}

class _ArticleCardState extends State<ArticleCard> {
  int _likeCount = 0;

  void _toggleLike() {
    setState(() {
      _likeCount += _likeCount > 0 ? -1 : 1;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          ListTile(
            leading: widget.article.imageList.isNotEmpty
                ? Image.network(widget.article.imageList.first)
                : Image.asset('assets/icon.png'),
            title: Text(
              widget.article.content,
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
            subtitle: Text(widget.article.writer.username),
          ),
          if (widget.article.imageList.isNotEmpty)
            Image.network(
              widget.article.imageList.first,
              fit: BoxFit.cover,
              height: 300,
            ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                IconButton(
                  icon: Icon(Icons.favorite_border),
                  onPressed: _toggleLike,
                ),
                IconButton(
                  icon: Icon(Icons.bookmark_border),
                  onPressed: () {},
                ),
              ],
            ),
          ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: Text(
              '$_likeCount likes',
            ),
          ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: Text(
              widget.article.content,
            ),
          ),
        ],
      ),
    );
  }
}

class ArticleScreen extends StatefulWidget {
  const ArticleScreen({Key? key}) : super(key: key);

  @override
  _ArticleScreenState createState() => _ArticleScreenState();
}

class _ArticleScreenState extends State<ArticleScreen> {
  late Future<List<ArticleModel>> _futureArticles;

  @override
  void initState() {
    super.initState();
    _futureArticles = fetchArticles();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('게시물 목록'),
      ),
      body: FutureBuilder<List<ArticleModel>>(
        future: _futureArticles,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            final articles = snapshot.data!;
            if (articles.isEmpty) {
              return Center(
                child: Text('게시물이 없습니다.'),
              );
            } else {
              return ListView.builder(
                itemCount: articles.length,
                itemBuilder: (context, index) {
                  final article = articles[index];
                  return GestureDetector(
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => ArticleDetailScreen(article: article),
                        ),
                      );
                    },
                    child: ArticleCard(article: article),
                  );
                },
              );
            }
          } else if (snapshot.hasError) {
            return Center(
              child: Text('${snapshot.error}'),
            );
          } else {
            return Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }
}