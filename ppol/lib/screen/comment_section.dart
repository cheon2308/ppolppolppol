// import 'package:flutter/material.dart';
// import 'package:ppol/models/articleModel.dart';
//
// class CommentSection extends StatefulWidget {
//   final int? articleId;
//   final int? likeCount;
//   final List<CommentModel>? comments;
//   const CommentSection({
//     Key? key,
//     required this.articleId,
//     required this.likeCount,
//     required this.comments,
//   }) : super(key: key);
//
//   @override
//   _CommentSectionState createState() => _CommentSectionState();
// }
//
// class _CommentSectionState extends State<CommentSection> {
//   List<CommentModel>? _comments = [];
//
//   @override
//   void initState() {
//     super.initState();
//     _comments = widget.comments ?? [];
//   }
//
//
//
//   void _addComment(String content) {
//     setState(() {
//       final newComment = CommentModel(
//         id: _comments.length + 1,
//         articleId: widget.articleId,
//         content: content,
//         writer: UserModel(id: 1, username: 'example_user'),
//         createdAt: DateTime.now(),
//       );
//       _comments.add(newComment);
//     });
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     return Column(
//       crossAxisAlignment: CrossAxisAlignment.stretch,
//       children: [
//         Padding(
//           padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
//           child: Row(
//             mainAxisAlignment: MainAxisAlignment.spaceBetween,
//             children: [
//               Row(
//                 children: [
//                   IconButton(
//                     icon: Icon(Icons.favorite_border),
//                     onPressed: _incrementLikeCount,
//                   ),
//                   Text('$_likeCount'),
//                 ],
//               ),
//               IconButton(
//                 icon: Icon(Icons.share),
//                 onPressed: () {},
//               ),
//             ],
//           ),
//         ),
//         Divider(height: 1, color: Colors.grey[300]),
//         Expanded(
//           child: ListView.builder(
//             itemCount: _comments.length,
//             itemBuilder: (context, index) {
//               final comment = _comments[index];
//               return ListTile(
//                 leading: CircleAvatar(
//                   child: Text(comment.writer.userId),
//                 ),
//                 title: Text(comment.writer.username),
//                 subtitle: Text(comment.content),
//                 trailing: Text(
//                   DateFormat('MM/dd HH:mm').format(comment.createdAt),
//                   style: TextStyle(fontSize: 12),
//                 ),
//               );
//             },
//           ),
//         ),
//         Divider(height: 1, color: Colors.grey[300]),
//         Padding(
//           padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
//           child: CommentInputField(
//             onSubmitted: _addComment,
//           ),
//         ),
//       ],
//     );
//   }
// }