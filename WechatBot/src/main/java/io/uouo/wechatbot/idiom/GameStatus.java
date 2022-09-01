package io.uouo.wechatbot.idiom;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameStatus {

    private static Map<String, IdiomGame> GAME_MAP = new ConcurrentHashMap<>();

    public static boolean isRunning(String roomid) {
        return GAME_MAP.containsKey(roomid);
    }

    public static IdiomGame getGame(String roomid) {
        return GAME_MAP.get(roomid);
    }

    public static void startGame(String roomid, IdiomGame game) {
        GAME_MAP.put(roomid, game);
    }

    public static void endGame(String roomid) {
        GAME_MAP.remove(roomid);
    }

}
