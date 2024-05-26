package it.polimi.codexnaturalis.network.util;

//If before the enum the args are not specified, it's not expected additional argument for the network message
public enum MessageType {
    /* ---ENUMS FOR THE COMMUNICATION PHASE--- */
    // args(0) = nickname
    COM_SET_NICKNAME_TCP,
    // args(0) = outcome (boolean); useful to communicate the outcome after receiving an order that can fail (like setNickname or joiningLobby)
    COM_ACK_TCP,
    //args(0) = lobbies in json (when receiving from the server)
    COM_GET_LOBBIES_TCP,
    //args(0) = playerName
    //args(1) = lobbyName
    COM_JOIN_LOBBY_TCP,
    //args(0) = playerNickname
    COM_SET_READY_LOBBY_TCP,
    //args(0) = playerNickname
    COM_LEAVE_LOBBY_TCP,
    COM_ERROR_TCP,
    //args(0) = playerNickname
    //args(1) = lobbyName
    COM_CREATE_LOBBY_TCP,
    //args(0) = GameController
    //args(1) = ArrayList<PlayerInfo> otherPlayers
    COM_CONNECT_GAME_TCP,
    COM_LOBBY_RMI,

    /* ---ENUMS FOR THE GAME PHASE--- */
    //quando un player va online/offline,
    // args(0) = playerDisconnectedNickname
    // args(1) = "true" o "false" boolean to string,
    STATUS_PLAYER_CHANGE,
    //in GameManager non ha args, arrivata in VirtualGame aggiunge "WRONG_TYPE_SHOP" come args
    WRONG_TYPE_SHOP,
    //no args
    NOT_YOUR_TURN,
    //in GameManager ha personalScoreBoardScore,
    // args(0) = message.getNickname()
    // args(1) = message.getArgs()
    SCORE_UPDATE,
    //args = argsGenerator(playerView.getGameMap())
    SWITCH_PLAYER_VIEW,
    //args = argsGenerator(this.hand)
    CORRECT_DRAW_CARD,
    //args = argsGenerator(getScoreResource()
    CORRECT_PLACEMENT
}
