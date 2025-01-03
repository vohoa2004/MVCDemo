/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.CategoryDAO;
import dao.ProductDAO;
import dto.CreateProductDTO;
import dto.SearchProductDTO;
import dto.UpdateProductDTO;
import entities.Category;
import entities.Product;
import exceptions.InvalidDataException;
import exceptions.ValidationException;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vothimaihoa
 */
@WebServlet(name = "ProductController", urlPatterns = "/Product")
public class ProductController extends HttpServlet {

    private final String LIST = "Product";
    private final String LIST_VIEW = "view/product/list.jsp";

    private final String PREPARE_CREATE = "Product?action=prepare-add";
    private final String EDIT_VIEW = "view/product/edit.jsp";

    private final String PREPARE_DELETE = "Product?action=prepare-delete";
    private final String DELETE_CONFIRM_VIEW = "view/product/deleteConfirm.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        String action = request.getParameter("action");
        if (action == null) {
            list(request, response, categoryDAO, productDAO);
        } else {
            switch (action) { // thay bang if cung duoc
                case "prepare-add":
                    prepareAdd(request, response, categoryDAO);
                    break;
                case "add":
                    add(request, response, categoryDAO, productDAO);
                    break;
                case "prepare-update":
                    prepareUpdate(request, response, categoryDAO, productDAO);
                    break;
                case "update":
                    update(request, response, categoryDAO, productDAO);
                    break;
                case "prepare-delete":
                    prepareDelete(request, response, productDAO);
                    break;
                case "delete":
                    delete(request, response, productDAO);
                    break;
                default:
                    list(request, response, categoryDAO, productDAO);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void list(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO, ProductDAO productDAO)
            throws ServletException, IOException {
        try {
            // get category list for drop down
            List<Category> categories = categoryDAO.list();
            request.setAttribute("categories", categories);

            // get search criterias
            String categoryIdRaw = request.getParameter("category");
            String productName = request.getParameter("productName");
            String priceRaw = request.getParameter("price");

            SearchProductDTO searchDTO = new SearchProductDTO(categoryIdRaw, productName, priceRaw);
            searchDTO.validate();

            Integer categoryId = null;
            Float price = null;
            if (categoryIdRaw != null && !categoryIdRaw.trim().isEmpty()) {
                categoryId = Integer.parseInt(categoryIdRaw);
            }

            if (priceRaw != null && !priceRaw.trim().isEmpty()) {
                price = Float.parseFloat(priceRaw);
            }

            // get search data
            List<Product> list = productDAO.list(productName, categoryId, price);
            if (list != null && !list.isEmpty()) {
                request.setAttribute("products", list);
            } else {
                throw new InvalidDataException("No Products Found!");
            }

            // hold search criteria on search bar for next request
            request.setAttribute("productName", productName);
            request.setAttribute("category", categoryIdRaw);
            request.setAttribute("price", priceRaw);

        } catch (ValidationException | InvalidDataException ex) {
            request.setAttribute("msg", ex.getMessage());
        } finally {
            request.getRequestDispatcher(LIST_VIEW).forward(request, response);
        }
    }

    private void prepareAdd(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO)
            throws ServletException, IOException {
        List<Category> categories = categoryDAO.list();
        request.setAttribute("categories", categories);
        request.setAttribute("targetAction", "add");
        request.getRequestDispatcher(EDIT_VIEW).forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO, ProductDAO productDAO)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String productYear = request.getParameter("productYear");
        String image = request.getParameter("image");
        String categoryId = request.getParameter("category");

        CreateProductDTO productDTO = new CreateProductDTO(name, price, productYear, image, categoryId);
        try {
            productDTO.validate();

            Category category = categoryDAO.getById(Integer.parseInt(categoryId));
            if (category == null) {
                throw new InvalidDataException("Category not found!");
            }

            // call DAO
            Product product = new Product(name, Float.parseFloat(price), Integer.parseInt(productYear), image, category);
            boolean isOk = productDAO.create(product);
            if (!isOk) {
                throw new InvalidDataException("Cannot save product to database!");
            } else {
                response.sendRedirect(LIST);
            }
        } catch (ValidationException | InvalidDataException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.getRequestDispatcher(PREPARE_CREATE).forward(request, response);
        }

    }

    private void prepareDataForUpdate(HttpServletRequest request, CategoryDAO categoryDAO, ProductDAO productDAO, int productId) {
        Product productToUpdate = productDAO.getById(productId);
        if (productToUpdate == null) {
            throw new InvalidDataException("Not found this product!");
        }
        request.setAttribute("productToUpdate", productToUpdate);
        List<Category> categories = categoryDAO.list();
        request.setAttribute("categories", categories);
        request.setAttribute("targetAction", "update");
    }

    private void prepareUpdate(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO, ProductDAO productDAO)
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            prepareDataForUpdate(request, categoryDAO, productDAO, id);
            request.getRequestDispatcher(EDIT_VIEW).forward(request, response);
        } catch (NumberFormatException | InvalidDataException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.getRequestDispatcher(LIST_VIEW).forward(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response, CategoryDAO categoryDAO, ProductDAO productDAO)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String productYear = request.getParameter("productYear");
        String image = request.getParameter("image");
        String categoryId = request.getParameter("category");

        UpdateProductDTO productDTO = new UpdateProductDTO(id, name, price, productYear, image, categoryId);
        try {
            productDTO.validate();

            Category category = categoryDAO.getById(Integer.parseInt(categoryId));
            if (category == null) {
                throw new InvalidDataException("Category not found!");
            }
            // call DAO
            Product product = productDAO.getById(Integer.parseInt(id));
            if (product == null) {
                throw new InvalidDataException("This product is not existed to be update!");
            }
            product.setImage(image);
            product.setName(name);
            product.setPrice(Float.parseFloat(price));
            product.setProductYear(Integer.parseInt(productYear));
            product.setCategory(category);

            boolean isOk = productDAO.update(product);

            if (!isOk) {
                throw new InvalidDataException("Cannot update product to database!");
            } else {
                response.sendRedirect(LIST);
            }
        } catch (ValidationException | InvalidDataException ex) {
            request.setAttribute("msg", ex.getMessage());

            // set the product for next request
            prepareDataForUpdate(request, categoryDAO, productDAO, Integer.parseInt(id));
            request.getRequestDispatcher(EDIT_VIEW).forward(request, response);
        }
    }

