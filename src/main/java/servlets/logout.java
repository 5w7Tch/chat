package servlets;

import models.USER.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/logout")
public class logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            ArrayList<User> online = (ArrayList<User>)request.getServletContext().getAttribute(User.ONLINE);
            User user = (User)session.getAttribute("user");
            online.remove(user);
            request.getServletContext().setAttribute(User.ONLINE, online);
            session.invalidate();
        }
        response.sendRedirect("/");
    }
}
