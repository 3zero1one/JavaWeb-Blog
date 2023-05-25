package com.njts.blog.servlet;

import com.njts.blog.bean.User;
import com.njts.blog.dao.LoginDao;
import com.njts.blog.dao.VisitorDao;
import com.njts.blog.service.ArticleService;
import com.njts.blog.service.TagService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        LoginDao loginDao = new LoginDao();
        ArticleService aritcleService = new ArticleService();
        TagService tagService = new TagService();

        //��ȡ��ǰ��������е�ֵ
        String username = req.getParameter("username");
        String password = req.getParameter("password");


        try {
            // ����LoginDao�ķ�������ȡuser����
            User user = loginDao.queryUserByNameAndPassword(username, password);

            //�ж��û�user�Ƿ�Ϊ�� �մ������ݿ�û������û� ���մ������ݿ⵱�������ֵ
            if (user != null) {

                // д��session����Ϊ�˴����û���
                req.getSession().setAttribute("user",user);

                // ���ݷ�������ƺ͸��������µ�������
                req.setAttribute("sort_count_map", aritcleService.getSortAndCount());
                // �������е����£���ʼ������ҳ����
                req.setAttribute("article_list", aritcleService.getAllArticle());
                // ��ȡ���еı�ǩ�����ҳ�ʼ������ҳ����
                req.setAttribute("tag_list", tagService.getAllTag());
                // ��ʼ������� ��־�����ࡢ��ǩ�ĸ���
                req.setAttribute("article_number", aritcleService.getArticleCount());
                req.setAttribute("sort_number", aritcleService.getSortCount());
                req.setAttribute("tags_number", tagService.getTagCount());
                // ��ʼ���Ķ����е�����
                req.setAttribute("visit_rank", aritcleService.getVisitRank());
                // ��ʼ����վ�ķ��ʴ����ͷ�����
                req.setAttribute("visited", VisitorDao.totalVisit());
                req.setAttribute("member", VisitorDao.totalMember());

                // ��λ��������
                req.getRequestDispatcher("/page/main.jsp").forward(req,resp);
            } else {
                //���󷵻ص���¼ҳ��
                req.getRequestDispatcher("/login.html").forward(req,resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
