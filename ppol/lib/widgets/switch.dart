// import 'package:flutter/cupertino.dart';
// import 'package:flutter/material.dart';
//
// class SwitchExample extends StatefulWidget {
//   const SwitchExample({super.key});
//
//   @override
//   State<SwitchExample> createState() => _SwitchExampleState();
// }
//
// class _SwitchExampleState extends State<SwitchExample> {
//   bool light0 = true;
//   bool light1 = true;
//   bool light2 = true;
//
//   final MaterialStateProperty<Icon?> thumbIcon =
//   MaterialStateProperty.resolveWith<Icon?>(
//         (Set<MaterialState> states) {
//       // Thumb icon when the switch is selected.
//       if (states.contains(MaterialState.selected)) {
//         return Icon(Icons.check);
//       }
//       return Icon(Icons.close);
//     },
//   );
//
//   @override
//   Widget build(BuildContext context) {
//     return Column(
//       mainAxisAlignment: MainAxisAlignment.center,
//       children: <Widget>[
//         Switch(
//           value: light0,
//           onChanged: (bool value) {
//             setState(() {
//               light0 = value;
//             });
//           },
//         ),
//         Switch(
//           // thumbIcon: thumbIcon,
//           thumbIcon: thumbIcon,
//           value: light1,
//           onChanged: (bool value) {
//             setState(() {
//               light1 = value;
//             });
//           },
//         ),
//       ],
//     );
//   }
// }
