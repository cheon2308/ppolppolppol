class ArticleModel {
  final int articleId;
  final List<String> imageList;
  final String content;
  final CommentModel? comment;
  final WriterModel writer;
  final int likeCount;
  final String openStatus;
  final DateTime createdAt;
  final UserInteractionModel userInteraction;

  ArticleModel({
    required this.articleId,
    required this.imageList,
    required this.content,
    required this.comment,
    required this.writer,
    required this.likeCount,
    required this.openStatus,
    required this.createdAt,
    required this.userInteraction,
  });

  factory ArticleModel.fromJson(Map<String, dynamic> json) {
    return ArticleModel(
      articleId: json['articleId'],
      imageList: List<String>.from(json['imageList']),
      content: json['content'],
      comment:
      json['comment'] != null ? CommentModel.fromJson(json['comment']) : null,
      writer: WriterModel.fromJson(json['writer']),
      likeCount: json['likeCount'],
      openStatus: json['openStatus'],
      createdAt: DateTime.parse(json['createdAt']),
      userInteraction: UserInteractionModel.fromJson(json['userInteraction']),
    );
  }
}

class CommentModel {
  final int? commentId;
  final int? articleId;
  final int? parent;
  final String? content;
  final CommentModel? comment;
  final WriterModel? writer;
  final DateTime? createdAt;
  final String? createString;
  final bool? like;

  CommentModel({
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

  factory CommentModel.fromJson(Map<String, dynamic> json) {
    return CommentModel(
      commentId: json['commentId'],
      articleId: json['articleId'],
      parent: json['parent'],
      content: json['content'],
      comment:
      json['comment'] != null ? CommentModel.fromJson(json['comment']) : null,
      writer: WriterModel.fromJson(json['writer']),
      createdAt: DateTime.parse(json['createdAt']),
      createString: json['createString'],
      like: json['like'],
    );
  }
}

class WriterModel {
  final int userId;
  final String username;
  final String profileImage;

  WriterModel({
    required this.userId,
    required this.username,
    required this.profileImage,
  });

  factory WriterModel.fromJson(Map<String, dynamic> json) {
    return WriterModel(
      userId: json['userId'],
      username: json['username'],
      profileImage: json['profileImage'],
    );
  }
}

class UserInteractionModel {
  final bool like;
  final bool bookmark;
  final bool follow;

  UserInteractionModel({
    required this.like,
    required this.bookmark,
    required this.follow,
  });

  factory UserInteractionModel.fromJson(Map<String, dynamic> json) {
    return UserInteractionModel(
      like: json['like'],
      bookmark: json['bookmark'],
      follow: json['follow'],
    );
  }
}