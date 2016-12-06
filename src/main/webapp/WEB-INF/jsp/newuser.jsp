<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="head.jsp">
    <jsp:param name="title" value="New User" />
</jsp:include>
<c:url value="/newuser" var="actionUrl" />
<form:form cssClass="form-horizontal" method="post"
    modelAttribute="newuser" action="${actionUrl}">
    <div class="form-group">
        <form:label cssClass="col-sm-2 control-label" path="username">Username: </form:label>
        <div class="col-sm-10">
            <form:input autofocus="autofocus" cssClass="form-control"
                path="username" pattern="[\\w ]+"
                placeholder="Enter your username"
                title="Please enter a username containing only letters, numbers, spaces, and underscores"
                type="text" required="required" />
            <form:errors cssClass="error" path="username" />
        </div>
    </div>
    <div class="form-group">
        <form:label cssClass="col-sm-2 control-label" path="password">Password: </form:label>
        <div class="col-sm-10">
            <form:password cssClass="form-control" path="password"
                pattern=".{8,}" placeholder="Enter a password"
                title="Please enter a password that is at least eight (8) characters long"
                required="required" />
            <form:errors cssClass="error" path="password" />
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">Create
                User</button>
        </div>
    </div>
</form:form>
<jsp:include page="foot.jsp" />