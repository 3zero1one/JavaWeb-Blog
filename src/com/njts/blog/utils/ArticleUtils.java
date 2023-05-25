package com.njts.blog.utils;

import com.njts.blog.bean.Article;
import com.njts.blog.bean.AxisArticle;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleUtils {

    /**
     * ����ҳ���������ݼ���һ�£��ø�������ݵ���Ķ�ȫ�Ŀ�
     * @param list
     * @return
     */
    public static List cutContent(List<Article> list) {

        for (Article a : list) {
            if (a.getContent() != null && a.getContent().length() > 351) {
                a.setContent(a.getContent().substring(0, 349) + "...");
            }
        }
        return list;
    }

    /**
     * ����һ��ʱ�䣬ֻ���������գ�ȥ��ʱ����
     * @param list
     * @return
     */
    public static List<Article> cutTime(List<Article> list) {

        for (Article a : list) {
            a.setTime(a.getTime().substring(0, 11));
        }

        return list;
    }

    /**
     * ��ȥ�������20�Ĳ���
     * @param str
     * @param begin
     * @param end
     * @return
     */
    public static String cutTitle(String str, int begin, int end) {

        if (str.length() < end || str.length() < begin)
            return str;

        return str.substring(begin, end);
    }

    /**
     * �����±�ɷ���ʱ�������͵�����
     * @param article
     * @return
     */
    public static AxisArticle changeToAxisArticle(Article article) {

        AxisArticle axisArticle = new AxisArticle();

        axisArticle.setTitle(article.getTitle());
        axisArticle.setId(article.getId());

        // 2017-09-20 21:27:14
        String year = cutTitle(article.getTime(), 0, 4);
        String month = cutTitle(article.getTime(), 5, 7);
        String day = cutTitle(article.getTime(), 8, 10);

        axisArticle.setYear(Integer.valueOf(year));
        axisArticle.setMonth(Integer.valueOf(month));
        axisArticle.setDay(Integer.valueOf(day));

        return axisArticle;
    }

    /**
     * ��ô������µ�ʱ��
     * @return
     */
    public static String timeOfCreate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static Article tidyToArticleBean(HttpServletRequest req) throws InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {
        Map value = new HashMap();

        value.put("title", req.getParameter("title"));
        value.put("time", req.getParameter("time"));
        value.put("author", req.getParameter("author"));
        value.put("sort", req.getParameter("sort"));
        value.put("content", req.getParameter("content"));
        value.put("star", 0);
        value.put("comment", 0);
        value.put("visit", 0);

        Article articleBean = new Article();
        //����Ӧ��ֵ����articleBean��
        BeanUtils.populate(articleBean, value);

        if (verify(articleBean)) {
            return articleBean;
        }
        return null;
    }

    /**
     * �жϱ�����Ƿ�Ϊ��
     * @param article
     * @return
     */
    private static boolean verify(Article article) {
        boolean result = true;

        if (article.getSort() == "" || article.getTitle() == "" || article.getAuthor() == "" || article.getContent() == "") {
            result = false;
        }
        return result;
    }

    // ���� �����URL������ֵ���ֵ���������  5/24ps:�ò��ϣ�������
//    public static String pareCode(String str) throws UnsupportedEncodingException {
//        return new String(str.getBytes("ISO-8859-1"), "UTF-8");
//    }
}
