<%--
  Created by IntelliJ IDEA.
  User: ����ʶׯ����
  Date: 2023/5/22
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="UTF-8">
    <title>登录 | MyBlog</title>
    <!-- Bootstrap core CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link type="text/css" rel="stylesheet" href="css/login.css" />
  </head>
  <body>
    <div class="container" id="main">
      <%--  下面表单中action的属性也可以为一个servlet类如"addUserServlet"，同时form表单中的数据也随同提交到了addUserServlet，之后servlet通过requset域对象来获取对应的参数值--%>
      <form class="form-signin" id="form" action="/LoginServlet" method="post">
        <a href="/index.jsp"><h2 class="form-signin-heading">MyBlog</h2></a>

        <label for="input" class="sr-only">用户名</label>
        <input type="text" id="input" class="form-control" placeholder="用户名" name="username" required>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="密码" name="password" required>

        <button class="btn btn-lg btn-primary btn-block" type="submit" id="submit">登录</button>
        <a href="/index.jsp">访客登录</a>
      </form>
    </div>

    <div id="footer">
      <div>
        <a href="https://github.com/Lemonreds"><img src="img/github.png" width="20px" height="20px" class="img-circle">&nbsp;&nbsp;GitHub</a>
        &nbsp;|
        <a href="#">&nbsp;&nbsp;Sakura</a>
      </div>
    </div>
  </body>
</html>
