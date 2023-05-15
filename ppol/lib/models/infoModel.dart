class InfoModel {
  Data? data;
  String? status;
  int? statusCode;
  String? message;
  String? timestamp;
  Null? metadata;

  InfoModel(
      {this.data,
      this.status,
      this.statusCode,
      this.message,
      this.timestamp,
      this.metadata});

  InfoModel.fromJson(Map<String, dynamic> json) {
    data = json['data'] != null ? new Data.fromJson(json['data']) : null;
    status = json['status'];
    statusCode = json['statusCode'];
    message = json['message'];
    timestamp = json['timestamp'];
    metadata = json['metadata'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    data['status'] = this.status;
    data['statusCode'] = this.statusCode;
    data['message'] = this.message;
    data['timestamp'] = this.timestamp;
    data['metadata'] = this.metadata;
    return data;
  }
}

class Data {
  int? userId;
  String? username;
  String? image;
  Null? intro;
  Null? phone;
  int? followerCount;
  int? followingCount;
  bool? follow;

  Data(
      {this.userId,
      this.username,
      this.image,
      this.intro,
      this.phone,
      this.followerCount,
      this.followingCount,
      this.follow});

  Data.fromJson(Map<String, dynamic> json) {
    userId = json['userId'];
    username = json['username'];
    image = json['image'];
    intro = json['intro'];
    phone = json['phone'];
    followerCount = json['followerCount'];
    followingCount = json['followingCount'];
    follow = json['follow'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['userId'] = this.userId;
    data['username'] = this.username;
    data['image'] = this.image;
    data['intro'] = this.intro;
    data['phone'] = this.phone;
    data['followerCount'] = this.followerCount;
    data['followingCount'] = this.followingCount;
    data['follow'] = this.follow;
    return data;
  }
}
