package com.spider.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spider.bean.Article;
import com.spider.util.C3p0Utils;
import com.spider.util.Dao;
import com.spider.util.ReplaceBlank;

import Main.AbstractFiles;
@Component
public class wx implements Runnable{
	private static List<Article> artl = new ArrayList<Article>();
	private static List<Integer> idl = new ArrayList<Integer>();
	private static int numid=1;
	private static final Logger log = LoggerFactory.getLogger(wx.class);
	

	@Override
	public void run() {
		log.info("++++++++++++开始处理++++++++++");
			selectwx();
			if (artl.size() != 0) {
				for (Article o : artl) {
					try {
						Dao.insertNews(o, 10);
					} catch (SQLException e) {
						log.error(e.getMessage());
					}
				}
				// 微信占用机器号10
				updatewx();
				log.info("处理到id为" + idl.get(idl.size() - 1));
			}
	}

	public static void updatewx() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			// 通过JdbcUtil获取数据库链接
			conn = C3p0Utils.getConnection();
			conn.setAutoCommit(false);
			stat = conn.prepareStatement("update tab_wx set __version=1 where id=? limit 1 ");
			for (int o : idl) {
				stat.setInt(1, o);
				stat.addBatch();
			}
			stat.executeBatch();
			conn.commit();
			log.info("更新完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择微信文章数据
	 * 
	 * @param url
	 * @return
	 */
	public static void selectwx() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		artl.clear();

		try {
			// 通过JdbcUtil获取数据库链接
			conn = C3p0Utils.getConnection();
			stat = conn.prepareStatement("select * from tab_wx  where __version=0  and id>? ORDER BY id limit 1,20");
			String article_content = null;
			if (idl.size() != 0) {
				numid = idl.get(idl.size() - 1);
			}
			stat.setInt(1, numid);
			rs = stat.executeQuery();
			idl.clear();
			while (rs.next()) {
				int id = rs.getInt("id");
				idl.add(id);
				String _time = rs.getString("__time");

				String article_title = rs.getString("article_title").replaceAll("<[^>]*>", "").trim();
				if (article_title.contains("document.write")) {
					article_title = article_title
							.substring(article_title.indexOf("write(") + 7, article_title.length() - 3)
							.replace("\");", "");
				}
				if (article_title.contains("document.write")) {
					article_title = article_title
							.substring(article_title.indexOf("write(") + 7, article_title.length() - 3)
							.replace("\");", "");
				}
				String article_author = rs.getString("article_author");
				String weixin_name = rs.getString("weixin_name");
				String weixin_nickname = rs.getString("weixin_nickname");
				String article_publish_time = rs.getString("article_publish_time");
				String article_brief = rs.getString("article_brief");
				article_content = rs.getString("article_content");
				String __pk = rs.getString("__pk");
				String is_original = rs.getString("is_original");
				Article art = new Article();
				art.setMd5_url(__pk);
				art.setAuthor(article_author);
				art.setBrief(article_brief);
				art.setContent(article_content);
				art.setPublish_time(new java.sql.Timestamp(Long.parseLong(article_publish_time) * 1000));
				art.setTitle(article_title);
				art.setSource_name(weixin_name);
				art.setSource_name(weixin_nickname);
				if (is_original.equals("是")) {
					art.setOrigin_flag(1);
				} else if (is_original.equals("否")) {
					art.setOrigin_flag(0);
				} else {
					art.setOrigin_flag(-1);// 未知
				}
				art.setFrom_type(1);// 类型神箭手

				if (!"".equals(weixin_nickname) && weixin_nickname != null) {
					art.setHit_tag("微信公众号*0#" + weixin_nickname + "*1");
				} else {
					art.setHit_tag("微信公众号*0");
				}
				art.setCategory_id(0);
				art.setIs_oversea(0);
				artl.add(art);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {

			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.info(e.getMessage());
			}
		}

	}
}
