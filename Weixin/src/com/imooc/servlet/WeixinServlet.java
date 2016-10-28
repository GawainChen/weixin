package com.imooc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.dom4j.DocumentException;

import com.imooc.po.TextMessage;
import com.imooc.util.CheckUtil;
import com.imooc.util.MessageUtil;

/**
 * Servlet implementation class WeixinServlet
 */
@WebServlet("/WeixinServlet")
public class WeixinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("ToUserName");
			String toUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = null;
			if ("text".equals(msgType)) {
				TextMessage text = new TextMessage();
				text.setFromUserName(fromUserName);
				text.setToUserName(toUserName);
				text.setMsgType("text");
				text.setCreateTime(new Date().getTime());
				text.setContent("您输入的消息是：" + content);
				message = MessageUtil.textMessageToXml(text);

				System.out.println(message);
			}
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}
