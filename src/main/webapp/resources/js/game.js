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
        $
                .get(
                        "/game/load",
                        {
                            gameid : getGameId()
                        },
                        function(game) {
                            // Update the buttons
                            if ($("body").attr("data-user") === game.activePlayer.username) {
                                $("#actions").show('slow');
                                $("#responses").hide('slow');
                            } else {
                                $("#actions").hide('slow');
                                $("#responses").show('slow');
                            }
                            // Update the player locations
                            var board = $("#gameboard");
                            $.each(game.players, function(pIndex, player) {
                                var x = player.currentLocation.x;
                                var y = player.currentLocation.y;
                                var p = $("#" + player.guest.toLowerCase());
                                if ((x + y) % 2 != 0) {
                                    board.find("tr").eq(y + 1).children("td")
                                            .eq(x + 1).children("div")
                                            .append(p);
                                } else {
                                    board.find("tr").eq(y + 1).children("td")
                                            .eq(x + 1).append(p);
                                }
                            });
                            // Update the non-player locations
                            // Update the weapon locations
                            // Update the status log
                            var statusDiv = $("#gamestatus");
                            statusDiv.empty();
                            $
                                    .each(
                                            game.turns,
                                            function(tIndex, turn) {
                                                var panelheading = $(document
                                                        .createElement('div'));
                                                panelheading
                                                        .addClass("panel-heading");
                                                if ($("body").attr("data-user") === turn.player.username) {
                                                    panelheading
                                                            .append("Your turn");
                                                } else {
                                                    panelheading
                                                            .append(turn.player.username
                                                                    + "'s turn");
                                                }
                                                var panelbody = $(document
                                                        .createElement('div'));
                                                panelbody
                                                        .addClass("panel-body");
                                                $
                                                        .each(
                                                                turn.actions,
                                                                function(
                                                                        aIndex,
                                                                        action) {
                                                                    panelbody
                                                                            .append(action.description);
                                                                    panelbody
                                                                            .append("<br/>");
                                                                });
                                                var panel = $(document
                                                        .createElement('div'));
                                                panel.addClass("panel");
                                                if ($("body").attr("data-user") === turn.player.username) {
                                                    panel
                                                            .addClass("panel-primary");
                                                } else {
                                                    panel
                                                            .addClass("panel-default");
                                                }
                                                panel.append(panelheading);
                                                panel.append(panelbody);
                                                statusDiv.prepend(panel);
                                            });
                        }).fail(function(error) {
                    showError(error);
                });
        // Reload in 5 seconds
        setTimeout(function() {
            loadGame();
        }, 5000);
    }
}

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
            loadGame();
        }).fail(function(error) {
            showError(error);
        });
    });

    // User clicked the guess button
    $("#suggest").click(function() {
        $("#suggestModal").modal("show");
    });

    $("#submitSuggestion").click(function() {
        $("#suggestModal").modal("hide");
        $.post("/game/action/guess", {
            _csrf : $("input[name=_csrf]").val(),
            gameid : getGameId(),
            guest : $("#selGuest").find(":selected").val(),
            weapon : $("#selWeapon").find(":selected").val()
        }, function(data) {
            loadGame();
        }).fail(function(error) {
            showError(error);
        });
    });

    // User clicked the accuse button
    $("#accuse").click(function() {
        $("#accuseModal").modal("show");
    });

    $("#submitAccusation").click(function() {
        $("#accuseModal").modal("hide");
        $.post("/game/action/accuse", {
            _csrf : $("input[name=_csrf]").val(),
            gameid : getGameId(),
            guest : $("#accuseGuest").find(":selected").val(),
            weapon : $("#accuseWeapon").find(":selected").val(),
            room : $("#accuseRoom").find(":selected").val()
        }, function(data) {
            loadGame();
        }).fail(function(error) {
            showError(error);
        });
    });

    // User clicked the end turn button
    $("#end-turn").click(function() {
        $.post("/game/action/endturn", {
            _csrf : $("input[name=_csrf]").val(),
            gameid : getGameId()
        }, function(data) {
            loadGame();
        }).fail(function(error) {
            showError(error);
        });
    });

    // User clicked a response button
    $("#responses").find("button").click(function(){
        $.post("/game/action/respond", {
            _csrf : $("input[name=_csrf]").val(),
            gameid : getGameId(),
            response : $(this).attr("data-respond")
        }, function(data) {
            loadGame();
        }).fail(function(error) {
            showError(error);
        });
    });

    loadGame();
});
