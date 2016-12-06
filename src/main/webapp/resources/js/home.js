// Handle joining a game
function joinGame(gameId) {
    $.post(
            "/game/join",
            {
                _csrf : $("input[name=_csrf]").val(),
                gameid : gameId
            },
            function(game) {
                window.location.href = window.location.protocol + "//"
                        + window.location.host + "/game?gameid=" + game.id;
            }).fail(function(error) {
        $("#tbkModalLabel").html("Error");
        $("#tbkModalBody").html(error.responseText);
        $("#tbkModal").modal("show");
        getOpenGames();
    });
}

// Populate the table of open games
function getOpenGames() {
    $.get("/game/open", function(data) {
        var table = $("#opengames");
        table.empty();
        $.each(data, function(index, element) {
            table.append($("<tr>").click(function() {
                joinGame(element.id);
            }).append($("<td>").text(element.name),
                    $("<td>").text(element.players[0].username),
                    $("<td>").text(element.players.length)));
        });
    });
}
getOpenGames();

// Handler for the create game button
$("#create-game").click(
        function() {
            $.post(
                    "/game/create",
                    {
                        _csrf : $("input[name=_csrf]").val(),
                        name : $("#gamename").val()
                    },
                    function(game) {
                        window.location.href = window.location.protocol + "//"
                                + window.location.host + "/game?gameid="
                                + game.id;
                    }).fail(function(error) {
                $("#tbkModalLabel").html("Error");
                $("#tbkModalBody").html(error.responseText);
                $("#tbkModal").modal("show");
            });
        });