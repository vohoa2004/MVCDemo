/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto.account;

import dto.DtoBase;
import exceptions.ValidationError;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.ValidateRegex;

/**
 *
 * @author vothimaihoa
 */
public class RegisterAccountDTO implements DtoBase {

    private String username;
    private String password;
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public RegisterAccountDTO(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @Override
    public void validate() throws ValidationException {
        List<ValidationError> errors = new ArrayList<>();
        if (username == null || username.isEmpty()) {
            errors.add(new ValidationError("username", "Username is required!"));
        } // validate username regex
        else {
            Pattern usernamePattern = Pattern.compile(ValidateRegex.USERNAME_REGEX);
            Matcher userNameMatcher = usernamePattern.matcher(username);
            if (!userNameMatcher.matches()) {
                errors.add(new ValidationError(("username"), "Username not longer than 20 characters and must not contains space"));
            }
        }
        if (password == null || password.isEmpty()) {
            errors.add(new ValidationError("password", "Password is required!"));
        } else {
            // validate password regex
            System.out.println(password);
            Pattern passwordPattern = Pattern.compile(ValidateRegex.PASSWORD_REGEX);
            Matcher passwordMatcher = passwordPattern.matcher(password);
            if (!passwordMatcher.matches()) {
                errors.add(new ValidationError(("password"), "Password must be 8 to 50 characters length. It must have at least 1 English lower letter, 1 English upper letter, 1 digit and 1 special character!"));
            } // validate password == confirm password
            if (!password.equals(confirmPassword)) {
                errors.add(new ValidationError(("password"), "Confirm password must match password"));
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

}
