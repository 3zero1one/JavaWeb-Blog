package com.njts.blog.servlet;

import com.njts.blog.service.AdminService;
import com.njts.blog.service.ArticleService;
import com.njts.blog.service.TagService;
import com.njts.blog.utils.ArticleUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminArticleServlet")
public class AdminArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminService adminService = new AdminService();
        ArticleService articleService = new ArticleService();
        TagService tagService = new TagService();

        //��ȡadmin.jspҳ��Ҫ���������ͣ��޸Ļ���ɾ��...
        String op = req.getParameter("op");

        switch (op) {
            case "edit_article":

                String articleIdForEdit = req.getParameter("article_id");
                try {
                    // ����id��ȡ���¡����ࡢ��ǩ
                    req.setAttribute("edit_article", adminService.getArticle(articleIdForEdit));
                    req.setAttribute("sort_count", articleService.getSortAndCount());
                    req.setAttribute("all_tag", tagService.getAllTag());
                    req.getRequestDispatcher("/admin/edit.jsp").forward(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "delete_article":

                System.out.println("delete_article");
                String articleIdForDelete = req.getParameter("article_id");
                try {
                    // ɾ������
                    adminService.deleteArticle(articleIdForDelete);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "sort_update":
                String old_sort = req.getParameter("old_sort");
                String new_sort = req.getParameter("new_sort");

                if (!old_sort.equals(new_sort)) {
                    try {
                        adminService.updateSort(old_sort, new_sort);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "sort_delete":
                // ����������е���ҪArticleUtils.pareCode()����ת�룬�еĲ���Ҫ���������鷳��
                String sort = req.getParameter("sort");
                try {
                    adminService.deleteSort(sort);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "tag_update":
                String old_tag = req.getParameter("old_tag");
                String new_tag = req.getParameter("new_tag");
                if (!old_tag.equals(new_tag)) {
                    try {
                        adminService.updateTag(old_tag, new_tag);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "tag_delete":
                String tag = req.getParameter("tag");
                System.out.println("tag:"+tag);
                try {
                    adminService.deleteTag(tag);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
