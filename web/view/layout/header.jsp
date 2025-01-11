<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
    <a href="#">Product Management</a>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/Product?action=list">Product Lists</a>
        </li>
    </ul>
    <c:if test="${sessionScope.account eq null}">
        <form action="Auth">
            <input type="hidden" name="action" value="login">
            <input type="submit" value="Login">
        </form>
    </c:if>

    <c:if test="${sessionScope.account ne null}">
        <form action="Auth">
            <input type="hidden" name="action" value="logout">
            <input type="submit" value="Logout">
        </form>
    </c:if>
</nav>
