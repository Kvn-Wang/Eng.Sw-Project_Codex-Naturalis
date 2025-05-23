package it.polimi.codexnaturalis.network.util.networkMessage;

//If before the enum the args are not specified, it's not expected additional argument for the network message
public enum MessageType {
    /* ---ENUMS FOR THE COMMUNICATION PHASE--- */
    // args(0) = nickname
    COM_SET_NICKNAME_TCP,
    // args(0) = outcome (boolean)
    COM_SET_NICKNAME_RESPONSE_TCP,

    //args(0) = lobbies in json (when receiving from the server)
    COM_GET_LOBBIES_TCP,
    // args(0) = outcome (boolean)
    COM_GET_LOBBIES_RESPONSE_TCP,

    //args(0) = playerName
    //args(1) = lobbyName
    COM_JOIN_LOBBY_TCP,
    // args(0) = outcome (boolean)
    COM_JOIN_LOBBY_TCP_OUTCOME,

    // args(0) = list<PlayerInfo>  (aggiornamento appena un player entra in lobby)
    COM_JOIN_LOBBY_OTHER_PLAYER_INFO_TCP,

    //args(0) = joinedPlayerNick
    //args(1) = action that the player has done
    // (JOIN, LEFT, READY, WAIT (quando un player è da solo in lobby e mette ready))
    COM_LOBBY_STATUS_NOTIFY,

    /**
     * args(0): playerNick
     * args(1): color (enum)
     */
    COM_SET_PLAYER_COLOR,
    /**
     * args(0): isValid
     * if isValid -> args(1): nickname that chose the color
     * if isValid -> args(2): color
     */
    COM_SET_PLAYER_COLOR_OUTCOME,

    //args(0) = playerNickname
    COM_SET_READY_LOBBY_TCP,
    COM_SET_READY_LOBBY_RESPONSE_TCP,

    //args(0) = playerNickname
    COM_LEAVE_LOBBY_TCP,
    COM_LEAVE_LOBBY_RESPONSE_TCP,

    //args(0) = playerNickname
    //args(1) = lobbyName
    COM_CREATE_LOBBY_TCP,
    // args(0) = outcome (boolean)
    COM_CREATE_LOBBY_OUTCOME_TCP,

    //args(0) = ArrayList<PlayerInfo> otherPlayers
    COM_CONNECT_GAME_TCP,


    /* ---- ENUMS FOR THE GAME PHASE: SETUP ---- */
    // args(0): 1 starterCard
    GAME_SETUP_GIVE_STARTER_CARD,

    // nickname = PlayerNick
    // args(0): StarterCard (after setting isCardUp)
    GAME_SETUP_STARTER_CARD_PLAY,

    /**
     * args(0): Resource card
     * args(1): Resource card
     * args(2): Obj card
     * args(3): First Common Mission
     * args(4): Second Common Mission
     * args(5): topDeck resourceCard of shop
     * args(6): first visible resourceCard of shop
     * args(7): second visible resourceCard of shop
     * args(8): topDeck objectiveCard of shop
     * args(9): first visible objectiveCard of shop
     * args(10): second visible objectiveCard of shop
     */
    GAME_SETUP_INIT_HAND_COMMON_MISSION_SHOP,

    /**
     *  args(0): first personal Mission
     *  args(1): second personal Mission
     */
    GAME_SETUP_SEND_PERSONAL_MISSION,

    /**
     *  args(0): playerNick
     *  args(1): PersonalMissionSelected (Mission)
     */
    GAME_SETUP_CHOOSE_PERSONAL_MISSION,

    /**
     * args(0): isYourTurn (boolean)
     */
    GAME_SETUP_NOTIFY_TURN,


    /* ---- ENUMS FOR THE GAME PHASE: PLAY ---- */

    /**
     * no args
     */
    YOUR_TURN,

    /**
     * no args
     */
    NOT_YOUR_TURN,

    /**
     * When requesting invalid action during a certain game phase,
     * ex. play a card during draw phase.
     * args(0) = currentGameState (enum GameState)
     */
    ERR_GAME_STATE_COMMAND,

    /**
     * args(0): playerNick
     * args(1): card
     * args(2): x_pos
     * args(3): y_pos
     */
    PLAY_CARD,

    /**
     * args(0): isValid (boolean)
     * if valid -> args(1):  card that has been placed
     * if valid -> args(2):  card that has been placed
     * if valid -> args(3):  card that has been placed
     * if valid -> args(4):  updated PlayerScoreResource
     * if valid -> args(5):  int updated board player score
     */
    PLACEMENT_CARD_OUTCOME,

    /**
     * args(0): playerNicknameGameMap
     * args(1): card
     * args(2): x_pos
     * args(3): y_pos
     * args(4): his scoreboard player score
     */
    UPDATE_OTHER_PLAYER_GAME_MAP,

    /**
     * args(0): playerNick
     * args(1): where? RESOURCE or OBJECTIVE (enum)
     * args(2): which card? 1 or 2 or 0 (for topDeckCard)
     */
    DRAW_CARD,

    /**
     * args(0): Card
     */
    DRAWN_CARD_DECK,

    /**
     * update the clients that the shop selection has changed
     * args(0): Card (of shop)
     * args(1): shopType (RESOURCE or OBJECTIVE (enum))
     * args(2): which card? 0 (topDeck) or 1 or 2
     */
    DRAW_CARD_UPDATE_SHOP_CARD_POOL,

    /**
     * args(0): sender nick
     * args(1): receiver nick
     * args(2): message
     */
    WRITE_MESSAGE,

    /**
     * args(0): sender nick
     * args(1): message
     */
    INCOMING_MESSAGE,

    NOTIFY_FINAL_TURN,
    /**
     * args(0) = {@code ArrayList<String>} winners
     */
    GAME_ENDED
}
