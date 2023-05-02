import 'package:flutter/material.dart';

class customInputField extends StatefulWidget{

  Icon fieldIcon;
  String hintText;
  final controller;
  final main_Color;


  customInputField(this.fieldIcon,this.hintText, this.controller, this.main_Color);

  @override
  State<customInputField> createState() => _customInputFieldState();
}

class _customInputFieldState extends State<customInputField> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
      width: 300,
      child: Material(
          elevation: 5.0,
          borderRadius: BorderRadius.all(Radius.circular(10.0)),
          color: widget.main_Color,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.all(13),
                child: widget.fieldIcon,
              ),
              Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.only(topRight: Radius.circular(10.0),bottomRight: Radius.circular(10.0)),
                ),
                width: 250,
                height: 60,
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: TextField(
                    controller: widget.controller,
                    obscureText: (widget.hintText=="PASSWORD"||widget.hintText=="PW"?true:false),
                    decoration: InputDecoration(
                        border: InputBorder.none,
                        hintText: widget.hintText,
                        fillColor: Colors.white,
                        filled: true
                    ),
                    style: TextStyle(
                        fontSize: 20.0,
                        color: Colors.black
                    ),
                  ),
                ),
              ),
            ],
          )
      ),
    );
  }
}