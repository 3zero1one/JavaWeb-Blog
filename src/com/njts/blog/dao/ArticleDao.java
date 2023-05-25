package com.njts.blog.dao;

import com.njts.blog.bean.Article;
import com.njts.blog.bean.Sort;
import com.njts.blog.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ArticleDao {

    /**
     * ��i�ж���ȡǰ������»��Ǻ��������
     * @param time
     * @param i
     * @return
     */
    public static Article getANearArticle(String time, int i) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = null;
        if (i == 1) {
            sql = " SELECT  * FROM t_article WHERE TIME < '" + time + "'  ORDER BY TIME DESC ";
        } else if (i == 2) {
            sql = " SELECT  * FROM t_article WHERE TIME > '" + time + "'  ORDER BY TIME ";
        }
        return qr.query(sql, new BeanHandler<>(Article.class));
    }

    /**
     * ��Ϊ��֪����ô�Ѳ�ѯ�����ķ������ƺ͸��������µ����·��أ������Զ�����һ��bean��sort
     * Ȼ��ѷ��صĽ����װ��map������
     * @return
     * @throws SQLException
     */
    public List<Sort> getColumAndCount() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select sort, count(sort) as counts from t_article group by sort;";
        return qr.query(sql, new BeanListHandler<>(Sort.class));
    }

    /**
     * ��ȡ���е�����
     * @return
     * @throws SQLException
     */
    public List<Article> getAllArticle() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "SELECT * FROM t_article;";
        return qr.query(sql,new BeanListHandler<>(Article.class));
    }

    public int getArticleCount() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "SELECT COUNT(*) FROM t_article;";
        Object query = qr.query(sql, new ScalarHandler<>());
        int count = Integer.parseInt(String.valueOf(query));
        return count;
    }

    public int getSortCount() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "SELECT COUNT(DISTINCT(sort)) FROM t_article;";
        Object query = qr.query(sql, new ScalarHandler<>());
        int count = Integer.parseInt(String.valueOf(query));
        return count;
    }

    /**
     *�����Ķ�������õ���������
     * @return
     * @throws SQLException
     */
    public List<Article> getVisitRank() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "SELECT * FROM t_article ORDER BY visit DESC;";
        return qr.query(sql,new BeanListHandler<>(Article.class));
    }

    /**
     * ��ȡ���еķ���
     * ��֪��new����Ӧ�����Ǹ���������ColumnListHandler��Ȼ���ˡ�����
     * @return
     * @throws SQLException
     */
    public List<String> getAllSort() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select distinct(sort) from t_article order by sort; ";
        return qr.query(sql, new ColumnListHandler<>());
    }

    /**
     * ���ݷ�������ѯ���£�����һ�����µ��б�
     * @param sort
     * @return
     * @throws SQLException
     */
    public List<Article> getArticleByColumnOfSort(String sort) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select * from t_article where sort = ?; ";
        return qr.query(sql, new BeanListHandler<>(Article.class), sort);
    }

    public List<Article> getArticleByColumnOfId(String value) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select * from t_article where id = ?; ";
        return qr.query(sql, new BeanListHandler<>(Article.class), value);
    }

    public Article addArticle(Article article) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "insert into t_article values(null,?,?,?,?,?,?,?,?)";
        qr.execute(sql, article.getTitle(), article.getAuthor(), article.getSort(),
                article.getTime(), article.getStar(), article.getComment(),
                article.getVisit(), article.getContent());
        // ֱ�ӷ������µ����£�Ҳ��������ռ����
        return this.getLatestArticle();
    }

    private Article getLatestArticle() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "SELECT * FROM t_article ORDER BY TIME DESC LIMIT 1";
        return qr.query(sql, new BeanHandler<>(Article.class));
    }

    public void deleteArticle(String id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "delete from t_article where id=?";
        qr.execute(sql, id);
    }

    public void updateSort(String old_sort, String new_sort) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "UPDATE t_article SET sort=? WHERE sort=?";
        qr.execute(sql, new_sort, old_sort);
    }

    public List<Article> getToDeleteArticleBySort(String sort) throws SQLException {
        // �ҵ���������µ�����,֮�����service���ƶ���t_article_delete
        // ��������Ϊ�˷�ֹ��ɾ��û������Լ�˵��
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "SELECT * FROM t_article where sort = ?";
        return qr.query(sql, new BeanListHandler<>(Article.class), sort);
    }

    public void deleteSort(String sort) throws SQLException {
        // ����ɾ������
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "delete from t_article where sort = ?";
        qr.execute(sql, sort);
    }

    public void addArticleOfDeleted(Article article) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "insert into t_article_delete values(null,?,?,?,?,?,?,?,?)";
        qr.execute(sql, article.getTitle(), article.getAuthor(), article.getSort(),
                article.getTime(), article.getStar(), article.getComment(),
                article.getVisit(), article.getContent());
    }

    public int star_article(int id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "update t_article set star=star+1 where id= ?;";
        qr.execute(sql, id);

        String sql2 = "select star from t_article where id= ?;";
        return qr.query(sql2, new ScalarHandler<Integer>(), id);
    }

    public void addVisit(int article_id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "update t_article set visit = visit+1 where id = " + article_id;
        qr.execute(sql);
    }
}
