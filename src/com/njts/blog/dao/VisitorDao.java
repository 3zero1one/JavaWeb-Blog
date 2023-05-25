package com.njts.blog.dao;

import com.njts.blog.utils.ArticleUtils;
import com.njts.blog.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class VisitorDao {
    /**
     * ͳ�����з��ʵĴ���
     * @return
     * @throws SQLException
     */
    public static int totalVisit() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "SELECT COUNT(id) FROM t_visitor;";
        Object query = qr.query(sql, new ScalarHandler<>());
        int count = Integer.parseInt(String.valueOf(query));
        return count;
    }

    /**
     * ͳ�Ʒÿ͵ĸ�����ͬһip��η�����һ��
     * @return
     * @throws SQLException
     */
    public static Object totalMember() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "SELECT COUNT(DISTINCT(ip)) FROM t_visitor;";
        Object query = qr.query(sql, new ScalarHandler<>());
        int count = Integer.parseInt(String.valueOf(query));
        return count;
    }

    public static void visit(HttpServletRequest req) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        String remoteAddr = req.getRemoteAddr();// �õ������ߵ�IP��ַ
        String localAddr = req.getLocalAddr(); // ��ȡWEB��������IP��ַ
        String remoteHost = req.getRemoteHost();
        String time = ArticleUtils.timeOfCreate();

        String sql = "insert into t_visitor values(null,?,?,?,?)";
        qr.execute(sql, remoteAddr, time, localAddr, remoteHost);
    }
}
