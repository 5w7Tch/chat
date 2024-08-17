<%@ page import="models.DAO.Dao" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="navbar.jsp" %>
    <link rel="stylesheet" href="styles/home.css">
    <%
        User loggedInUser = (User)request.getSession().getAttribute("user");
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        ArrayList<User> friends = db.getFriends(loggedInUser.getId());
        ArrayList<User> online = (ArrayList<User>) request.getServletContext().getAttribute(User.ONLINE);
    %>
    <title>Home</title>
</head>
    <body>
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
                <div class="friend-list">
                    <%for(int i = 0; i < friends.size(); i++){%>
                        <button class="buttonF" name="<%=friends.get(i).getId()%>">
                            <div class="friend">
                                <div class="profile-picture">
                                    <img src="<%=friends.get(i).getImage()%>>" alt=":picture:">
                                    <%if(online.contains(friends.get(i))){%>
                                        <span class="status online"></span>
                                    <%}else{%>
                                        <span class="status offline"></span>
                                    <%}%>
                                </div>
                                <div class="friend-name"><%=friends.get(i).getUsername()%></div>
                            </div>
                        </button>
                        <div class="friend-line"></div>
                    <%}%>
                </div>
            </div>
        </div>
    </body>

<script src="scripts/home.js"></script>

</html>
