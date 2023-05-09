import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:line_awesome_flutter/line_awesome_flutter.dart';
import 'package:ppol/login/constancts.dart';


class ProfileListItem extends StatefulWidget {
  final IconData icon;
  final String text;
  final bool hasNavigation;
  final Function fun;

  const ProfileListItem({Key? key,required this.icon, required this.text, this.hasNavigation = true, required this.fun}) : super(key: key);

  @override
  State<ProfileListItem> createState() => _ProfileListItemState();
}

class _ProfileListItemState extends State<ProfileListItem> {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: kSpacingUnit.w * 5.5,
      margin: EdgeInsets.symmetric(
        horizontal: kSpacingUnit.w * 4,
      ).copyWith(
        bottom: kSpacingUnit.w * 2,
      ),
      child: InkWell(
        onTap: () {
          widget.fun();
        },
        borderRadius: BorderRadius.circular(kSpacingUnit.w * 3),
        child: Container(
          // height: kSpacingUnit.w * 5.5,
          // margin: EdgeInsets.symmetric(
          //   horizontal: kSpacingUnit.w * 4,
          // ).copyWith(
          //   bottom: kSpacingUnit.w * 2,
          // ),
          padding: EdgeInsets.symmetric(
            horizontal: kSpacingUnit.w * 2,
          ),
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(kSpacingUnit.w * 3),
            color: Theme.of(context).backgroundColor,
          ),
          child: Row(
            children: <Widget>[
              Icon(
                this.widget.icon,
                size: kSpacingUnit.w * 2.5,
              ),
              SizedBox(width: kSpacingUnit.w * 1.5),
              Text(
                this.widget.text,
                style: kTitleTextStyle.copyWith(
                  fontWeight: FontWeight.w500,
                ),
              ),
              Spacer(),
              if (this.widget.hasNavigation)
                Icon(
                  LineAwesomeIcons.angle_right,
                  size: kSpacingUnit.w * 2.5,
                ),
            ],
          ),
        ),
      ),
    );
  }
}