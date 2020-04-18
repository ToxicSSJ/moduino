package me.itoxic.moduino.util.properties;

import me.itoxic.moduino.util.properties.type.PropertiesFileType;
import com.google.gson.Gson;

import java.io.*;

public class PropertiesHelper {

    private static final String EXTENSION = ".json";

    public static ArduinoModelProperties openFile(PropertiesFileType type) {

        String fileName = type.getPath() + EXTENSION;
        InputStream url = PropertiesHelper.class.getClassLoader().getResourceAsStream(fileName);

        final BufferedReader reader = new BufferedReader(new InputStreamReader(url));
        return new Gson().fromJson(reader, ArduinoModelProperties.class);

    }

}
