package servlet;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UpdateUserServlet", urlPatterns = {"/user/update"})
public class UpdateUserServlet extends HttpServlet {

    UserService userService = UserService.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String id = request.getParameter("id");
        String login = request.getParameter("login");
        String value = request.getParameter("cmd");
        String name = request.getParameter("text");
        String pass = request.getParameter("password");
        String date = request.getParameter("date");

        if ( login.equals("") || pass.equals("")) {
            response.sendRedirect("/user");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (value.equals("DELETE")) {
            session.setAttribute("info", "you do not have sufficient privileges");
            response.sendRedirect("/user");
        } else {
            User user = new User(Long.valueOf(id), login, name, pass, java.sql.Date.valueOf(date), "user");
            userService.updateUser(user);
            session.setAttribute("login", login);
            session.setAttribute("password", pass);
            session.setAttribute("info", "user update");
            response.sendRedirect("/user");
        }
    }
}