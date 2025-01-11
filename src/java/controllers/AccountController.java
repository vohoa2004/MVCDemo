/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AccountDAO;
import dto.account.RegisterAccountDTO;
import entities.Account;
import exceptions.InvalidDataException;
import exceptions.ValidationException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.enums.AccountRole;

/**
 *
 * @author vothimaihoa
 */
public class AccountController extends HttpServlet {
    
    private final String REGISTER = "Account";
    private final String REGISTER_VIEW = "view/account/register.jsp";
    private final String LOGIN_VIEW = "view/account/login.jsp";
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        request.getRequestDispatcher(REGISTER_VIEW).forward(request, response);
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
        if ("register".equals(action)) {
            register(request, response, accountDAO);
        }
    }
    
    private void register(HttpServletRequest request, HttpServletResponse response, AccountDAO accountDAO)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("passwordConfirm");
        
        String view = REGISTER_VIEW;
        
        try {
            RegisterAccountDTO dto = new RegisterAccountDTO(username, password, confirmPassword);
            dto.validate();
            
            Account account = new Account(username, password);
            account.setRole(AccountRole.CUSTOMER);
            
            if (accountDAO.getByUsername(username) != null) {
                throw new InvalidDataException("Username existed!");
            }
            
            boolean isOk = accountDAO.create(account);
            if (!isOk) {
                throw new InvalidDataException("Cannot add new user to database!");
            } else {
                request.setAttribute("message", "Registered Successfully! Now you can login!");
            }
        } catch (ValidationException | InvalidDataException ex) {
            request.setAttribute("message", ex.getMessage());
        }
        finally {
            request.getRequestDispatcher(view).forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
