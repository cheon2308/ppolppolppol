import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class CommentsPage extends StatefulWidget {
  final String postId;

  CommentsPage({required this.postId});

  @override
  _CommentsPageState createState() => _CommentsPageState();
}

class _CommentsPageState extends State<CommentsPage> {
  List _comments = [];

  final TextEditingController _commentController = TextEditingController();

  Future<void> _getComments() async {
    final response = await http.get(
      Uri.parse(
          'http://k8e106.p.ssafy.io:8000/article-service/articles/${widget.postId}/comments?commentOrder=LIKE'),
      headers: {
        'Authorization': '1',
        'Accept-Charset': 'utf-8',
      },
    );
    if (response.statusCode == 200) {
      setState(() {
        _comments = jsonDecode(utf8.decode(response.bodyBytes))['data'];
      });
    } else {
      throw Exception('Failed to load comments');
    }
  }

  Future<void> _postComment() async {
    final response = await http.post(
      Uri.parse(
          'http://k8e106.p.ssafy.io:8000/article-service/articles/${widget.postId}/comments'),
      headers: {
        'Authorization': '1',
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'content': _commentController.text,
      }),
    );
    if (response.statusCode == 200) {
      _getComments();
    } else {
      throw Exception('Failed to post comment');
    }
  }

  Future<void> _putComment(String commentId, String content) async {
    final response = await http.put(
      Uri.parse(
          'http://k8e106.p.ssafy.io:8000/article-service/articles/comments/$commentId'),
      headers: {
        'Authorization': '1',
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'content': content,
      }),
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to put comment');
    }
  }

  Future<void> _deleteComment(String commentId) async {
    final response = await http.delete(
      Uri.parse(
          'http://k8e106.p.ssafy.io:8000/article-service/comments/$commentId'),
      headers: {
        'Authorization': '1',
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to delete comment');
    }
  }

  @override
  void initState() {
    super.initState();
    _getComments();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Comments'),
      ),
      body: Column(
        children: [
      Expanded(
      child: ListView.builder(
      itemCount: _comments.length,
        itemBuilder: (BuildContext context, int index) {
          return ListTile(
            leading: CircleAvatar(
              backgroundImage: NetworkImage(_comments[index]['writer']['profileImage']),
            ),
            title: Row(
              children: [
                Text(_comments[index]['writer']['username']),
                Padding(
                  padding: EdgeInsets.only(left: 8),
                  child: Text(
                    _comments[index]['createString'],
                    style: TextStyle(
                      color: Colors.grey,
                      fontSize: 14,
                      height: 1,
                    ),
                  ),
                ),
              ],
            ),
            subtitle: Text(
              _comments[index]['content'],
              maxLines: 2,
              overflow: TextOverflow.ellipsis,
            ),
            trailing: IconButton(
              icon: Icon(Icons.edit),
              onPressed: () {
                Navigator.of(context).pushNamed('/edit-comment', arguments: _comments[index]);
              },
            ),
          );},
      ),
      ),
        ],
      ),
    );
  }
}


class EditCommentPage extends StatefulWidget {
  final String commentId;
  final String content;

  EditCommentPage({required this.commentId, required this.content});

  @override
  _EditCommentPageState createState() => _EditCommentPageState();
}

class _EditCommentPageState extends State<EditCommentPage> {
  late TextEditingController _textEditingController;

  @override
  void initState() {
    super.initState();
    _textEditingController = TextEditingController(text: widget.content);
  }

  @override
  void dispose() {
    _textEditingController.dispose();
    super.dispose();
  }

  void _updateComment() async {
    final response = await http.put(
      Uri.parse('http://k8e106.p.ssafy.io:8000/article-service/comments/${widget.commentId}'),
      headers: {
        'Authorization': '1',
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode({
        'content': _textEditingController.text,
      }),
    );
    if (response.statusCode == 200) {
      Navigator.of(context).pop(true);
    } else {
      throw Exception('Failed to update comment');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Edit Comment'),
        actions: [
          IconButton(
            icon: Icon(Icons.check),
            onPressed: _updateComment,
          ),
        ],
      ),
      body: Padding(
        padding: EdgeInsets.all(16),
        child: TextField(
          controller: _textEditingController,
          maxLines: null,
          decoration: InputDecoration(
            hintText: 'Enter your comment...',
          ),
        ),
      ),
    );
  }
}