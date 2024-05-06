package io.github.yuko1101.allfivestars.config;

import com.google.gson.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigFile {
    public File file;
    public JsonObject def;
    private final ArrayList<String> route;

    public JsonObject data;

    public ConfigFile(File file, JsonObject def, ArrayList<String> route, JsonObject storedData) {
        this.file = file;
        this.def = def;
        this.route = route;

        if (storedData != null) {
            this.data = storedData;
        } else {
            this.data = def;
        }
    }

    public ConfigFile(File file, JsonObject def) {
        this.file = file;
        this.def = def;
        this.route = new ArrayList<String>();

        this.data = def;
    }

    public ConfigFile save(boolean compact) throws IOException {
        if (file.getParentFile() != null && !file.getParentFile().exists()) file.getParentFile().mkdirs();
        if (!file.exists()) file.createNewFile();
        PrintWriter out = new PrintWriter(file);
        if (compact) {
            out.println(data.toString());
        } else {
            out.println(new GsonBuilder().serializeNulls().setPrettyPrinting().create().toJson(data));
        }
        out.close();
        return this;
    }
    public ConfigFile save() throws IOException {
        return save(false);
    }

    public ConfigFile load() throws IOException {
        if (!file.exists()) {
            save();
        }
        try {
            data = JsonParser.parseString(Files.lines(file.toPath()).collect(Collectors.joining(System.lineSeparator()))).getAsJsonObject();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            save(); //ファイルを変更前に戻す
        }
        return this;
    }

    public ConfigFile set(String key, JsonElement value) {
        getObjectFromPath().add(key, value);
        return this;
    }
    public ConfigFile set(String key, String value) {
        getObjectFromPath().addProperty(key, value);
        return this;
    }
    public ConfigFile set(String key, int value) {
        getObjectFromPath().addProperty(key, value);
        return this;
    }
    public ConfigFile set(String key, double value) {
        getObjectFromPath().addProperty(key, value);
        return this;
    }
    public ConfigFile set(String key, float value) {
        getObjectFromPath().addProperty(key, value);
        return this;
    }
    public ConfigFile set(String key, long value) {
        getObjectFromPath().addProperty(key, value);
        return this;
    }
    public ConfigFile set(String key, boolean value) {
        getObjectFromPath().addProperty(key, value);
        return this;
    }

    public ConfigFile set(JsonElement value) {
        getPreObjectFromPath().add(getLastRoute(), value);
        return this;
    }
    public ConfigFile set(String value) {
        getPreObjectFromPath().addProperty(getLastRoute(), value);
        return this;
    }
    public ConfigFile set(int value) {
        getPreObjectFromPath().addProperty(getLastRoute(), value);
        return this;
    }
    public ConfigFile set(double value) {
        getPreObjectFromPath().addProperty(getLastRoute(), value);
        return this;
    }
    public ConfigFile set(float value) {
        getPreObjectFromPath().addProperty(getLastRoute(), value);
        return this;
    }
    public ConfigFile set(long value) {
        getPreObjectFromPath().addProperty(getLastRoute(), value);
        return this;
    }
    public ConfigFile set(boolean value) {
        getPreObjectFromPath().addProperty(getLastRoute(), value);
        return this;
    }

    public JsonElement getValue(String key) {
        return getObjectFromPath().get(key);
    }
    public JsonElement getValue() {
        return getPreObjectFromPath().get(getLastRoute());
    }

    public ConfigFile get(String key) {
        ArrayList<String> newRoute = new ArrayList<String>(route);
        newRoute.add(key);
        return new ConfigFile(file, def, newRoute, data);
    }

    public ConfigFile getPath(List<String> path) {
        ArrayList<String> newRoute = new ArrayList<String>(route);
        newRoute.addAll(path);
        return new ConfigFile(file, def, newRoute, data);
    }

    public boolean has(String key) {
        return getObjectFromPath().has(key);
    }

    public boolean exists() {
        return hasPath(route);
    }

    public ConfigFile resetData() {
        data = def;
        return this;
    }

    public ConfigFile resetPath() {
        route.clear();
        return this;
    }

    private JsonObject getObjectFromPath() {
        //println(route)
        //println(data)
        JsonObject obj = data;
        for (String s : route) {
            obj = obj.get(s).getAsJsonObject();
        }
        return obj;
    }
    private JsonObject getPreObjectFromPath() {
        //println(route)
        //println(data)
        JsonObject obj = data;
        for (int i = 0; i < route.size() - 1; i++) {
            obj = obj.get(route.get(i)).getAsJsonObject();
        }
        return obj;
    }
    private String getLastRoute() {
        return route.get(route.size() - 1);
    }

    private boolean hasPath(List<String> path) {
        JsonObject obj = data;
        for (String s : path) {
            if (!obj.has(s)) return false;
            obj = obj.get(s).getAsJsonObject();
        }
        return true;
    }
}