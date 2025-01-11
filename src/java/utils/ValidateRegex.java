/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author vothimaihoa
 */
public class ValidateRegex {
    public static final String IMAGE_URL_REGEX = "^(https?:\\/\\/)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\\/[^\\s]+\\.(jpg|jpeg|png|gif|bmp|webp|svg)$";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_.]{1,20}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,50}$";
}
