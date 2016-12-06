<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="${game.name}" />
</jsp:include>
<div class="text-center">
    <table class="gameboard" data-gameid="${game.id}" id="gameboard">
        <tr>
            <td colspan="4">&nbsp;</td>
            <td class="start" id="startscarlet"><span>Miss
                    Scarlet </span>start</td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="room" id="study"><span class="roomname">Study</span></td>
            <td class="hallway"><div class="hhall">&nbsp;</div></td>
            <td class="room" id="hall"><span class="roomname">Hall</span></td>
            <td class="hallway"><div class="hhall">&nbsp;</div></td>
            <td class="room" id="lounge"><span class="roomname">Lounge</span></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td class="start" id="startplum"><span>Prof.
                    Plum </span>start</td>
            <td class="hallway"><div class="vhall">&nbsp;</div></td>
            <td class="void">&nbsp;</td>
            <td class="hallway"><div class="vhall">&nbsp;</div></td>
            <td class="void">&nbsp;</td>
            <td class="hallway"><div class="vhall">&nbsp;</div></td>
            <td class="start" id="startmustard"><span>Col.
                    Mustard </span>start</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="room" id="library"><span class="roomname">Library</span></td>
            <td class="hallway"><div class="hhall">&nbsp;</div></td>
            <td class="room" id="billiard"><span class="roomname">Billiard
                    Room</span></td>
            <td class="hallway"><div class="hhall">&nbsp;</div></td>
            <td class="room" id="dining"><span class="roomname">Dining
                    Room</span></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td class="start" id="startpeacock"><span>Mrs.
                    Peacock </span>start</td>
            <td class="hallway"><div class="vhall">&nbsp;</div></td>
            <td class="void">&nbsp;</td>
            <td class="hallway"><div class="vhall">&nbsp;</div></td>
            <td class="void">&nbsp;</td>
            <td class="hallway"><div class="vhall">&nbsp;</div></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="room" id="conservatory"><span
                class="roomname">Conservatory</span></td>
            <td class="hallway"><div class="hhall">&nbsp;</div></td>
            <td class="room" id="ballroom"><span class="roomname">Ballroom</span></td>
            <td class="hallway"><div class="hhall">&nbsp;</div></td>
            <td class="room" id="kitchen"><span class="roomname">Kitchen</span></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
            <td class="start" id="startgreen"><span>Mr.
                    Green </span>start</td>
            <td>&nbsp;</td>
            <td class="start" id="startwhite"><span>Mrs.
                    White </span>start</td>
            <td colspan="2">&nbsp;</td>
        </tr>
    </table>
    <div id="gamestatus" data-gamestate="${game.state}">
        <h2>Game Status</h2>
        <c:if test="${game.state == 'NEW'}">
            Game has not started. There are currently ${fn:length(game.players)} players joined.
            <c:if
                test="${game.players[0].username == user && fn:length(game.players) >= 3}">
                <div>
                    <br />
                    <button id="start-game">Start Game</button>
                </div>
            </c:if>
        </c:if>
        <c:if test="${game.state == 'IN_PROGRESS'}">
            Game is in progress. It is <c:out
                value="${game.activePlayer.guest.name}" />'s turn.
        </c:if>
    </div>
    <br />
    <div>
        <c:if test="${game.state == 'IN_PROGRESS'}">
            <h2>Actions</h2>
            <div>
                <br />
                <button id="guess">Make Guess</button>
            </div>
            <div>
                <br />
                <button id="accuse">Accuse</button>
            </div>
            <div>
                <br />
                <button id="end-turn">End Turn</button>
            </div>
        </c:if>
    </div>

    <div style="clear: both;">
        <c:if test="${game.state != 'NEW'}">
            <h2>Your Cards</h2>
            <c:forEach items="${game.players}" var="player">
                <c:if test="${player.username == user}">
                    <c:forEach items="${player.cards}" var="card">
                        <div
                            class="card card-<c:out value="${card.shortName}" />">
                            <c:out value="${card.name}" />
                        </div>
                    </c:forEach>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
</div>

<script src="<c:url value="/resources/js/game.js" />"></script>
<jsp:include page="foot.jsp" />