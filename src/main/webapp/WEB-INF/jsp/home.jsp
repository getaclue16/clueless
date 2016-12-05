<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
<div style="text-align: center;">
    <h1>Welcome!</h1>
    <img alt="The Bee's Knees"
        src="<c:url value="/resources/images/beesknees.png" />" /> <br />
    <sec:authorize access="isAnonymous()">
        <a class="btn btn-primary" href="<c:url value="/login" />">login</a>
        <br />
        <a class="btn btn-info" href="<c:url value="/newuser" />">new
            user</a>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <form action="<c:url value="/logout" />" method="post">
            <sec:csrfInput />
            <input type="submit" value="Sign Out" />
        </form>
    </sec:authorize>
</div>
<jsp:include page="foot.jsp" />