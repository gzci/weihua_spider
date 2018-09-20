package com.spider.ci;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spider.bean.ci.pricenews;
import com.spider.util.C3p0Utils;
import com.spider.util.Dao;

public class wxprice {
	private static List<pricenews> artl = new ArrayList<pricenews>();
	private static List<Integer> idl = new ArrayList<Integer>();
	private static int numid=0;
	private static final Logger log = LoggerFactory.getLogger(wxprice.class);

	public static void main() throws SQLException {
		log.info("++++++++++++开始处理++++++++++");
			selectwx();
			if (artl.size() != 0) {
				for (pricenews o : artl) {
					Dao.insertPriceNews(o);
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

				String article_title = rs.getString("article_title").replaceAll("<[^>]*>", "").trim();
				if(!article_title.contains("光伏产业链一周价格汇总")&&!article_title.contains("多晶硅报价")){
					continue;
				}
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
				String md5=rs.getString("__pk");
				String weixin_nickname = rs.getString("weixin_nickname");
				String article_publish_time = rs.getString("article_publish_time");
				article_content = rs.getString("article_content");
				pricenews o=new pricenews();
				o.setContent(article_content);
				o.setProcessedContent(article_content);
				o.setPublishTime(new java.sql.Timestamp(Long.parseLong(article_publish_time) * 1000).toString().split(" ")[0]);
				o.setTitle(article_title);
				o.setSourceName(weixin_nickname);
				o.setMd5(md5);
				artl.add(o);
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
