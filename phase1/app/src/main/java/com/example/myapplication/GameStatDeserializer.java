package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GameStatDeserializer implements JsonDeserializer<GameStatus> {
    private String gameStatTypeName;
    private Gson gson;
    private Map<String, Class<? extends GameStatus>> gameStatTypeRegistry;

    public GameStatDeserializer(String gameManagerTypeName){
        this.gson = new Gson();
        this.gameStatTypeRegistry = new HashMap<>();
        this.gameStatTypeName = gameManagerTypeName;
    }

    public void registerGameType(String gameTypeName, Class<? extends GameStatus> GameType) {
        gameStatTypeRegistry.put(gameTypeName, GameType);
    }

    @Override
    public GameStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject gameStatusObject = json.getAsJsonObject();
        JsonElement gameStatType = gameStatusObject.get(gameStatTypeName);
        Class<? extends GameStatus> gameType = gameStatTypeRegistry.get(gameStatType.getAsString());
        return gson.fromJson(gameStatusObject, gameType);
    }
}
