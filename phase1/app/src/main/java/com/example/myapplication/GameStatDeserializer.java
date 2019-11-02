package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GameStatDeserializer implements JsonDeserializer<GameStatus> {
    /**
     * the string "type" that is a common instance attribute of all subclasses of GameStat
     */
    private String gameStatTypeName;
    /**
     * the Gson object used to convert onject into Json
     */
    private Gson gson;
    /**
     * map each subclass name to it actual type
     */
    private Map<String, Class<? extends GameStatus>> gameStatTypeRegistry;

    /**
     * Construct a new GameStatDeserializer
     * @param gameManagerTypeName: name that is a common attribute of all subclasses of GameStatus
     */
     GameStatDeserializer(String gameManagerTypeName){
        this.gson = new Gson();
        this.gameStatTypeRegistry = new HashMap<>();
        this.gameStatTypeName = gameManagerTypeName;
    }

    /**
     * add subclass name and actual type into gameStatDeserializer
     * @param gameTypeName subclass name
     * @param GameType its type
     */

     void registerGameType(String gameTypeName, Class<? extends GameStatus> GameType) {
        gameStatTypeRegistry.put(gameTypeName, GameType);
    }

    /**
     * deserialize json into subclasses of GameStatus
     * @param json json representation of this GameStatus
     * @param typeOfT type of the return type
     * @param context the context that doing deserialize
     * @return the GameStatus in subclass form
     */
    @Override
    public GameStatus deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context){
        JsonObject gameStatusObject = json.getAsJsonObject();
        JsonElement gameStatType = gameStatusObject.get(gameStatTypeName);
        Class<? extends GameStatus> gameType = gameStatTypeRegistry.get(gameStatType.getAsString());
        return gson.fromJson(gameStatusObject, gameType);
    }
}
