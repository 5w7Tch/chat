<%@ page import="models.DAO.Dao" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat</title>
    <link rel="stylesheet" href="styles/welcome.css">
    <%@ include file="navbar.jsp" %>
    <%
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
    %>
</head>
<body>
    <div class="welcome-container">
        <div style="justify-content: space-around; margin-top: 10%;">
            <h1>Your gateway to</h1>
            <h1>seamless communication</h1>
            <a href="enter" class="enter-button">Get Started</a>
            <div class="container2">
                <div style="display: flex">
                    <div class="user-count"><%=db.getUserCount()%></div>
                    <div class="rating-value">Happy Customers</div>
                </div>
                <div class="line-container">
                    <div class="line"></div>
                </div>
                <div class="rating">
                    <div class="stars">
                        <span class="star">&#9733;</span> <!-- Filled star -->
                        <span class="star">&#9733;</span>
                        <span class="star">&#9733;</span>
                        <span class="star">&#9733;</span>
                        <span class="star">&#9734;</span> <!-- Empty star -->
                    </div>
                    <div class="rating-value">4.0/5.0</div>
                </div>
            </div>
        </div>
        <div class="photo">
            <img style="width: 500px; height: 700px" src="icons/phones.png" alt="description of image">
        </div>
    </div>
    <h1 style="justify-content: space-around; display: flex; margin-top: 100px; margin-bottom: 60px;">Features for a better experience</h1>
    <div class="features-container">
        <h3> sry just started u can't even chat ;)</h3>
    </div>
    <h1 style="justify-content: space-around; display: flex; margin-top: 100px; margin-bottom: 60px;">Features coming soon</h1>
    <div class="features-container">
        <div class="feature">
            <img style="width: 70px; height: 70px" src="icons/chating.png" alt="description of image">
            <div class="line-container">
                <div class="line"></div>
            </div>
            <div >
                <h3>Chat:</h3>
                <p>Live chatting with</p>
                <p>complete privacy</p>
            </div>
        </div>
        <div class="feature">
            <img style="width: 70px; height: 70px" src="icons/folder.png" alt="description of image">
            <div class="line-container">
                <div class="line"></div>
            </div>
            <div >
                <h3>File transfer:</h3>
                <p>Send data with no limits</p>
            </div>
        </div>
        <div class="feature">
            <img style="width: 70px; height: 70px" src="icons/video.png" alt="description of image">
            <div class="line-container">
                <div class="line"></div>
            </div>
            <div >
                <h3>Video messaging:</h3>
                <p>Live call with video shearing</p>
            </div>
        </div>
    </div>
</body>
</html>
