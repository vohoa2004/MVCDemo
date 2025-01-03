<%-- 
    Document   : create
    Created on : Dec 27, 2024, 6:36:23 PM
    Author     : vothimaihoa
--%>

<%@page import="entities.Product"%>
<%@page import="java.util.List"%>
<%@page import="entities.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create/Update Product</title>
    </head>
    <body>
        <%
            String targetAction = (String) request.getAttribute("targetAction");
            if (("add").equals(targetAction)) {
        %>  
        <h1>Add New Product</h1>
        <%
        } else {
        %>
        <h1>Update Product</h1>
        <%
            }
        %>

        <!--DISPLAY ERROR MESSAGE-->
        <%
            String error = (String) request.getAttribute("msg");
        %>
        <%
            if (error != null) {
        %>
        <p><%=error%></p>
        <%
            }
        %>
        <% 
            Integer selectedCategory = null;
            if ("update".equals(targetAction)) {
                Product productToUpdate = (Product) request.getAttribute("productToUpdate");
                selectedCategory = productToUpdate.getCategory().getId();
            }
        %>
        <form action="Product" method="POST">
            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" value="${productToUpdate.name}" required><br><br>

            <label for="price">Price:</label>
            <input type="text" id="price" name="price" value="${productToUpdate.price}" required><br><br>

            <label for="productYear">Product Year:</label>
            <input type="text" id="productYear" name="productYear" value="${productToUpdate.productYear}" required><br><br>

            <label for="image">Image URL:</label>
            <input type="text" id="image" name="image" value="${productToUpdate.image}"><br><br>

            <label for="category">Category:</label>
            <select id="category" name="category" required>
                <%
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    if (categories != null) {
                        for (Category category : categories) {
                %>
                    <option value="<%= category.getId()%>"><%= category.getName()%></option>
                <%
                        }
                    }
                %>
            </select><br><br>
            <input type="hidden" name="id" value=${productToUpdate.id} />
            <input type="hidden" name="action" value=${requestScope.targetAction} /> <!-- parameter action to handle add new product-->
            <button type="submit">Save</button>
        </form>
        <script>
                const selectedCategoryId = '<%= selectedCategory %>';
                const selectElement = document.getElementById('category');

                if (selectedCategoryId) {
                    selectElement.value = selectedCategoryId;
                } else {
                    selectElement.value = "";
                }
        </script>
    </body>
</html>
