package it.polimi.codexnaturalis.model.enumeration;

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
    COM_LOBBY_RMI,

    /* ---ENUMS FOR THE GAME PHASE--- */
    CORRECT_CHOSEN_COLOR,
    COLOR_ALREADY_CHOSEN,
    WRONG_TYPE_SHOP,

}
