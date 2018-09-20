package com.spider.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spider.bean.Article;
import com.spider.bean.Fethcount;
import com.spider.bean.Seed;

public class Daopaging {
	private static final Logger log = LoggerFactory.getLogger(Daopaging.class);
	
	public static Seed saveCrawlstatus(String ip) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Seed seed = new Seed();
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			stat = conn.prepareStatement("INSERT INTO crawl_status(ip,heart_time) values (?,NOW()) ON DUPLICATE KEY UPDATE heart_time=NOW()");
			stat.setString(1, ip);
			stat.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//log.info(e);
			}
		}
		return seed;
	}
	public static Seed updatePostIp(String ip) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Seed seed = new Seed();
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			stat = conn.prepareStatement("INSERT INTO post_ip (`ip`, `id`, `update_time`) VALUES (?, 1, now());");
			stat.setString(1, ip);
			stat.executeUpdate();
			stat = conn.prepareStatement("update post_ip set ip=? where id=1");
			stat.setString(1, ip);
			stat.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//log.info(e);
			}
		}
		return seed;
	}
	
	public static int checkPostIp(String ip) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			stat = conn.prepareStatement("select count(*) from post_ip where ip=?");
			stat.setString(1, ip);
			rs = stat.executeQuery();
			boolean result = rs.next();
			if (result) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//log.info(e);
			}
		}
		return count;
	}
	public static int checkurl(String md5_url) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			stat = conn.prepareStatement("select count(*) from article_self_crawler where md5_url=? limit 1");
			stat.setString(1, md5_url);
			rs = stat.executeQuery();
			boolean result = rs.next();
			if (result) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//log.info(e);
			}
		}
		return count;
	}

	public static Seed selectSeedlist() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Seed seed = null;
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			stat = conn.prepareStatement("select * from seed_2 where  flag=1 limit 1");
			// stat = conn.prepareStatement("select * from seed");
			rs = stat.executeQuery();
			if (rs.next()) {
				seed = new Seed();
				seed.setId(rs.getLong("id"));
				seed.setRef_url(rs.getString("ref_url"));
				seed.setGet_time(rs.getTimestamp("get_time"));
				seed.setEnd_time(rs.getTimestamp("end_time"));
				seed.setTime_skip(rs.getLong("time_skip"));
				seed.setRefcss(rs.getString("refcss"));
				seed.setUrlcss(rs.getString("urlcss"));
				seed.setTitlecss(rs.getString("titlecss"));
				seed.setTimecss(rs.getString("timecss"));
				seed.setContcss(rs.getString("contcss"));
				seed.setSource_name(rs.getString("source_name"));
				seed.setCategory_id(rs.getInt("category_id"));
				seed.setBriefcss(rs.getString("briefcss"));
				seed.setIs_oversea(rs.getInt("is_oversea"));
				seed.setHit_tag(rs.getString("hit_tag"));
				seed.setFlag(rs.getInt("flag"));
				seed.setReftimecss(rs.getString("reftimecss"));
				seed.setReftitlecss(rs.getString("reftitlecss"));
				seed.setPagingcss(rs.getString("pagingcss"));
			}

		} catch (SQLException e) {
			log.error(e.getMessage());	
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//log.info(e);
			}
		}
		return seed;
	}

	public static Seed selectSeed() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Seed seed = null;
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			stat = conn.prepareStatement(
					"select * from seed_copy where flag=1 and category_id=0 and is_driver =1 and (NOW()-end_time>time_skip and get_time<=end_time or get_time is null) ORDER BY id  limit 1");
			
			rs = stat.executeQuery();
			boolean result = rs.next();
			if (result) {
				seed = new Seed();
				seed.setId(rs.getLong("id"));
				seed.setRef_url(rs.getString("ref_url"));
				seed.setGet_time(rs.getTimestamp("get_time"));
				seed.setEnd_time(rs.getTimestamp("end_time"));
				seed.setTime_skip(rs.getLong("time_skip"));
				seed.setUrlcss(rs.getString("urlcss"));
				seed.setTitlecss(rs.getString("titlecss"));
				seed.setTimecss(rs.getString("timecss"));
				seed.setContcss(rs.getString("contcss"));
				seed.setPagingcss(rs.getString("pagingcss"));
				seed.setSource_name(rs.getString("source_name"));
				seed.setCategory_id(rs.getInt("category_id"));
				seed.setRefcss(rs.getString("refcss"));
				seed.setReftimecss(rs.getString("reftimecss"));
				seed.setBriefcss(rs.getString("briefcss"));
				seed.setReftitlecss(rs.getString("reftitlecss"));
				seed.setIs_oversea(rs.getInt("is_oversea"));
				seed.setHit_tag(rs.getString("hit_tag"));
				seed.setFlag(rs.getInt("flag"));
				seed.setUrl_type(rs.getInt("url_type"));
				// System.out.println(seed.getId());
				Timestamp get_time = new Timestamp(new Date().getTime());
				seed.setGet_time(get_time);
				stat = conn.prepareStatement("update seed_copy set get_time=? where id=?");
				stat.setTimestamp(1, get_time);
				stat.setLong(2, seed.getId());
				stat.executeUpdate();
				// System.out.println("拿取成功");
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//log.info(e);
			}
		}
		return seed;
	}

	public static Timestamp updateSeed(String ref_url) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Timestamp end_time = null;
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			end_time = new Timestamp(new Date().getTime());
			stat = conn.prepareStatement("update seed_copy set end_time=? where ref_url=?");

			stat.setTimestamp(1, end_time);
			stat.setString(2, ref_url);
			stat.executeUpdate();
			// log.info("更改end_time成功");
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//log.info(e);
			}
		}
		return end_time;
	}
	public static void updateSeedCss(String ref_url, Seed o) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;

		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			//stat = conn.prepareStatement(
			//		"update seed_2 set urlcss=?,titlecss=?,timecss=?,contcss=?,pagingcss=?,refcss=?,reftimecss=?,reftitlecss=?,flag=8 where ref_url=?");
			stat = conn.prepareStatement(
					"update seed_2 set pagingcss=?,flag=88 where ref_url=?");
			stat.setString(1, o.getPagingcss());
			stat.setString(2, o.getRef_url());
			stat.executeUpdate();
			log.info("更改css成功");

		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public static void seedLog(String ref_url, String url, String erro) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			stat = conn.prepareStatement(
					"INSERT IGNORE INTO `seed_erro_log` (`ref_url`, `url`, `erro`,md5) VALUES (?, ?, ?,?)");
			stat.setString(1, ref_url);
			stat.setString(2, url);
			stat.setString(3, erro);
			stat.setString(4, MainUtil.getMD5(ref_url+url));
			stat.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//log.info(e);
			}
		}
	}

	public static void articleCount(Fethcount o) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			//
			stat = conn.prepareStatement("INSERT INTO `url_fetch_count`" + " (`ref_url`, `get_time`, `end_time`, `num`)"
					+ " VALUES ( ?, ?, ?, ?);");
			stat.setString(1, o.getRef_url());
			stat.setTimestamp(2, o.getGet_time());
			stat.setTimestamp(3, o.getEnd_time());
			stat.setInt(4, o.getNum());
			stat.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//log.info(e);
			}
		}
	}

	public static void insertNews(Article o, int machineId) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
			//
			stat = conn.prepareStatement("insert IGNORE into article_self_crawler "
					+ "(url, publish_time, title, fetch_time, content, author, source_name, id, brief, from_type, "
					+ "category_id, md5_url, hit_tag, is_oversea, ref_url,origin_flag,url_type) " + "values "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			stat.setString(1, o.getUrl());
			if(o.getPublish_time().compareTo(new Timestamp(System.currentTimeMillis()))>0){
				Daopaging.seedLog(o.getRef_url(), o.getUrl(), "time>now");
				throw new IllegalStateException(o.getUrl() + "-->time>now");
			}
			stat.setTimestamp(2, o.getPublish_time());
			stat.setString(3, o.getTitle());
			Date date = new Date();  
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	    
	        String fetch_time = format.format(date);
			stat.setString(4, fetch_time);
			stat.setString(5, o.getContent());
			stat.setString(6, o.getAuthor());
			stat.setString(7, o.getSource_name());
			long id=IdGenerator.getId(0, machineId);
			stat.setLong(8, id);
			stat.setString(9, o.getBrief());

			stat.setInt(10, o.getFrom_type());
			if (null==o.getCategory_id()) {
				stat.setInt(11, 0);
			} else {
				stat.setInt(11, o.getCategory_id());
			}
			stat.setString(12, o.getMd5_url());
			stat.setString(13, o.getHit_tag());
			if (null==o.getIs_oversea()) {
				stat.setInt(14, 0);
			} else {
				stat.setInt(14, o.getIs_oversea());
			}
			stat.setString(15, o.getRef_url());
			if (o.getOrigin_flag() == null) {
				stat.setInt(16, 0);
			} else {
				stat.setInt(16, o.getOrigin_flag());
			}
			if (o.getUrl_type() == null) {
				stat.setInt(17, 0);
			} else {
				stat.setInt(17, o.getUrl_type());
			}
			stat.executeUpdate();
			stat = conn.prepareStatement("INSERT INTO `article_source` (`id`, `html_source`) VALUES (?, ?)");
			stat.setLong(1, id);
			stat.setString(2, o.getHtml_source());
			
			stat.executeUpdate();
			
			// log.info("保存完成");
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//log.info(e);
			}
		}

	}

	public static void insertSeed(Seed o) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			for(int i=0;i<3;i++){
				if (conn != null) {
					break;
				}
			}
			if (conn == null) {
				throw new SQLException();
			}
		//	
			stat = conn.prepareStatement("insert IGNORE into seed "
					+ "(`ref_url`, `time_skip`,source_name,hit_tag) values " + "(?,?,?,?)");

			stat.setString(1, o.getRef_url());
			stat.setLong(2, o.getTime_skip());
			stat.setString(3, o.getSource_name());
			stat.setString(4, o.getHit_tag());
			stat.executeUpdate();
			// log.info("保存完成");
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//log.info(e);
				//
			}
		}

	}


}
