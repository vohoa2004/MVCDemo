/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Account;
import dao.AccountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vothimaihoa
 */
public class AuthController extends HttpServlet {
    private final String LIST = "Product";
    private final String LOGIN_VIEW = "view/account/login.jsp";
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.equals("") || action.equals("login")) {
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
        } else if (action.equals("logout")) { 
            logout(request, response);
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        AccountDAO accountDAO = new AccountDAO();

        if (action == null || action.equals("")) {
            login(request, response, accountDAO);
        }
    }
    
    
    private void login(HttpServletRequest request, HttpServletResponse response, AccountDAO accountDAO)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Account account = accountDAO.getByUsernamePassword(username, password);

        if (account != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("account", account);
            response.sendRedirect(LIST);
        } else {
            String msg = "Username or password is wrong!";
            request.setAttribute("error", msg);
            request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            session.invalidate();
        }
        request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
    }
}
