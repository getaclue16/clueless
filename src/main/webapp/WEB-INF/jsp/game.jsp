<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="${game.name}" />
</jsp:include>
<!-- Suggest Modal -->
<div class="modal fade" id="suggestModal" tabindex="-1" role="dialog"
    aria-labelledby="suggestModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="suggestModalLabel">Make
                    A Suggestion</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="selGuest">Guest:</label> <select
                        class="form-control" id="selGuest" name="guest">
                        <c:forEach items="${guests}" var="guest">
                            <option value="<c:out value="${guest}"/>"><c:out
                                    value="${guest.name}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="selGuest">Weapon:</label> <select
                        class="form-control text-capitalize"
                        id="selWeapon" name="weapon">
                        <c:forEach items="${weapons}" var="weapon">
                            <option class=""
                                value="<c:out value="${weapon}"/>"><c:out
                                    value="${weapon.name}" /></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary"
                    id="submitSuggestion">Submit Suggestion</button>
                <button type="button" class="btn btn-default"
                    data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>

<!-- Accuse Modal -->
<div class="modal fade" id="accuseModal" tabindex="-1" role="dialog"
    aria-labelledby="accuseModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="accuseModalLabel">Make
                    An Accusation</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="accuseGuest">Guest:</label> <select
                        class="form-control" id="accuseGuest"
                        name="guest">
                        <c:forEach items="${guests}" var="guest">
                            <option value="<c:out value="${guest}"/>"><c:out
                                    value="${guest.name}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="accuseWeapon">Weapon:</label> <select
                        class="form-control text-capitalize"
                        id="accuseWeapon" name="weapon">
                        <c:forEach items="${weapons}" var="weapon">
                            <option class=""
                                value="<c:out value="${weapon}"/>"><c:out
                                    value="${weapon.name}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="accuseRoom">Room:</label> <select
                        class="form-control text-capitalize"
                        id="accuseRoom" name="room">
                        <c:forEach items="${rooms}" var="room">
                            <option class=""
                                value="<c:out value="${room}"/>"><c:out
                                    value="${room.name}" /></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary"
                    id="submitAccusation">Submit Accusation</button>
                <button type="button" class="btn btn-default"
                    data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>

<div class="text-center">
    <div class="row">
        <div class="col-sm-8 col-md-6 col-lg-5">
            <table class="gameboard" data-gameid="${game.id}"
                id="gameboard">
                <tr>
                    <td colspan="4">&nbsp;</td>
                    <td class="start" id="startscarlet"><span>Miss
                            Scarlet </span>start
                        <div class="guest" id="scarlet">
                            <span class="sr-only">Miss Scarlet</span>&nbsp;
                        </div></td>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="room" id="study"><span
                        class="roomname">Study</span>
                        <div style="clear: both;"></div></td>
                    <td class="hallway"><div class="hhall">&nbsp;</div></td>
                    <td class="room" id="hall"><span
                        class="roomname">Hall</span>
                        <div style="clear: both;"></div></td>
                    <td class="hallway"><div class="hhall">&nbsp;</div></td>
                    <td class="room" id="lounge"><span
                        class="roomname">Lounge</span>
                        <div style="clear: both;"></div></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="start" id="startplum"><span>Prof.
                            Plum </span>start
                        <div class="guest" id="plum">
                            <span class="sr-only">Prof. Plum</span>&nbsp;
                        </div></td>
                    <td class="hallway"><div class="vhall">&nbsp;</div></td>
                    <td class="void">&nbsp;</td>
                    <td class="hallway"><div class="vhall">&nbsp;</div></td>
                    <td class="void">&nbsp;</td>
                    <td class="hallway"><div class="vhall">&nbsp;</div></td>
                    <td class="start" id="startmustard"><span>Col.
                            Mustard </span>start
                        <div class="guest" id="mustard">
                            <span class="sr-only">Col. Mustard</span>&nbsp;
                        </div></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="room" id="library"><span
                        class="roomname">Library</span>
                        <div style="clear: both;"></div></td>
                    <td class="hallway"><div class="hhall">&nbsp;</div></td>
                    <td class="room" id="billiard"><span
                        class="roomname">Billiard Room</span>
                        <div style="clear: both;"></div></td>
                    <td class="hallway"><div class="hhall">&nbsp;</div></td>
                    <td class="room" id="dining"><span
                        class="roomname">Dining Room</span>
                        <div style="clear: both;"></div></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="start" id="startpeacock"><span>Mrs.
                            Peacock </span>start
                        <div class="guest" id="peacock">
                            <span class="sr-only">Mrs. Peacock</span>&nbsp;
                        </div></td>
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
                        class="roomname">Conservatory</span>
                        <div style="clear: both;"></div></td>
                    <td class="hallway"><div class="hhall">&nbsp;</div></td>
                    <td class="room" id="ballroom"><span
                        class="roomname">Ballroom</span>
                        <div style="clear: both;"></div></td>
                    <td class="hallway"><div class="hhall">&nbsp;</div></td>
                    <td class="room" id="kitchen"><span
                        class="roomname">Kitchen</span>
                        <div style="clear: both;"></div></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                    <td class="start" id="startgreen"><span>Mr.
                            Green </span>start
                        <div class="guest" id="green">
                            <span class="sr-only">Mr. Green</span>&nbsp;
                        </div></td>
                    <td>&nbsp;</td>
                    <td class="start" id="startwhite"><span>Mrs.
                            White </span>start
                        <div class="guest" id="white">
                            <span class="sr-only">Mrs. White</span>&nbsp;
                        </div></td>
                    <td colspan="2">&nbsp;</td>
                </tr>
            </table>
        </div>
        <div class="col-sm-4 col-md-6 col-lg-7"
            style="max-height: 460px; overflow-x: hidden; overflow-y: scroll;">
            <h2>Players</h2>
            <table class="table table-condensed text-left">
                <thead>
                    <tr>
                        <th>Player</th>
                        <th>Guest</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${game.players}" var="player">
                        <tr>
                            <td><c:out value="${player.username}" /></td>
                            <td><c:out value="${player.guest.name}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h2>Action Log</h2>
            <div id="gamestatus" data-gamestate="${game.state}">
                <c:if test="${game.state == 'NEW'}">
            Game has not started. There are currently ${fn:length(game.players)} players joined.
            <c:if
                        test="${game.players[0].username == user && fn:length(game.players) >= 3}">
                        <div>
                            <br />
                            <button class="btn btn-primary"
                                id="start-game">Start Game</button>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
    <c:if test="${game.state == 'IN_PROGRESS'}">
        <h2>Actions</h2>
        <div class="row" id="actions">
            <div class="col-sm-2 col-sm-offset-3">
                <button class="btn btn-primary" id="suggest">Suggest</button>
            </div>
            <div class="col-sm-2">
                <button class="btn btn-primary" id="accuse">Accuse</button>
            </div>
            <div class="col-sm-2">
                <button class="btn btn-primary" id="end-turn">End
                    Turn</button>
            </div>
        </div>
        <div class="row" id="responses">
            <div class="col-sm-2 col-sm-offset-2">
                <button class="btn btn-primary" data-respond="0">No
                    cards to show</button>
            </div>
            <div class="col-sm-2">
                <button class="btn btn-primary" data-respond="1">Show
                    guest card</button>
            </div>
            <div class="col-sm-2">
                <button class="btn btn-primary" data-respond="2">Show
                    weapon card</button>
            </div>
            <div class="col-sm-2">
                <button class="btn btn-primary" data-respond="3">Show
                    room card</button>
            </div>
        </div>
    </c:if>

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