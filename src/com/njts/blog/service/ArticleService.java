package com.njts.blog.service;

import com.njts.blog.bean.Article;
import com.njts.blog.bean.AxisArticle;
import com.njts.blog.bean.Sort;
import com.njts.blog.dao.ArticleDao;
import com.njts.blog.utils.ArticleUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.*;

public class ArticleService {

    ArticleDao articleDao = new ArticleDao();

    /**
     * �����������£�1����ǰ�ģ�2�Ǻ����
     * @param time
     * @return
     * @throws SQLException
     */
    public static Article getPreviousArticle(String time) throws SQLException {
        return ArticleDao.getANearArticle(time, 1);
    }

    public static Article getNextArticle(String time) throws SQLException {
        return ArticleDao.getANearArticle(time, 2);
    }

    /**
     * �������ݿ��ȡ��article��sort����͸��������ݷ�װ��map����
     * @return
     * @throws SQLException
     */
    public Map getSortAndCount() throws SQLException {
        //�����ݿ��ȡ��article��sort����͸���������
        List<Sort> columAndCount = articleDao.getColumAndCount();

        //����map���ϣ��洢����
        Map<String, Integer> map = new HashMap<>();
        for(Sort sort : columAndCount){
            map.put(sort.getSort(),sort.getCounts());
        }
        return map;
    }

    /**
     * ������е����²��Ҵ���󷵻�
     * @return
     * @throws SQLException
     */
    public List getAllArticle() throws SQLException {
        List<Article> allArticle = articleDao.getAllArticle();

        //����һ����ʾ����ҳ���ϵ����£��������ݣ�ȥ��ʱ���ʱ����
        ArticleUtils.cutContent(allArticle);
        ArticleUtils.cutTime(allArticle);

        return allArticle;
    }

    /**
     * ������������
     * @return
     * @throws SQLException
     */
    public int getArticleCount() throws SQLException {
        int articleCount = articleDao.getArticleCount();
        return articleCount;
    }

    /**
     * ���ط�������
     * @return
     * @throws SQLException
     */
    public int getSortCount() throws SQLException {
        int sortCount = articleDao.getSortCount();
        return sortCount;
    }

    /**
     * ���ش������Ķ���������
     * @return
     * @throws SQLException
     */
    public List<Article> getVisitRank() throws SQLException {
        List<Article> visitRank = articleDao.getVisitRank();

        // �������������10�Ͳ�����ʾ
        if (visitRank.size() > 10) {
            for (int i = 10; i < visitRank.size(); i++) {
                visitRank.remove(i);
            }
        }
        // ��ȥ������Ŀ����10���ֵĲ���
        for (int y = 0; y < visitRank.size(); y++) {
            Article article = visitRank.get(y);
            if (article.getTitle().length() > 20) {
                article.setTitle(ArticleUtils.cutTitle(article.getTitle(), 0, 19) + "...");
            }
        }

        return visitRank;
    }

    /**
     * �ѷ������͸÷����µ����·�װ��һ��map�����У�sort_name�ǲ�ͬ�ķ�������all�������з�����
     * @return
     * @throws SQLException
     */
    public Map<String, List<Article>> getSortAndArticle(String sort_name) throws SQLException {
        Map<String, List<Article>> map = new HashMap<>();
        // ��ȡ���з���
        if (sort_name.equals("all") || StringUtils.isEmpty(sort_name)) {
            List<String> allSort = articleDao.getAllSort();

            for (int i = 0; i < allSort.size(); i++) {
                String sort = allSort.get(i);
                List<Article> articleByColumn = articleDao.getArticleByColumnOfSort(sort);
                ArticleUtils.cutTime(articleByColumn);
                map.put(sort, articleByColumn);
            }
        } else {
            // ��ȡ��������
            List<Article> articleByColumn = articleDao.getArticleByColumnOfSort(sort_name);
            ArticleUtils.cutTime(articleByColumn);
            map.put(sort_name, articleByColumn);
        }
        return map;
    }

    public List<Article> getArticle(String value) throws SQLException {
        return articleDao.getArticleByColumnOfId(value);
    }

    public List<AxisArticle> getAxisList() throws SQLException {
        // 1.��ȡ���ݿ��У���������
        List<Article> allArticle = articleDao.getAllArticle();

        // 2.�������ʱ��������
        List<AxisArticle> axisArticleList = new ArrayList<>();

        // 3.������Article���AxisArticle����
        for (Article article : allArticle) {
            AxisArticle axisArticle = ArticleUtils.changeToAxisArticle(article);
            axisArticleList.add(axisArticle);
        }

        /**
         * ���￪ʼ�������� ���������� 2018-2017-2016 ʱ�併��
         * ��ΪҪʵ�� ����+����+year ����+����+year��Ч�� �����year��װ��һ�������AxisArticle����
         * id=0 year = ���½�������
         * Ȼ��ȫ������ result ��
         * ��jsp�ж�id==0 true: year��� false: ���AxisArticle�����
         */
        AxisArticle tmpAxisArticle = null;
        List result = new LinkedList();
        // ����ȥ���µ�һ����� ���������һ��AxisArticle
        if (!axisArticleList.isEmpty()) {
            tmpAxisArticle = new AxisArticle();
            tmpAxisArticle.setId(0);
            tmpAxisArticle.setYear(axisArticleList.get(0).getYear());
            result.add(tmpAxisArticle);
            result.add(axisArticleList.get(0));
        }
        // �ж���������ǲ��ǲ�һ�� ��һ������һ��year
        for (int i = 1; i < axisArticleList.size(); i++) {
            int present_year = axisArticleList.get(i).getYear();
            int past_year = axisArticleList.get(i - 1).getYear();

            if (present_year != past_year) {
                tmpAxisArticle = new AxisArticle();
                tmpAxisArticle.setId(0);
                tmpAxisArticle.setYear(present_year);
                result.add(tmpAxisArticle);
            }
            result.add(axisArticleList.get(i));
        }
        // ע��: ��list�������涯̬�޸������鳤�Ȼ�����ڴ���������
        return result;
    }

    public int starArticle(int id) throws SQLException {
        return articleDao.star_article(id);
    }

    public void addVisit(int article_id) throws SQLException {
        articleDao.addVisit(article_id);
    }
}
