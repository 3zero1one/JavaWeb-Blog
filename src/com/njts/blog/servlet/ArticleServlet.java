package com.njts.blog.servlet;

import com.njts.blog.bean.Article;
import com.njts.blog.dao.ArticleDao;
import com.njts.blog.dao.TagDao;
import com.njts.blog.service.ArticleService;
import com.njts.blog.service.CommentService;
import com.njts.blog.service.TagService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ArticleServlet")
public class ArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArticleService articleService = new ArticleService();
        TagService tagService = new TagService();
        CommentService commentService = new CommentService();

        String id = req.getParameter("id");

        try {
            // ����
            Article article = articleService.getArticle(id).get(0);
            req.setAttribute("article", article);

            // �������еı�ǩ
            req.setAttribute("article_tags", tagService.getTagById(id));

            // ��һƪ����
            req.setAttribute("article_pre", ArticleService.getPreviousArticle(article.getTime()));

            // ��һƪ����
            req.setAttribute("article_next", ArticleService.getNextArticle(article.getTime()));

            // ��������
            req.setAttribute("comment", commentService.loadComment(article.getId()));

            req.getRequestDispatcher("/page/article.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
