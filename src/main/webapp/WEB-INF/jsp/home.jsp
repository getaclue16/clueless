<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>

<jsp:include page="head.jsp">
    <jsp:param value="title" name="Clueless" />
</jsp:include>
<body>
    <div>
        <h1>Clue-less</h1>
        <sec:authorize access="isAnonymous()">
            <a href="<c:url value="/login" />">login</a>&nbsp;&nbsp;
        <a href="<c:url value="/newuser" />">new user</a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <form action="/logout" method="post">
                <sec:csrfInput />
                <input type="submit" value="Sign Out" />
            </form>
        </sec:authorize>
    </div>
</body>

</html>
