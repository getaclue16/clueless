<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
<div style="text-align: center;">
    <table class="gameboard">
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

    <br />
    <div>
        <div>
            Move<br />
            <button id="move-up">Up</button>
            <br />
            <button id="move-left">Left</button>
            <button id="move-right">Right</button>
            <br />
            <button id="move-down">Down</button>
            <br />
            <button id="move-shortcut">Take Shortcut</button>
        </div>
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
    </div>

    <div style="clear: both;">
        Cards<br />
        <div class="card card-white">Mrs. White</div>
        <div class="card card-plum">Prof. Plum</div>
        <div class="card card-hall">Hall</div>
        <div class="card card-billiard">Billiard Room</div>
        <div class="card card-rope">Rope</div>
        <div class="card card-wrench">Wrench</div>
    </div>
</div>
<jsp:include page="foot.jsp" />