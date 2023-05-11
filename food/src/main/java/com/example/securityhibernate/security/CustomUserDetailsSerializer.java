package com.example.securityhibernate.security;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CustomUserDetailsSerializer implements JsonSerializer<CustomUserDetails> {

    @Override
    public JsonElement serialize(CustomUserDetails src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", src.getUsername());
        jsonObject.addProperty("password", src.getPassword());
        jsonObject.addProperty("role", src.getUser().getRoles().getRoleName());
        // Add other properties as needed
        return jsonObject;
    }
}