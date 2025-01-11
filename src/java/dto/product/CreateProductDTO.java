/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto.product;

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
public class CreateProductDTO implements DtoBase {

    private String name;
    private String price;
    private String productYear;
    private String image;
    private String categoryId;

    public CreateProductDTO() {
    }

    public CreateProductDTO(String name, String price, String productYear, String image, String categoryId) {
        this.name = name;
        this.price = price;
        this.productYear = productYear;
        this.image = image;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getProductYear() {
        return productYear;
    }

    public String getImage() {
        return image;
    }

    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @throws ValidationException
     */
    @Override
    public void validate() throws ValidationException {
        List<ValidationError> errors = new ArrayList<>();

        //validate name
        if (name == null || name.trim().isEmpty()) {
            errors.add(new ValidationError("name", "Product name cannot be empty."));
        }
        
        // validate price
        try {
            float priceValue = Float.parseFloat(price);
            if (priceValue <= 0) {
                errors.add(new ValidationError("price", "Price must be greater than 0."));
            }
        } catch (NumberFormatException e) {
            errors.add(new ValidationError("price", "Price must be a valid number."));
        }
        
        // validate product year
        try {
            int productYearValue = Integer.parseInt(productYear);
            int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            if (productYearValue <= 1900 || productYearValue > currentYear) {
                errors.add(new ValidationError("product year", "Product year must be between 1900 and the current year."));
            }
        } catch (NumberFormatException e) {
            errors.add(new ValidationError("product year", "Product year must be a valid number."));
        }
        
        // validate image url
        if (!isValidImageURL(image)) {
            errors.add(new ValidationError("image", "Image URL is not valid!"));
        }
        
        // validate category
        try {
            int categoryIdValue = Integer.parseInt(categoryId);
            if (categoryIdValue <= 0) {
                errors.add(new ValidationError("category", "Category ID is invalid."));
            }
        } catch (NumberFormatException e) {
            errors.add(new ValidationError("category", "Category ID must be a valid number."));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
    
    private boolean isValidImageURL(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        Pattern IMAGE_URL_PATTERN = Pattern.compile(ValidateRegex.IMAGE_URL_REGEX);
        Matcher matcher = IMAGE_URL_PATTERN.matcher(url);
        return matcher.matches();
    }
}
