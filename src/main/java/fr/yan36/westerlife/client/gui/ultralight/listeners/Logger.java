package fr.yan36.westerlife.client.gui.ultralight.listeners;

import com.labymedia.ultralight.plugin.logging.UltralightLogLevel;
import com.labymedia.ultralight.plugin.logging.UltralightLogger;
import fr.yan36.westerlife.Main;

public class Logger implements UltralightLogger {
    /**
     * This is called by Ultralight every time a message needs to be logged. Note that Ultralight messages may include
     * new lines, so if you want really pretty log output reformat the string accordingly.
     * <p>
     * This logger is <b>NOT</b> called for {@code console.log} messages, see  for that
     * instead.
     *
     * @param level   The level of the message
     * @param message The message to log
     */
    @Override
    public void logMessage(UltralightLogLevel level, String message) {
        switch (level) {
            case ERROR:
                Main.logger.error("[Ultralight/ERR] " + message);
                break;

            case WARNING:
                Main.logger.warn("[Ultralight/WARN] " + message);
                break;

            case INFO:
                Main.logger.info("[Ultralight/INFO] " + message);
                break;
        }
    }
}
