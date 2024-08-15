package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jdk.internal.net.http.common.Pair;
import models.DAO.Dao;
import models.USER.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/searchAccount")
public class forSearchingAccount extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("query");
        if (userName == null){
            response.sendError(404);
            return;
        }
        Dao db = (Dao)request.getServletContext().getAttribute(Dao.DBID);
        try {
            int userId = db.getUserByName(userName);
            if (userId == -1){
                response.sendError(404);
                return;
            }
            response.sendRedirect("/account?Id=" + userId);
        } catch (SQLException e) {
            response.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        if (query != null && !query.isEmpty()) {
            Dao db = (Dao) request.getServletContext().getAttribute(Dao.DBID);
            try {
                HashMap<String,String> users = db.searchUserByUsername(query);

                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(users);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } else {
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
}
