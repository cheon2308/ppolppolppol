import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:ppol/models/articleModel.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:ppol/screen/commentScreen.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';
import 'package:intl/intl.dart';

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
  late int _imageIndex;

  @override
  void initState() {
    super.initState();
    _likeCount = widget.article.likeCount;
    _isLiked = widget.article.userInteraction.like;
    _isBookmarked = widget.article.userInteraction.bookmark;
    _imageIndex = 0;
  }

  void _toggleLike() {
    setState(() {
      _isLiked = !_isLiked;
      _likeCount += _isLiked ? 1 : -1;
      _updateLike({
        'liked': _isLiked,
      });
    });
  }

  void _toggleBookmark() {
    setState(() {
      _isBookmarked = !_isBookmarked;
      _updateBookmark({'bookmarked': _isBookmarked});
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

  Future<void> _updateLike(Map<String, dynamic> data) async {
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

  Future<void> _updateBookmark(Map<String, dynamic> data) async {
    final response = await http.put(
      Uri.parse(
        'http://k8e106.p.ssafy.io:8000/article-service/articles/${widget.article.articleId}/bookmark',
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
    final dateTimeFormat = DateFormat('yyyy-MM-dd HH:mm:ss');
    bool _isExpanded = false;
    String getDateTimeString(DateTime dateTime) {
      return dateTimeFormat.format(dateTime);
    }

    return Card(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 16),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Row(
                  children: [
                    CircleAvatar(
                      backgroundImage:
                          NetworkImage(widget.article.writer.profileImage),
                      radius: 20,
                    ),
                    SizedBox(width: 8),
                    Text(
                      widget.article.writer.username,
                      style:
                          TextStyle(fontSize: 16, fontWeight: FontWeight.w700),
                    ),
                  ],
                ),
                // IconButton(
                //   onPressed: () {
                //     Navigator.push(
                //       context,
                //       MaterialPageRoute(
                //         builder: (context) =>
                //             CommentsPage(postId: '',),
                //       ),
                //     );
                //   },
                //   icon: Icon(Icons.more_horiz),
                // ),
              ],
            ),
          ),
          if (widget.article.imageList.isNotEmpty)
            Container(
              height: 380,
              child: PageView(
                onPageChanged: _onPageChanged,
                children: widget.article.imageList.map((imageUrl) {
                  return CachedNetworkImage(
                    imageUrl: imageUrl,
                    fit: BoxFit.fitWidth,
                  );
                }).toList(),
              ),
            ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Expanded(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    IconButton(
                      onPressed: () {
                        _toggleLike();
                      },
                      icon: Icon(
                        _isLiked ? Icons.favorite : Icons.favorite_border,
                      ),
                    ),
                    IconButton(
                      onPressed: () {
                        _toggleLike();
                      },
                      icon: Icon(
                        Icons.chat_outlined,
                      ),
                    ),
                  ],
                ),
              ),
              if (isMultipleImages)
                Expanded(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: List.generate(
                      widget.article.imageList.length,
                      (index) => Padding(
                        padding:
                            EdgeInsets.symmetric(horizontal: 4, vertical: 10),
                        child: CircleAvatar(
                          backgroundColor:
                              _imageIndex == index ? Colors.black : Colors.grey,
                          radius: 4,
                        ),
                      ),
                    ),
                  ),
                ),
              Expanded(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    IconButton(
                      onPressed: () {
                        _toggleBookmark();
                      },
                      icon: Icon(
                        _isBookmarked ? Icons.bookmark : Icons.bookmark_border,
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 15),
            child: Text(
              '좋아요 $_likeCount개',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
          ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  widget.article.writer.username,
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                    height: 1,
                  ),
                ),
                SizedBox(width: 8),
                Expanded(
                  child: Text(
                    widget.article.content,
                    style: TextStyle(fontSize: 16, height: 1),
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
              ],
            ),
          ),
          if (widget.article.comment != null)
            Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
              GestureDetector(
                onTap: () {
                  Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) =>
                                CommentsPage(postId: widget.article.articleId.toString(),),
                          ),
                        );
                },
                child: Padding(
                  padding: EdgeInsets.symmetric(
                    horizontal: 16,
                  ),
                  child: Text(
                    '댓글 모두 보기',
                    style: TextStyle(
                      fontSize: 15,
                      color: Colors.grey,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.symmetric(
                  horizontal: 16,
                ),
                child: Text.rich(
                  TextSpan(
                    children: <TextSpan>[
                      TextSpan(
                          text: '${widget.article.comment!.writer!.username!}   ',
                          style: TextStyle(
                              fontSize: 15, fontWeight: FontWeight.w700)),
                      TextSpan(
                          text: widget.article.comment!.content!,
                          style: TextStyle(
                            fontSize: 15,
                          )),
                    ],
                  ),
                ),
              ),
            ])
          else
            Padding(
              padding: EdgeInsets.symmetric(
                horizontal: 16,
              ),
              child: Text('댓글이 없습니다',
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                    color: Colors.grey,
                  )),
            ),
          Padding(
            padding: EdgeInsets.fromLTRB(16, 4, 16, 8),
            child: Text(
              widget.article.createString,
              style: TextStyle(fontSize: 14, color: Colors.grey),
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
                    // onTap: () {
                    //   Navigator.push(
                    //     context,
                    //     MaterialPageRoute(
                    //       builder: (context) =>
                    //           ArticleDetailScreen(article: article),
                    //     ),
                    //   );
                    // },
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
