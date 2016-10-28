package com.ycyl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.ycyl.po.Image;
import com.ycyl.po.ImageMessage;
import com.ycyl.po.Music;
import com.ycyl.po.MusicMessage;
import com.ycyl.po.News;
import com.ycyl.po.NewsMessage;
import com.ycyl.po.TextMessage;

/**
 * 消息转换类
 * @author 梅波
 *
 */
public class MessageUtil {
	
	/**
	 * xml转map集合
	 * @param req
	 * @return
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest req){
		Map<String,String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		try {
			//从request中获取输入流
			InputStream ins = req.getInputStream();
			
			//获取xml元素
			Document doc = reader.read(ins);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements();

			//遍历根节点元素集合，加入map集合
			for(Element e: list){
				map.put(e.getName(), e.getText());
			}
			
			//关闭输入流
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 将文本消息对象转换为xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		//xstream提供了一些对象类型转xml的方法
		XStream xstream = new XStream();
		
		//将根元素替换为xml，不然返回给客户端的就是一个TextMessage的全路径，com.ycyl.po.TextMessage
		xstream.alias("xml", textMessage.getClass());
		
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 图文消息转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		//System.out.println(xstream.toXML(newsMessage));
		return xstream.toXML(newsMessage);
	}
	
	/**
	 * 图片消息转为xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		xstream.alias("Image", new Image().getClass());
		//System.out.println(xstream.toXML(imageMessage));
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 音乐消息转为xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	
	/**
	 * 初始化回复内容
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @param msgType
	 * @return
	 */
	public static String initMessage(String toUserName,String fromUserName,
			String content,String msgType){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setContent(content);
		text.setMsgType(msgType);
		text.setCreateTime(new Date().getTime());
		//将文本消息对象转换为xml，并返回一个消息字符串
		return textMessageToXml(text);
	}
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注!");
		return sb.toString();
	}
	
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("慕课网介绍");
		news.setDescription("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。");
		news.setPicUrl("http://mb999.tunnel.qydev.com/WeixinDemo/source/image/imooc.jpg");
		news.setUrl("www.imooc.com");
		
		newsList.add(news);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(Constants.MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("8fE5JRCAT8oo_VzAFWRmoyfGbL4xbvGMnIf67ylxwyk3NemwV5km0bIyiaAldE36");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(Constants.MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	/**
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("HYEPSHv-G8--SkcII2LIAJgsY-2w6MdTuUmf62EJoPacL0aTT737qHuqGVKaDNzd");
		music.setTitle("see you again");
		music.setDescription("速7片尾曲");
		music.setMusicUrl("http://mb999.tunnel.qydev.com/WeixinDemo/source/music/See You Again.mp3");
		music.setHQMusicUrl("http://mb999.tunnel.qydev.com/WeixinDemo/source/music/See You Again.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(Constants.MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
}
