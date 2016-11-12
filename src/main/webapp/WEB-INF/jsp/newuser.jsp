<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="head.jsp">
    <jsp:param value="title" name="Clueless - New User" />
</jsp:include>
<body>
    <c:url value="/newuser" var="actionUrl" />
    <form:form method="post" modelAttribute="newuser"
        action="${actionUrl}">
        <table>
            <tr>
                <td><form:label path="username">Username: </form:label></td>
                <td><form:input path="username" pattern="[\\w ]+"
                        placeholder="Enter your username"
                        title="Please enter a username containing only letters, numbers, spaces, and underscores"
                        type="text" required="required" /> <form:errors
                        cssClass="error" path="username" /></td>
            </tr>
            <tr>
                <td><form:label path="password">Password: </form:label></td>
                <td><form:password path="password" pattern=".{8,}"
                        placeholder="Enter a password"
                        title="Please enter a password that is at least eight (8) characters long"
                        required="required" /> <form:errors
                        cssClass="error" path="password" /></td>
            </tr>
            <tr>
                <td>
                    <button type="submit">Create User</button>
                </td>
            </tr>
        </table>
    </form:form>
</body>
</html>
