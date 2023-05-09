import 'package:json_annotation/json_annotation.dart';

part 'article_model.g.dart';

@JsonSerializable()
class ArticleModel {
  int articleId;
  List<String> imageList;
  String content;
  CommentModel comment;
  WriterModel writer;
  String openStatus;
  String createdAt;
  String createString;
  UserInteractionModel userInteraction;

  ArticleModel(
      {required this.articleId,
        required this.imageList,
        required this.content,
        required this.comment,
        required this.writer,
        required this.openStatus,
        required this.createdAt,
        required this.createString,
        required this.userInteraction});

  factory ArticleModel.fromJson(Map<String, dynamic> json) =>
      _$ArticleModelFromJson(json);
  Map<String, dynamic> toJson() => _$ArticleModelToJson(this);
}

@JsonSerializable()
class CommentModel {
  int commentId;
  int articleId;
  int? parent;
  String content;
  CommentModel? comment;
  WriterModel writer;
  String createdAt;
  String createString;
  bool like;

  CommentModel(
      {required this.commentId,
        required this.articleId,
        required this.parent,
        required this.content,
        required this.comment,
        required this.writer,
        required this.createdAt,
        required this.createString,
        required this.like});

  factory CommentModel.fromJson(Map<String, dynamic> json) =>
      _$CommentModelFromJson(json);
  Map<String, dynamic> toJson() => _$CommentModelToJson(this);
}

@JsonSerializable()
class WriterModel {
  int userId;
  String username;
  String profileImage;

  WriterModel(
      {required this.userId,
        required this.username,
        required this.profileImage});

  factory WriterModel.fromJson(Map<String, dynamic> json) =>
      _$WriterModelFromJson(json);
  Map<String, dynamic> toJson() => _$WriterModelToJson(this);
}

@JsonSerializable()
class UserInteractionModel {
  bool like;
  bool bookmark;
  bool follow;

  UserInteractionModel(
      {required this.like, required this.bookmark, required this.follow});

  factory UserInteractionModel.fromJson(Map<String, dynamic> json) =>
      _$UserInteractionModelFromJson(json);
  Map<String, dynamic> toJson() => _$UserInteractionModelToJson(this);
}