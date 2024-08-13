package servlets;

import com.google.gson.Gson;
import jdk.internal.net.http.common.Pair;
import models.DAO.Dao;
import models.USER.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class forSearchingAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        if (query != null && !query.isEmpty()) {
            Dao db = (Dao) request.getServletContext().getAttribute(Dao.DBID);
            try {
                List<Integer> userIds = db.searchUserByUsername(query);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                List<Pair<String,String>> users = new ArrayList<>();
                int cnt = 5;
                for (Integer id : userIds){
                    if (cnt == 0) break;
                    User user = db.getUserById(id);
                    users.add(new Pair<>(user.getUsername(),user.getImage()));
                    cnt--;
                }
                String json = new Gson().toJson(users);
                response.getWriter().write(json);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } else {
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
}
