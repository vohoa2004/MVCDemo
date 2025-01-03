<%-- 
    Document   : deleteConfirm.jsp
    Created on : Jan 3, 2025, 9:42:51 PM
    Author     : vothimaihoa
--%>

<%@page import="entities.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Confirmation Page</title>
    </head>
    <body>
        <%
            Product product = (Product) request.getAttribute("product");
            if (product == null) {
                String msg = (String) request.getAttribute("msg");
        %>
        <h3><%= msg%></h3>
        <form action="Product">
            <input type="submit" value="Back to Product List" />
        </form> 
        <%
        } else {
        %> 
        <h1>Delete Product Confirmation</h1>
        <h3>Are you sure to delete this product?</h3>
        <p>Product Name: <%=product.getName()%></p>
        <p>Price: <%=product.getPrice()%></p>
        <p>Product Year:: <%=product.getProductYear()%></p>
        <p>Category: <%=product.getCategory().getName()%></p>
        <form action="Product" method="POST">
            <input type="hidden" name="id" value=<%= product.getId()%> />
            <input type="hidden" name="action" value="delete" />
            <input type="submit" value="Yes" />
        </form>
        <form action="Product">
            <input type="submit" value="No" />
        </form> 
        <%}%>
    </body>
</html>
