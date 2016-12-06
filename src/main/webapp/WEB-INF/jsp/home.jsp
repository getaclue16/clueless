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
        <div style="text-align: left;">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Creator</th>
                        <th>Players</th>
                    </tr>
                </thead>
                <tbody id="opengames"></tbody>
            </table>
        </div>
        <input id="gamename" name="name" required="required" type="text" />
        <button id="create-game">Create New Game</button>

        <form action="<c:url value="/logout" />" method="post">
            <sec:csrfInput />
            <input type="submit" value="Sign Out" />
        </form>
        <script src="<c:url value="/resources/js/home.js" />"></script>
    </sec:authorize>
</div>
<jsp:include page="foot.jsp" />