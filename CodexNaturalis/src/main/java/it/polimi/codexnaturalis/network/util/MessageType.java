package it.polimi.codexnaturalis.network.util;

//If before the enum the args are not specified, it's not expected additional argument for the network message
public enum MessageType {
    /* ---ENUMS FOR THE COMMUNICATION PHASE--- */
    // args = nickname
    COM_SET_NICKNAME_TCP,
    // args = outcome (boolean); useful to communicate the outcome after receiving an order that can fail (like setNickname or joiningLobby)
    COM_ACK_TCP,
    COM_GET_LOBBIES_TCP,
    //args = lobbyName
    COM_JOIN_LOBBY_TCP,
    COM_SET_READY_LOBBY_TCP,
    COM_LEAVE_LOBBY_TCP,
    COM_ERROR_TCP,
    //args = lobbyName
    COM_CREATE_LOBBY_TCP,
    COM_LOBBY_RMI,

    /* ---ENUMS FOR THE GAME PHASE--- */
    STATUS_PLAYER_CHANGE,
    //args = "true" o "false" boolean to string
    WRONG_TYPE_SHOP,
    //in GameManager non ha args, arrivata in VirtualGame aggiunge "WRONG_TYPE_SHOP" come args
    NOT_YOUR_TURN,
    //no args
    SCORE_UPDATE,
    //in GameManager ha personalScoreBoardScore, arrivata in VirtualGame args diventa message.getNickname()+"$"+message.getArgs()
    SWITCH_PLAYER_VIEW,
    //args = argsGenerator(playerView.getGameMap())
    CORRECT_DRAW_CARD,
    //args = argsGenerator(this.hand)
    CORRECT_PLACEMENT,
    //args = argsGenerator(getScoreResource()
}
