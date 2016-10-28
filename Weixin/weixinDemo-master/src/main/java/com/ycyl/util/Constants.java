package com.ycyl.util;

public class Constants {
	//token
	public static final String TOKEN = "ycyl";
	
	//消息类型
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_THUMB = "thumb";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";
	
	//开发者ID
	public static final String WX_APPID = "wxa80fcab32a115fa8"; //应用id
	public static final String WX_APPSECRET = "2b5caa27aa50fb669f16fb6cd0247451"; //应用密钥
	public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static final String WX_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	public static final String WX_CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public static final String WX_QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	public static final String WX_DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
}
