package com.njts.blog.servlet;

import com.njts.blog.bean.Article;
import com.njts.blog.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminService adminService = new AdminService();


        /**
         * ���servlet����edit.jspҳ������ݶ��ù�����
         * ��ԭ����ɾ��Ȼ����������һƪ����
         * ����updateArticle��������ɾ�����µĲ���
         */
        Article result = null;
        try {

            result = adminService.updateArticle(req);
            req.setAttribute("article", result);
            req.getRequestDispatcher("/admin/result.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
