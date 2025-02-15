package de.karimkeshta.oneWayElytra.messages;

import de.karimkeshta.oneWayElytra.configuration.ConfigurationFile;

import java.util.Locale;

public class MessageFile extends ConfigurationFile {

    MessageFile(String configPath, String configName, Locale locale) {
        super(configPath, configName);
    }
}
