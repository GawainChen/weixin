package com.ycyl.po;

import java.util.List;

/**
 * 图文消息
 * @author 梅波
 *
 */
public class NewsMessage extends BaseMessage{
	private int ArticleCount;
	private List<News> Articles;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
}
