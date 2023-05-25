package com.njts.blog.servlet;

import com.njts.blog.bean.Comment;
import com.njts.blog.service.CommentService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DislikeCommentServlet")
public class DislikeCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ҵ����� ��ȡ����id
        String id = req.getParameter("id");
        // ���ص�����
        JSONObject jo = new JSONObject();
        boolean repeat = false;
        // дһ��cookie��ֹ�ظ��ύ
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("diss_cm" + id)) {
                // �ظ��ύ������
                jo.put("msg", "failed");
                repeat = true;
                break;
            }
        }
        if (!repeat) {
            CommentService commentService = new CommentService();

            int new_diss = 0;
            try {
                new_diss = commentService.starOrDiss(Integer.parseInt(id), Comment.DISS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            jo.put("msg", "success");
            jo.put("new_diss", new_diss);

            // �����µ�cookie
            Cookie cookie = new Cookie("diss_cm" + id, System.currentTimeMillis() + "");
            // ������Ч�� 15����
            cookie.setMaxAge(15 * 60);
            // ������ЧĿ¼
            cookie.setPath("/");
            // д�������
            resp.addCookie(cookie);
        }
        // д��ajax
        resp.getWriter().println(jo);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
