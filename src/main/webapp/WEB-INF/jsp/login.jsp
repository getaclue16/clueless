<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>

<jsp:include page="head.jsp">
    <jsp:param value="title" name="Clueless - Login" />
</jsp:include>
<body>
    <c:if test="${param.error != null}">
        <div class="error">Invalid username or password</div>
    </c:if>
    <form action="<c:url value="/login" var="actionUrl" />"
        method="post">
        <sec:csrfInput />
        <div>
            <label> User Name : <input type="text"
                name="username" />
            </label>
        </div>
        <div>
            <label> Password: <input type="password"
                name="password" />
            </label>
        </div>
        <div>
            <input type="submit" value="Sign In" />
        </div>
    </form>
</body>
</html>
