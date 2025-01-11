/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Account;
import entities.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;
import utils.enums.AccountRole;

/**
 *
 * @author vothimaihoa
 */
public class AccountDAO {

    public Account getById(int id) {
        Account a = null;
        String sql = " SELECT id, username, role "
                + " from Account a "
                + " where id = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    a.setRole((rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER));
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex) {
            System.out.println("SQL Exception in getting product by id. Details: ");
            ex.printStackTrace();
        }
        return a;
    }

    public Account getByUsernamePassword(String username, String password) {
        Account a = null;
        String sql = " SELECT id, username, role "
                + " from Account a "
                + " where username = ? AND password = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    a.setRole((rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER));
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex) {
            System.out.println("SQL Exception in getting product by id. Details: ");
            ex.printStackTrace();
        }
        return a;
    }
    
    public Account getByUsername(String username) {
        Account a = null;
        String sql = " SELECT id, username, role "
                + " from Account a "
                + " where username = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    a.setRole((rs.getInt("role") == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER));
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex) {
            System.out.println("SQL Exception in getting product by id. Details: ");
            ex.printStackTrace();
        }
        return a;
    }
    
    public boolean create(Account newAccount) {
        boolean status = false;
        String sql = "INSERT INTO Account(username, password, role) "
                + " VALUES(?, ?, ?)";

        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newAccount.getUsername());
            ps.setString(2, newAccount.getPassword()); // sau nay thay bang password hash
            ps.setInt(3, newAccount.getRole().getValue());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                status = true;
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("DBUtils not found!");
        } catch (SQLException ex) {
            System.out.println("SQL Exception in inserting new account. Details: ");
            ex.printStackTrace();
        }
        return status;
    }
}
