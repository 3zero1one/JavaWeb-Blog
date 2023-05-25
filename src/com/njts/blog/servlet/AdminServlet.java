package com.njts.blog.servlet;

import com.njts.blog.dao.ArticleDao;
import com.njts.blog.dao.VisitorDao;
import com.njts.blog.service.TagService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArticleDao articleDao = new ArticleDao();
        TagService tagService = new TagService();

        try {
            // �����е�����
            req.setAttribute("articles", articleDao.getAllArticle());
            // �����еķ���
            req.setAttribute("sort", articleDao.getAllSort());
            // �����еı�ǩ
            req.setAttribute("tags", tagService.getAllTag());
            // ����վ��ͳ������
            req.setAttribute("visited", VisitorDao.totalVisit());
            req.setAttribute("member", VisitorDao.totalMember());
            // ת��
            req.getRequestDispatcher("/admin/admin.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
