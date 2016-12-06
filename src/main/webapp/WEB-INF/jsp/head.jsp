<%@ page contentType="text/html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${param.title}-TheBee'sKnees</title>
<link rel="stylesheet" type="text/css"
    href="<c:url value="/resources/css/clueless.css" />" />
<link rel="stylesheet" type="text/css"
    href="<c:url value="/resources/css/bootstrap.min.css" />" />
<script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>
    <div class="container">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button"
                        class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span> <span
                            class="icon-bar"></span> <span
                            class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="<c:url value="/" />">The
                        Bee's Knees</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="<c:url value="/" />">Home</a></li>
                        <sec:authorize access="isAnonymous()">
                            <li><a href="<c:url value="/login" />">Login</a></li>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <li><a href="#"
                                onclick="document.getElementById('logoutform').submit()">Sign
                                    Out</a></li>
                        </sec:authorize>
                    </ul>
                </div>
                <!--/.nav-collapse -->
            </div>
        </nav>
        <!-- Modal -->
        <div class="modal fade" id="tbkModal" tabindex="-1" role="dialog"
            aria-labelledby="tbkModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close"
                            data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="tbkModalLabel">&nbsp;</h4>
                    </div>
                    <div class="modal-body" id="tbkModalBody">&nbsp;</div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default"
                            data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <sec:authorize access="isAuthenticated()">
            <form id="logoutform" action="<c:url value="/logout" />"
                method="post">
                <sec:csrfInput />
            </form>
        </sec:authorize>