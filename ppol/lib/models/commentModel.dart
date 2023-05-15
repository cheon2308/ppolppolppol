class Comment {
  int commentId;
  int articleId;
  int parent;
  String content;
  Comment? comment;
  Writer writer;
  DateTime createdAt;
  String createString;
  bool like;

  Comment({
    required this.commentId,
    required this.articleId,
    required this.parent,
    required this.content,
    required this.comment,
    required this.writer,
    required this.createdAt,
    required this.createString,
    required this.like,
  });

  factory Comment.fromJson(Map<String, dynamic> json) {
    return Comment(
      commentId: json['commentId'],
      articleId: json['articleId'],
      parent: json['parent'],
      content: json['content'],
      comment: json['comment'] != null ? Comment.fromJson(json['comment']) : null,
      writer: Writer.fromJson(json['writer']),
      createdAt: DateTime.parse(json['createdAt']),
      createString: json['createString'],
      like: json['like'],
    );
  }
}

class Writer {
  int userId;
  String username;
  String profileImage;

  Writer({
    required this.userId,
    required this.username,
    required this.profileImage,
  });

  factory Writer.fromJson(Map<String, dynamic> json) {
    return Writer(
      userId: json['userId'],
      username: json['username'],
      profileImage: json['profileImage'],
    );
  }
}