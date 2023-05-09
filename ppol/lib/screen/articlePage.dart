import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:ppol/models/ArticleModel.dart';

class ArticleScreen extends StatefulWidget {
  @override
  _ArticleScreenState createState() => _ArticleScreenState();
}

class _ArticleScreenState extends State<ArticleScreen> {
  List<ArticleModel> _articles = [];

  @override
  void initState() {
    super.initState();
    _fetchArticles();
  }

  void _fetchArticles() async {
    final url =
        Uri.parse('http://k8e106.p.ssafy.io:8000/article-service/articles');

    try {
      final response = await http.get(url, headers: {'Authorization': '1'});

      if (response.statusCode == 200) {
        final articlesJson = json.decode(response.body)['data'];
        List<ArticleModel> articles = [];
        articlesJson.forEach((articleJson) {
          articles.add(ArticleModel.fromJson(articleJson));
        });
        setState(() {
          _articles = articles;
        });
      } else {
        print('Request failed with status: ${response.statusCode}.');
      }
    } catch (error) {
      print('Error: $error');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView.builder(
        itemCount: _articles.length,
        itemBuilder: (context, index) {
          final article = _articles[index];
          return Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.fromLTRB(16, 16, 8, 8),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Row(
                      children: [
                        Container(
                          width: 50,
                          height: 50,
                          decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            image: DecorationImage(
                              image: AssetImage('assets/main_logo/test1.png'),
                              fit: BoxFit.fill,
                            ),
                          ),
                        ),
                        SizedBox(width: 10),
                        Text(
                          article.data.author.username,
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                    IconButton(
                      icon: Icon(Icons.more_horiz),
                      onPressed: () {},
                    ),
                  ],
                ),
              ),
              Container(
                width: MediaQuery.of(context).size.width,
                height: 430,
                decoration: BoxDecoration(
                  image: DecorationImage(
                    image: AssetImage('assets/main_logo/test2.png'),
                    fit: BoxFit.fill,
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(16, 16, 8, 8),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Row(
                      children: [
                        IconButton(
                          icon: Icon(Icons.favorite_border),
                          onPressed: () {},
                        ),
                        SizedBox(width: 10),
                        IconButton(
                          icon: Icon(Icons.chat_bubble_outline),
                          onPressed: () {},
                        ),
                        SizedBox(width: 10),
                        IconButton(
                          icon: Icon(Icons.send),
                          onPressed: () {},
                        ),
                      ],
                    ),
                    IconButton(
                      icon: Icon(Icons.bookmark_border),
                      onPressed: () {},
                    ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: Text(
                  'Liked by ${article.data.likeCount} and ${article.data.commentCount} others',
                  style: TextStyle(fontWeight: FontWeight.bold),
                ),
              ),
              SizedBox(height: 10),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: Text(
                  article.data.content,
                  style: TextStyle(color: Colors.black87),
                ),
              ),
              SizedBox(height: 10),
            ],
          );
        },
      ),
    );
  }
}
