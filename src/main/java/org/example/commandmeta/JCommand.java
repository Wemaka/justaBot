package org.example.commandmeta;

import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import net.dv8tion.jda.api.interactions.commands.localization.ResourceBundleLocalizationFunction;

import java.util.List;

public interface JCommand {
	public LocalizationFunction localizationFunction = ResourceBundleLocalizationFunction
			.fromBundles("lang.bottext", DiscordLocale.RUSSIAN, DiscordLocale.ENGLISH_US)
			.build();

	CommandData getCommandData();

	String getName();

	void execute(SlashContext ctx);
}
