<%-- 
    Document   : product
    Created on : Dec 26, 2024, 10:48:03 PM
    Author     : vothimaihoa
--%>

<%@page import="entities.Product"%>
<%@page import="entities.Category"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Page</title>
    </head>
    <body>
        <jsp:include page="../layout/header.jsp" flush="true"/>
        <!-- Search by category(drop down) and name (text box) - Add -->
        <h1>Product Page</h1>
        <br>
        <h2 class="text-primary"> Login user: ${sessionScope.account.username}</h2>

        <!--SEARCH FORM-->
        <form action="Product" method="GET">
            <label>Product Name:</label>
            <input type="text" name="productName" value="${requestScope.productName}">

            <label>Category:</label>
            <select name="category" id="category">
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
                <option value="<%=""%>"><%= "All categories"%></option>

            </select>
            <label>Price (Equals Or Under): </label>
            <input type="text" name="price" value="${requestScope.price}">
            <input type="submit" value="search">
        </form>

        <br>

        <!--PRODUCT LIST-->
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products == null) {
                String msg = (String) request.getAttribute("msg");
        %>
        <h3><%= msg%></h3>
        <%
        } else {
            int count = 1;
        %>
        <table>
            <tr>
                <th>No. </th>
                <th>Product Name</th>
                <th>Price (USD)</th>
                <th>Product Year</th>
                <th>Image</th>
                <th>Category</th>
            </tr>
            <%
                for (Product p : products) {
            %>
            <tr>
                <td><%= count++%></td>
                <td><%= p.getName()%></td>
                <td><%= p.getPrice()%></td>
                <td><%= p.getProductYear()%></td>
                <td><img src="<%= p.getImage()%>" alt="product image" width="100" height="100"></td>
                <td><%= p.getCategory().getName()%></td>
                <td>
                    <form action="Product" method="POST">
                        <input type="hidden" name="id" value=<%= p.getId()%> />
                        <input type="hidden" name="action" value="prepare-update" />
                        <input type="submit" value="Update" />
                    </form>
                </td>
                <td>
                    <form action="Product" method="POST">
                        <input type="hidden" name="id" value=<%= p.getId()%> />
                        <input type="hidden" name="action" value="prepare-delete" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <%
            }
        %>
        <br>
        <form action="Product" method="GET">
            <input type="hidden" name="action" value="prepare-add">
            <button type="submit">Add</button>
        </form>


        <!-- JS -->
        <script>
            const selectedCategoryId = '<%= request.getAttribute("category")%>';
            const selectElement = document.getElementById('category');

            if (selectedCategoryId) {
                selectElement.value = selectedCategoryId;
            } else {
                selectElement.value = "";
            }
        </script>

        <!-- BTVN: Viet them search by price va sort, + viet them update + delete product by id -->
    </body>
</html>
