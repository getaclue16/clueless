<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>

<jsp:include page="head.jsp">
    <jsp:param name="title" value="Login" />
</jsp:include>
<div class="container">
    <c:if test="${param.error != null}">
        <div class="error">Invalid username or password</div>
    </c:if>
    <form class="form-horizontal"
        action="<c:url value="/login" var="actionUrl" />" method="post">
        <sec:csrfInput />
        <div class="form-group">
            <label class="col-sm-2 control-label" for="username">User
                Name</label>
            <div class="col-sm-10">
                <input class="form-control" id="username" type="text"
                    name="username" autofocus="autofocus"
                    required="required" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="password">Password</label>
            <div class="col-sm-10">
                <input class="form-control" id="password"
                    type="password" name="password" required="required" />
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Sign
                    in</button>
            </div>
        </div>
    </form>
</div>
<jsp:include page="foot.jsp" />