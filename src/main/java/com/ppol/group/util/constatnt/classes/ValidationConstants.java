package com.ppol.group.util.constatnt.classes;

/**
 * 	서비스의 다양한 Validation 상수들을 모아서 관리하는 Class
 */
public class ValidationConstants {

	// 게시글/피드의 최대 글자 수
	public static final int ARTICLE_CONTENT_MAX_SIZE = 2000;

	// 댓글의 최대 글자 수
	public static final int COMMENT_CONTENT_MAX_SIZE = 500;

	// 그룹 이름의 최대 글자 수
	public static final int GROUP_TITLE_MAX_SIZE = 20;

	// 사용자 닉네임 최대 글자 수
	public static final int USERNAME_MAX_SIZE = 16;

	// 사용자 소개 최대 글자 수
	public static final int USER_INTRO_MAX_SIZE = 50;


	// 개인 방의 최대 앨범 수
	public static final int ROOM_MAX_ALBUM = 10;

	// 앨범 제목 최대 글자 수
	public static final int ALBUM_TITLE_MAX_SIZE = 30;

	// 앨범 소개 글 최대 글자 수
	public static final int ALBUM_INTRO_MAX_SIZE = 1000;

	// 앨범 콘텐츠 최대 글자 수
	public static final int ALBUM_CONTENT_MAX_SIZE = 1000;


	// 퀴즈 최대 글자 수
	public static final int QUIZ_MAX_SIZE = 200;


	// 한 사람이 만들 수 있는 최대 그룹 수
	public static final int USER_CREATE_GROUP_MAX = 5;

	// 그룹 최대 인원 수
	public static final int GROUP_USER_MAX = 6;
}
