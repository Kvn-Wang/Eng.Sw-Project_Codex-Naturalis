package it.polimi.codexnaturalis.network.util.networkMessage;

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

    // args(0) = list<PlayerInfo>  (aggiornamento appena un player entra in lobby)
    COM_JOIN_LOBBY_OTHER_PLAYER_INFO_TCP,

    //args(0) = joinedPlayerNick
    //args(1) = action that the player has done
    // (JOIN, LEFT, READY, COLOR(+ args(1) = color), WAIT (quando un player è da solo in lobby e mette ready))
    COM_LOBBY_STATUS_NOTIFY,

    /**
     * args(0): playerNick
     * args(1): color
     */
    COM_SET_PLAYER_COLOR,

    //args(0) = playerNickname
    COM_SET_READY_LOBBY_TCP,

    //args(0) = playerNickname
    COM_LEAVE_LOBBY_TCP,

    COM_ERROR_TCP,

    //args(0) = playerNickname
    //args(1) = lobbyName
    COM_CREATE_LOBBY_TCP,

    //args(0) = ArrayList<PlayerInfo> otherPlayers
    COM_CONNECT_GAME_TCP,


    /* ---- ENUMS FOR THE GAME PHASE: SETUP ---- */
    // args(0): 1 starterCard
    GAME_SETUP_GIVE_STARTER_CARD,

    // nickname = PlayerNick
    // args(0): StarterCard (after setting isCardUp)
    GAME_SETUP_STARTER_CARD_PLAY,

    /**
     * args(0): hand (with 2 resourceCard and 1 obj)
     * args(1): First Common Mission
     * args(2): Second Common Mission
     * args(3): topDeck resourceCard of shop
     * args(4): first visible resourceCard of shop
     * args(5): second visible resourceCard of shop
     * args(6): topDeck objectiveCard of shop
     * args(7): first visible objectiveCard of shop
     * args(8): second visible objectiveCard of shop
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
     * if valid -> args(2):  updated PlayerScoreResource
     * if valid -> args(3):  int updated board player score
     */
    PLACEMENT_CARD_OUTCOME,

    /**
     * args(0): playerNicknameGameMap
     * args(1): card
     * args(2): x_pos
     * args(3): y_pos
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



    /* ---- TODO: non implementati ---- */

    //quando un player va online/offline,
    // args(0) = playerDisconnectedNickname
    // args(1) = "true" o "false" boolean to string,
    STATUS_PLAYER_CHANGE,

    //in GameManager ha personalScoreBoardScore,
    // args(0) = message.getNickname()
    // args(1) = message.getArgs()
    SCORE_UPDATE,

    //args = argsGenerator(playerView.getGameMap())
    SWITCH_PLAYER_VIEW,

    //args = argsGenerator(getScoreResource())
    CORRECT_PLACEMENT
}
