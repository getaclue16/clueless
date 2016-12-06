<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
<div class="text-center">
    <h1>Welcome!</h1>
    <img alt="The Bee's Knees"
        src="<c:url value="/resources/images/beesknees.png" />" /> <br />
    <sec:authorize access="isAnonymous()">
        <p>
            <a class="btn btn-primary" href="<c:url value="/login" />">login</a>
        </p>
        <p>
            <a class="btn btn-info" href="<c:url value="/newuser" />">new
                user</a>
        </p>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <h2>Join a game</h2>
        <div class="text-left">
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

        <br />
        <h2>Create a game</h2>
        <div class="form-inline">
            <div class="form-group">
                <input class="form-control" id="gamename" name="name"
                    placeholder="Enter game name here"
                    required="required" type="text" />
                <button id="create-game">Create</button>
            </div>
        </div>

        <script src="<c:url value="/resources/js/home.js" />"></script>
    </sec:authorize>
    <br /> <br />
</div>
<jsp:include page="foot.jsp" />