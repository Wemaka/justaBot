package org.example.i18n;

import org.example.service.BotDb;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
	private static final BotDb db = BotDb.getDb();
	//	public static final String DEFAULTLOCALE = "en_US";
	private final String guildLocale;

	public I18n(Long guildId) {
		//		this.guildLocale = guilds.get(this.guildId).getGuildSettings().getLanguage();
		this.guildLocale = db.getGuildsService().getGuilds().get(guildId).getGuildSettings().getLanguage();
	}

	public String i18n(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("lang.bottext", new Locale(guildLocale));
		return bundle.getString(key);

//		if (bundle.containsKey(key)) {
//			return bundle.getString(key);
//		}
//
//		System.out.println("There is no language entry for the locale " + locale);
//		return ResourceBundle.getBundle("lang.bottext", new Locale(DEFAULTLOCALE)).getString(key);
	}

	public String i18nFormat(String key, String... params) {
		if (params.length == 0) {
//			log
			return "I18n#i18nFormat() called with empty or null params";
		}
		return MessageFormat.format(i18n(key), params);
	}
}
