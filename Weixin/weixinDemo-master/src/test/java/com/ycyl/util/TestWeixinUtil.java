package com.ycyl.util;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;
import org.junit.Test;

import com.ycyl.po.AccessToken;


public class TestWeixinUtil {
	
	/**
	 * 测试获取AccessToken
	 */
	@Test
	public void getAccessTokenTest(){
		AccessToken at = WeixinUtil.getAccessToken();
		System.out.println(at);
	}
	
	/**
	 * 缩略图文件上传后，获取时的唯一标识
	 */
	@Test
	public void getThumbMediaIdTest(){
		AccessToken at = WeixinUtil.getAccessToken();
		try {
			String path = "src/main/resources/file/thumb.jpg";
			String mediaId = WeixinUtil.upload(path,at.getToken(), Constants.MESSAGE_THUMB);
			System.out.println(mediaId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片文件上传后，获取时的唯一标识
	 */
	@Test
	public void getImageMediaIdTest(){
		AccessToken at = WeixinUtil.getAccessToken();
		try {
			String path = "src/main/resources/file/image.jpg";
			String mediaId = WeixinUtil.upload(path,at.getToken(), Constants.MESSAGE_IMAGE);
			System.out.println(mediaId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建菜单
	 */
	@Test
	public void createMenuTest(){
		AccessToken at = WeixinUtil.getAccessToken();
		try {
			String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			int errcode = WeixinUtil.createMenu(at.getToken(),menu);
			if(errcode == 0){
				System.out.println("菜单创建成功！");
			} else {
				System.out.println("错误码："+errcode);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询菜单
	 */
	@Test
	public void queryMenuTest(){
		AccessToken at = WeixinUtil.getAccessToken();
		try {
			JSONObject jo = WeixinUtil.queryMenu(at.getToken());
			System.out.println(jo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除菜单
	 */
	@Test
	public void deleteMenuTest(){
		AccessToken at = WeixinUtil.getAccessToken();
		try {
			int result = WeixinUtil.deleteMenu(at.getToken());
			if(result == 0){
				System.out.println("菜单删除成功！");
			}else{
				System.out.println("错误码："+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
