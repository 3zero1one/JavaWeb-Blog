package com.njts.blog.service;

import com.njts.blog.bean.Article;
import com.njts.blog.bean.Tag;
import com.njts.blog.dao.TagDao;
import com.njts.blog.utils.ArticleUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagService {

    TagDao tagDao = new TagDao();
    ArticleService articleService = new ArticleService();

    /**
     * ������еı�ǩ����
     * @return
     * @throws SQLException
     */
    public List<Tag> getAllTag() throws SQLException {
        return tagDao.getAllTag();
    }

    /**
     * ��.size()�Ϳ��Ի�ø�����
     * @return
     * @throws SQLException
     */
    public int getTagCount() throws SQLException {
        return tagDao.getAllTag().size();
    }

    public Map<String, List<Article>> getTagAndArticle(String tag_name) throws SQLException {
        List<Tag> allTag;

        if (tag_name.equals("all") || StringUtils.isEmpty(tag_name)) {
            // ��ȡ���в��ظ��ı�ǩ
            allTag = tagDao.getAllTag();
        } else {
            // ��ȡ�����ǩ
            allTag = tagDao.getTagByColumn(tag_name);
        }

        Map<String, List<Article>> map = new HashMap<>();
        // ��ȡ�����ǩ���������� id
        for (Tag tag : allTag) {
            List<Tag> tagByColumn = tagDao.getTagByColumn(tag.getTag());
            // ��ѯ�������� id ����article_list
            List<Article> articleList = null;
            for (Tag tag_all : tagByColumn) {
                // �������ֻ��һ��Ԫ��
                List<Article> result = articleService.getArticle(String.valueOf(tag_all.getId()));
                articleList = ArticleUtils.cutTime(result);
            }
            // ���ر�ǩ������+��ǩ��ǵ��������¼���
            map.put(tag.getTag(), articleList);
        }
        return map;
    }

    public List<Tag> getTagById(String id) throws SQLException {
        return tagDao.getTagByColumnOfId(id);
    }
}
