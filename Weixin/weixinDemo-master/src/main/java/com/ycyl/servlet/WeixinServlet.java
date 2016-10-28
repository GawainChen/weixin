package com.ycyl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ycyl.util.CheckUtil;
import com.ycyl.util.Constants;
import com.ycyl.util.MessageUtil;

public class WeixinServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
		String signature = req.getParameter("signature"); 
		//时间戳
		String timestamp = req.getParameter("timestamp");
		//随机数
		String nonce = req.getParameter("nonce");
		//随机字符串
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		//校验签名
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//设置编码格式
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		//解析请求传递过来的xml数据
		Map<String,String> map = MessageUtil.xmlToMap(req);
		//获得客户端传递的数据
		String toUserName = map.get("ToUserName");
		String fromUserName = map.get("FromUserName");
		String msgType = map.get("MsgType");
		String content = map.get("Content");
		
		String message = null;
		PrintWriter out = resp.getWriter();
		if(Constants.MESSAGE_TEXT.equals(msgType)){
			if("1".equals(content)){
				//回复图文消息
				message = MessageUtil.initNewsMessage(toUserName, fromUserName);
			} else if("2".equals(content)){
				//回复图片消息
				message = MessageUtil.initImageMessage(toUserName, fromUserName);
			} else if("3".equals(content)){
				//回复音乐消息
				message = MessageUtil.initMusicMessage(toUserName, fromUserName);
			}else {
				//回复文本消息
				message = MessageUtil.initMessage(toUserName, fromUserName, "你发送的内容为："+content, Constants.MESSAGE_TEXT);
			}
		}else if(Constants.MESSAGE_EVENT.equals(msgType)){
			String eventType = map.get("Event");
			if(Constants.MESSAGE_SUBSCRIBE.equals(eventType)){
				message = MessageUtil.initMessage(toUserName, fromUserName, MessageUtil.menuText(),Constants.MESSAGE_TEXT);
			}else if(Constants.MESSAGE_CLICK.equals(eventType)){
				message = MessageUtil.initMessage(toUserName, fromUserName, MessageUtil.menuText(),Constants.MESSAGE_TEXT);
			}else if(Constants.MESSAGE_VIEW.equals(eventType)){
				String url = map.get("EventKey");
				message = MessageUtil.initMessage(toUserName, fromUserName, url,Constants.MESSAGE_TEXT);
			}else if(Constants.MESSAGE_SCANCODE.equals(eventType)){
				String key = map.get("EventKey");
				message = MessageUtil.initMessage(toUserName, fromUserName, key,Constants.MESSAGE_TEXT);
			}
		}else if(Constants.MESSAGE_LOCATION.equals(msgType)){
			String label = map.get("Label");
			message = MessageUtil.initMessage(toUserName, fromUserName,label,Constants.MESSAGE_TEXT);
		}
		out.print(message);
		out.close();
	}
}
