<%@ page import="models.USER.User" %>
<link rel="icon" href="icons/chat.png" type="image/x-icon">
<link rel="stylesheet" href="styles/navbarStyles.css">

<nav class="topNavbar" style="position: fixed">
    <div class="navDiv">
        <div class="chatNameClass">
            <a href="/" style="margin-right: 80px"><p class="chatName">ChatApp</p></a>
            <form action="/searchAccount" method="get" class="searchForm">
                <input type="text" name="query" placeholder="Search users..." class="searchInput" id="searchInput">
                <button type="submit" class="userSearchButton">Search</button>
                <div id="suggestions" class="suggestions"></div>
            </form>
        </div>
        <div class="userInfoContainer">
            <% if(request.getSession().getAttribute("user")==null) { %>
            <a href="login" class="login"><button class="loginBtn">Log In</button></a>
            <% } else { %>
            <%
                String username = ((User) request.getSession().getAttribute("user")).getUsername();
            %>
            <a href="home"><%=username%></a>
            <a href="logout" class="logout"><button class="loginBtn">Log Out</button></a>
            <% } %>
        </div>
    </div>
</nav>
<script src="scripts/searchActions.js"></script>

