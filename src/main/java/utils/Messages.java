package utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Доступ к локализованным строкам. Все текстовые сообщения вынесены
 * в resource bundle i18n/messages[_xx].properties, благодаря чему их
 * можно прогонять через native2ascii.
 */
public final class Messages {

    private static final String BUNDLE = "i18n.messages";

    private Messages() {
    }

    /** Сообщение по ключу для текущей локали по умолчанию. */
    public static String get(String key) {
        return ResourceBundle.getBundle(BUNDLE).getString(key);
    }

    /** Сообщение по ключу для указанной локали. */
    public static String get(String key, Locale locale) {
        return ResourceBundle.getBundle(BUNDLE, locale).getString(key);
    }

    /** Сообщение с подстановкой параметров (MessageFormat). */
    public static String format(String key, Object... args) {
        return MessageFormat.format(get(key), args);
    }

    /** Сообщение с подстановкой параметров для указанной локали. */
    public static String format(String key, Locale locale, Object... args) {
        return MessageFormat.format(get(key, locale), args);
    }
}
