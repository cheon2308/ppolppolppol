import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:ppol/models/articleModel.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:ppol/screen/articleDetailPage.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';

Future<List<ArticleModel>> fetchArticles() async {
  final response = await http.get(
    Uri.parse('http://k8e106.p.ssafy.io:8000/article-service/articles'),
    headers: {
      'Authorization': '1',
      'Accept-Charset': 'utf-8',
    },
  );
  if (response.statusCode == 200) {
    final List<dynamic> jsonList =
        jsonDecode(utf8.decode(response.bodyBytes))['data'];
    final List<ArticleModel> articles =
        jsonList.map((json) => ArticleModel.fromJson(json)).toList();
    return articles;
  } else {
    throw Exception('Failed to load articles');
  }
}

class ArticleCard extends StatefulWidget {
  final ArticleModel article;

  const ArticleCard({
    Key? key,
    required this.article,
  }) : super(key: key);

  @override
  _ArticleCardState createState() => _ArticleCardState();
}

class _ArticleCardState extends State<ArticleCard> {
  late int _likeCount;
  late bool _isLiked;
  late bool _isBookmarked;
  late bool _isCommenting;
  late int _imageIndex;

  @override
  void initState() {
    super.initState();
    _likeCount = widget.article.likeCount;
    _isLiked = false;
    _isBookmarked = false;
    _isCommenting = false;
    _imageIndex = 0;
  }

  void _toggleLike() {
    setState(() {
      _isLiked = !_isLiked;
      _likeCount += _isLiked ? 1 : -1;
      _updateArticle({
        'likeCount': _likeCount,
        'liked': _isLiked,
      });
    });
  }

  void _toggleBookmark() {
    setState(() {
      _isBookmarked = !_isBookmarked;
      _updateArticle({'bookmarked': _isBookmarked});
    });
  }

  // void _toggleCommenting() {
  //   setState(() {
  //     _isCommenting = !_isCommenting;
  //   });
  // }

  void _onPageChanged(int index) {
    setState(() {
      _imageIndex = index;
    });
  }

  Future<void> _updateArticle(Map<String, dynamic> data) async {
    final response = await http.put(
      Uri.parse(
        'http://k8e106.p.ssafy.io:8000/article-service/articles/${widget.article.articleId}/like',
      ),
      headers: {
        'Authorization': '1',
        'Accept-Charset': 'utf-8',
        'Content-Type': 'application/json',
      },
      body: jsonEncode(data),
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to update article');
    }
  }

  @override
  Widget build(BuildContext context) {
    if (widget.article.openStatus == 'private') {
      return Visibility(
        visible: false,
        child: SizedBox(),
      );
    }

    final isMultipleImages = widget.article.imageList.length > 1;
    return Card(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          if (isMultipleImages)
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: List.generate(
                widget.article.imageList.length,
                (index) => Padding(
                  padding: EdgeInsets.symmetric(horizontal: 4),
                  child: CircleAvatar(
                    backgroundColor:
                        _imageIndex == index ? Colors.black : Colors.grey,
                    radius: 4,
                  ),
                ),
              ),
            ),
          if (widget.article.imageList.isNotEmpty)
            Container(
              height: 300,
              child: PageView(
                onPageChanged: _onPageChanged,
                children: widget.article.imageList.map((imageUrl) {
                  return Image.network(
                    imageUrl,
                    fit: BoxFit.cover,
                  );
                }).toList(),
              ),
            ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Row(
                  children: [
                    IconButton(
                      onPressed: _toggleLike,
                      icon: Icon(
                        Icons.thumb_up,
                        color: _isLiked ? Colors.blue : Colors.grey,
                      ),
                    ),
                    Text('${widget.article.likeCount}'),
                    SizedBox(width: 16),
                    IconButton(
                      onPressed: _toggleBookmark,
                      icon: Icon(
                        Icons.bookmark,
                        color: _isBookmarked ? Colors.blue : Colors.grey,
                      ),
                    ),
                  ],
                ),
                IconButton(
                  onPressed: () {},
                  icon: Icon(Icons.share),
                ),
              ],
            ),
          ),
          Divider(height: 0),
          Padding(
            padding: EdgeInsets.all(16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                Text(
                  widget.article.content,
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 18,
                  ),
                ),
                SizedBox(height: 8),
                Text(
                  widget.article.content,
                  style: TextStyle(fontSize: 16),
                ),
              ],
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
        backgroundColor: Color(0xffADD8E6),
        title: Text(
          '뽈뽈뽈',
          style: TextStyle(
            fontFamily: 'text',
            fontSize: 30,
          ),
        ),
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
                          builder: (context) =>
                              ArticleDetailScreen(article: article),
                        ),
                      );
                    },
                    child: ArticleCard(
                      article: article,
                    ),
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
