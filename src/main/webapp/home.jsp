<%--
  Created by IntelliJ IDEA.
  User: sw1tch
  Date: 11.08.24
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="navbar.jsp" %>
    <link rel="stylesheet" href="styles/home.css">
    <%
        User loggedInUser = (User)request.getSession().getAttribute("user");

    %>
    <title>Home</title>
</head>
    <body >
        <div class="containerAll">
            <div class="your-info">
                <div class="rectangle">
                    <div class="picture">
                        <img style="width: 110px; height: 110px" src="<%=loggedInUser.getImage()%>" alt=":picture:">
                    </div>
                    <div class="info">
                        <h3><%=loggedInUser.getUsername()%></h3>
                        <p><%=loggedInUser.getEmail()%></p>

                        <div class="buttons">
                            <button>Change Picture</button>
                            <button>Edit Account</button>
                        </div>

                    </div>
                </div>

            </div>
            <div class="posts">
                <p>posts</p>
            </div>
            <div class="friends">
                <p>friend list</p>
            </div>
        </div>

    </body>
<script src="scripts/home.js"></script>

</html>
