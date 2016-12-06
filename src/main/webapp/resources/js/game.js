$(document).ready(function() {
    // User clicked start game button
    $("#start-game").click(function() {
        $.post("/game/start", {
            _csrf : $("input[name=_csrf]").val(),
            gameid : getGameId()
        }, function(data) {
            window.location.reload(1);
        }).fail(function(error) {
            showError(error);
        });
    });

    // User clicked on a location to move
    $(".room, .hallway").click(function(event) {
        var x = $(this).index() - 1;
        var y = $(this).parent().index() - 1;
        $.post("/game/action/move", {
            _csrf : $("input[name=_csrf]").val(),
            gameid : getGameId(),
            x : x,
            y : y
        }, function(data) {
            // TODO: the move was successful
        }).fail(function(error) {
            showError(error);
        });
    });

    // User clicked the guess button
    $("#suggest").click(function() {
        $("#suggestModal").modal("show");
    });

    // User clicked the accuse button
    $("#accuse").click(function() {
        $("#accuseModal").modal("show");
    });

    // User clicked the end turn button
    $("#end-turn").click(function() {
        $.post("/game/action/endturn", {
            _csrf : $("input[name=_csrf]").val(),
            gameid : getGameId()
        }, function(data) {
            // TODO: end turn was successful
        }).fail(function(error) {
            showError(error);
        });
    });

    // Show an error modal
    function showError(error) {
        $("#tbkModalLabel").html("Error");
        $("#tbkModalBody").html(error.responseText);
        $("#tbkModal").modal("show");
    }

    // Get the current game id
    function getGameId() {
        return $("#gameboard").attr("data-gameid");
    }

    // Load the game
    function loadGame() {
        if ($("#gamestatus").attr("data-gamestate") === "NEW") {
            // Game is new, wait 10 seconds and reload
            setTimeout(function() {
                window.location.reload(1);
            }, 10000);
        } else {
            $.get("/game/load", {
                gameid : getGameId()
            }, function(data) {
                // TODO: end turn was successful
            }).fail(function(error) {
                showError(error);
            });
        }
    }
    loadGame();
});
