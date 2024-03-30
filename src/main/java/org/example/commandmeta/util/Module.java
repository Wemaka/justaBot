package org.example.commandmeta.util;

import java.util.Optional;

public enum Module {
	ADMIN("admin", Emoji.PC),
	MODERATION("moderation", Emoji.SHIELD),
	INFO("info", Emoji.INFO),
	FUN("fun", Emoji.PARTY),
	NSFW("nsfw", Emoji.UNDERAGE),
	SETTINGS("settings", Emoji.GEAR),
	;

	private String altName;
	private String emoji;

	Module(String altName, String emoji) {
		this.altName = altName;
		this.emoji = emoji;
	}

	public String getAltName() {
		return altName;
	}

	public String getEmoji() {
		return emoji;
	}

	public static Optional<Module> parseToModule(String str) {
		for (Module module : Module.values()) {
			if (module.name().equalsIgnoreCase(str) || module.getAltName().equalsIgnoreCase(str)) {
				return Optional.of(module);
			}
		}

		return Optional.empty();
	}
}
