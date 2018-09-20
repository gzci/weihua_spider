package com.spider.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spider.bean.Article;
import com.spider.bean.Fethcount;
import com.spider.bean.Seed;
import com.spider.bean.enclosure;
import com.spider.bean.ci.GFmodel;
import com.spider.bean.ci.pricenews;
import com.spider.sendEmail.sendbean;

public class Dao {
	private static final Logger log = LoggerFactory.getLogger(Dao.class);
	
	public static String getNP() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String name_password=null;
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
			
			stat = conn.prepareStatement("select name,password from okcisold");
			rs = stat.executeQuery();
			if(rs.next()){
				name_password=rs.getString("name")+"#&#"+rs.getString("password");
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
		return name_password;
	}
	public static int numTab(){
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		int num=0;
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
			
			stat = conn.prepareStatement("select count(*) from tab_raw_bidd where url like 'http://www.okcis.cn/%' and update_time>'"+TimeT.getTimeD(0)+"'");
			rs = stat.executeQuery();
			boolean result = rs.next();
			if(result){
				num=rs.getInt(1);
			}
			stat = conn.prepareStatement("select count(*) from tab_put_on_record where url like 'http://www.okcis.cn/%' and update_time>'"+TimeT.getTimeD(0)+"'");
			rs = stat.executeQuery();
			result = rs.next();
			if(result){
				num+=rs.getInt(1);
			}
			stat = conn.prepareStatement("select count(*) from tab_rcc_project where url like 'http://www.okcis.cn/%' and update_time>'"+TimeT.getTimeD(0)+"'");
			rs = stat.executeQuery();
			result = rs.next();
			if(result){
				num+=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return num;
	}
	public static sendbean checkNum(sendbean o) {
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
			String tablecheck=o.getTablecheck();
			String date=o.getDate();
			String is_not=o.getIs_not();
			stat = conn.prepareStatement("SELECT count(*) FROM "+tablecheck+" where DATE_FORMAT(update_time,'%Y-%m-%d')='"+date+"' and url is "+is_not+" null GROUP BY DATE_FORMAT(update_time,'%Y-%m-%d')");
			rs = stat.executeQuery();
			if(rs.next()){
				o.setCount(rs.getLong(1));
			}else{
				o.setCount(0);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
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
		return o;
	}
	public static Seed saveCrawlstatus(String ip) {
		Connection conn = null;
		PreparedStatement stat = null;
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
			stat = conn.prepareStatement("INSERT IGNORE INTO crawl_status(ip,heart_time) values (?,NOW()) ON DUPLICATE KEY UPDATE heart_time=NOW()");
			stat.setString(1, ip);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
			stat = conn.prepareStatement("INSERT IGNORE INTO post_ip (`ip`, `id`, `update_time`) VALUES (?, 1, now());");
			stat.setString(1, ip);
			stat.executeUpdate();
			stat = conn.prepareStatement("update post_ip set ip=? where id=1");
			stat.setString(1, ip);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			stat = conn.prepareStatement("select * from seed where  flag=0 order by id  limit 1");
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
			e.printStackTrace();
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
	public static List<Seed> selectSeederro() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Seed> list = new LinkedList<Seed>();
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
					"select * from seed where LENGTH(refcss)!=0 and ref_url in (select ref_url from seed_erro_log where erro='url css erro' GROUP BY ref_url)");
			
			rs = stat.executeQuery();
			while (rs.next()) {
				Seed seed = new Seed();
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
				seed.setIs_driver(rs.getInt("is_driver"));
				seed.setIs_paging(rs.getInt("is_paging"));
				seed.setUrl_type(rs.getInt("url_type"));
				Timestamp get_time = new Timestamp(new Date().getTime());
				seed.setGet_time(get_time);
				stat = conn.prepareStatement("update seed set get_time=? where id=?");
				stat.setTimestamp(1, get_time);
				stat.setLong(2, seed.getId());
				stat.executeUpdate();
				stat = conn.prepareStatement(
						"delete from seed_erro_log where ref_url=?");
				stat.setString(1, seed.getRef_url());
				stat.executeUpdate();
				list.add(seed);
			}
			
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//log.info(e);
			}
		}
		return list;
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
					"select * from seed where flag=1 and (NOW()-end_time>time_skip and get_time<=end_time or get_time is null) ORDER BY id  limit 1");
			
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
				seed.setIs_driver(rs.getInt("is_driver"));
				seed.setIs_paging(rs.getInt("is_paging"));
				seed.setUrl_type(rs.getInt("url_type"));
				Timestamp get_time = new Timestamp(new Date().getTime());
				seed.setGet_time(get_time);
				stat = conn.prepareStatement("update seed set get_time=? where id=?");
				stat.setTimestamp(1, get_time);
				stat.setLong(2, seed.getId());
				stat.executeUpdate();
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
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
			stat = conn.prepareStatement("update seed set end_time=? where ref_url=?");

			stat.setTimestamp(1, end_time);
			stat.setString(2, ref_url);
			stat.executeUpdate();
			// log.info("更改end_time成功");
		} catch (SQLException e) {
			e.printStackTrace();
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

		try {
			// 通过C3p0Utils获取数据库链接
			conn = C3p0Utils.getConnection();
			stat = conn.prepareStatement(
					"update seed set urlcss=?,titlecss=?,timecss=?,contcss=?,pagingcss=?,refcss=?,reftimecss=?,reftitlecss=?,flag=8 where ref_url=?");
//			stat = conn.prepareStatement(
//					"update seed set pagingcss=?,flag=8 where ref_url=?");
//			stat.setString(1, o.getPagingcss());
//			stat.setString(2, o.getRef_url());
			stat.setString(1, o.getUrlcss());
			stat.setString(2, o.getTitlecss());
			stat.setString(3, o.getTimecss());
			stat.setString(4, o.getContcss());
			stat.setString(5, o.getPagingcss());
			stat.setString(6, o.getRefcss());
			stat.setString(7, o.getReftimecss());
			stat.setString(8, o.getReftitlecss());
			stat.setString(9, o.getRef_url());
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
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
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
			stat = conn.prepareStatement("INSERT IGNORE INTO `url_fetch_count`" + " (`ref_url`, `get_time`, `end_time`, `num`)"
					+ " VALUES ( ?, ?, ?, ?);");
			stat.setString(1, o.getRef_url());
			stat.setTimestamp(2, o.getGet_time());
			stat.setTimestamp(3, o.getEnd_time());
			stat.setInt(4, o.getNum());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	public static void insertNews(Article o, int machineId) throws SQLException {
		Connection conn = null;
		PreparedStatement stat = null;
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
				Dao.seedLog(o.getRef_url(), o.getUrl(), "time>now");
				throw new IllegalStateException(o.getUrl() + "-->time>now");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	    
		   
			stat.setString(2, sdf.format(o.getPublish_time()));
			stat.setString(3, o.getTitle());
			Date date = new Date();  
	      
	        String fetch_time = sdf.format(date);
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
			stat = conn.prepareStatement("INSERT IGNORE INTO `article_source` (`id`, `html_source`) VALUES (?, ?)");
			stat.setLong(1, id);
			stat.setString(2, o.getHtml_source());
			
			stat.executeUpdate();
			
			// log.info("保存完成");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}

	}

	public static void insertSeed(Seed o) {
		Connection conn = null;
		PreparedStatement stat = null;
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
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}

	}

	public static void insertNZJ(GFmodel o) {
		Connection conn = null;
		PreparedStatement stat = null;
		PreparedStatement stat2 = null;
		try {
			// 通过JdbcUtil获取数据库链接
			conn = C3p0Utils.getConnection();
			stat = conn.prepareStatement("insert IGNORE into tab_rcc_project "
					+ "(rowkey, project_name, project_number, project_type, project_stage, pub_time,proprietor,url,area,investment_amounts,project_remark,project_address,project_introduce,city) values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stat2 = conn.prepareStatement("insert IGNORE into tab_rcc_source "
					+ "(rowkey,page_source,processed_content) values "
					+ "(?,?,?)");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				stat.setString(1, o.getRowkey());
				stat.setString(2, o.getProject_name());
				stat.setString(3, o.getProject_number());
				stat.setString(4, o.getProject_type());
				stat.setString(5, o.getProject_stage());
				
				stat.setString(6, sdf.format(o.getPub_timed()));
				stat.setString(7, o.getProprietor());
				stat.setString(8, o.getUrl());
				stat.setString(9, o.getArea());
				stat.setLong(10, o.getInvestment_amounts());
				stat.setString(11, o.getProject_remark());
				stat.setString(12, o.getProject_address());
				stat.setString(13, o.getProject_introduce());
				stat.setString(14, o.getCity());
				stat2.setString(1, o.getRowkey());
				stat2.setString(2, o.getHtml());
				stat2.setString(3, o.getProcessed_content());
				stat2.executeUpdate();
				stat.executeUpdate();
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	public static void insertZZ(GFmodel o) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			// 通过JdbcUtil获取数据库链接
			conn = C3p0Utils.getConnection();
			stat = conn.prepareStatement("insert IGNORE into tab_raw_bidd "
					+ "(`title`, `url`, `context`, `pub_time`, `remark`, `anchor_text`,province,processed_content,city) values "
					+ "(?,?,?,?,?,?,?,?,?)");
				stat.setString(1, o.getProject_name());
				stat.setString(2, o.getUrl());
				stat.setString(3, o.getHtml());
				stat.setString(4,o.getPub_time());
				stat.setString(5, o.getProject_type());
				stat.setString(6, o.getProject_name());
				stat.setString(7, o.getArea());
				stat.setString(8, o.getProcessed_content());
				stat.setString(9, o.getCity());
				stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
		
	}
	public static void insertBA(GFmodel o) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			// 通过JdbcUtil获取数据库链接
			conn = C3p0Utils.getConnection();
			stat = conn.prepareStatement("insert IGNORE into tab_put_on_record "
					+ "(`title`, `url`, `context`, `time`, `remark`, `anchor_text`,area,processed_content,city) values "
					+ "(?,?,?,?,?,?,?,?,?)");
				stat.setString(1, o.getProject_name());
				stat.setString(2, o.getUrl());
				stat.setString(3, o.getHtml());
				stat.setString(4,o.getPub_time());
				stat.setString(5, o.getProject_type());
				stat.setString(6, o.getProject_name());
				stat.setString(7, o.getArea());
				stat.setString(8, o.getProcessed_content());
				stat.setString(9,o.getCity());
				stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
		
	}
	public static int checkurl(String url,String table) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			// 通过JdbcUtil获取数据库链接
			conn = C3p0Utils.getConnection();
			stat = conn.prepareStatement("select COUNT(*) num from "+table+" where url =? LIMIT 1");
			stat.setString(1, url);
			rs = stat.executeQuery();
			boolean result = rs.next();
			if (result) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
				
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
		return count;
	}
	public static void insertPriceNews(pricenews o) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		int count = 0;
		try {
			// 通过JdbcUtil获取数据库链接
			conn = C3p0Utils.getConnection();
			stat=conn.prepareStatement("select COUNT(*) num from tab_price_news where md5=?");
			stat.setString(1, o.getMd5());
			rs=stat.executeQuery();
			boolean result = rs.next();
			if (result) {
				count = rs.getInt(1);
			}
			if(count==1){
				log.info("已经存在");
				return;
			}
			stat = conn.prepareStatement("INSERT IGNORE INTO `tab_price_news` "
					+ "(`title`, `url`, `publish_time`, `source_name`, `content`, `processed_content`,md5) "
					+ "VALUES (?, ?, ?, ?, ?, ?,?);");
				stat.setString(1, o.getTitle());
				stat.setString(2, o.getUrl());
				stat.setString(3, o.getPublishTime());
				stat.setString(4,o.getSourceName());
				stat.setString(5, o.getContent());
				stat.setString(6, o.getProcessedContent());
				stat.setString(7, o.getMd5());
				stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
		
	}
	public static void insertEnclosure(enclosure o) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			// 通过JdbcUtil获取数据库链接
			conn = C3p0Utils.getConnection();
			stat = conn.prepareStatement("INSERT IGNORE INTO enclosure "
					+ "(`url`, `anchor_text`, `enclosure_url`, `oss_url`, `md5`, `download_code`, `erro_log`, `retry_num`, `content_type`, `postfix`) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stat.setString(1,o.getUrl());
			stat.setString(2,o.getAnchor_text());
			stat.setString(3,o.getEnclosure_url());
			stat.setString(4,o.getOss_url());
			stat.setString(5,o.getMd5());
			stat.setInt(6,o.getDownload_code());
			stat.setString(7,o.getErro_log());
			stat.setInt(8,o.getRetry_num());
			stat.setString(9,o.getContent_type());
			stat.setString(10,o.getPostfix());
			stat.executeUpdate();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
		
		
	}
}