    private void prepareDelete(HttpServletRequest request, HttpServletResponse response, ProductDAO productDAO)
            throws ServletException, IOException {
        String productIdRaw = request.getParameter("id");
        try {
            Integer productId = Integer.parseInt(productIdRaw);
            Product productToDelete = productDAO.getById(productId);
            if (productToDelete == null) {
                throw new InvalidDataException("Not found this product!");
            }
            request.setAttribute("product", productToDelete);
            request.getRequestDispatcher(DELETE_CONFIRM_VIEW).forward(request, response);
        } catch (NumberFormatException | InvalidDataException ex) {
            request.setAttribute("msg", ex.getMessage());
            request.getRequestDispatcher(LIST).forward(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, ProductDAO productDAO)
            throws ServletException, IOException {
        String productIdRaw = request.getParameter("id");
        try {
            Integer productId = Integer.parseInt(productIdRaw);
            boolean isOk = productDAO.delete(productId);
            if (!isOk) {
                throw new InvalidDataException("Cannot delete product in database!");
            } else {
                request.setAttribute("msg", "Deleted product successfully!");
            }
        } catch (NumberFormatException | InvalidDataException ex) {
            request.setAttribute("msg", ex.getMessage());
        } finally {
            request.getRequestDispatcher(DELETE_CONFIRM_VIEW).forward(request, response);
        }
    }

}
