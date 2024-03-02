package org.example.i18n;

import java.util.Locale;
import java.util.Optional;

public enum Language {
	EN_US("en", "US", "English"),
	RU_RU("ru", "RU", "Russian"),
	;

	private final String englishName;
	private final String code;
	private final Locale locale;

	Language(String language, String country, String englishName) {
		this.englishName = englishName;
		this.code = language + "_" + country;
		this.locale = new Locale(language, country);
	}

	public String getCode() {
		return code;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getEnglishName() {
		return englishName;
	}

	public static Optional<Language> parseToLang(String str) {
		for (Language lang : Language.values()) {
			if (lang.name().equalsIgnoreCase(str) ||
					lang.getCode().equalsIgnoreCase(str) ||
					lang.getEnglishName().equalsIgnoreCase(str)
			) {
				return Optional.of(lang);
			}
		}

		return Optional.empty();
	}
}
