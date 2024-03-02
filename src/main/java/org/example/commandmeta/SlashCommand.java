package org.example.commandmeta;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import net.dv8tion.jda.api.interactions.commands.localization.ResourceBundleLocalizationFunction;
import org.example.commandmeta.CommandRegistry;
import org.example.commandmeta.SlashContext;
import org.example.commandmeta.util.Module;

import java.util.ArrayList;
import java.util.List;

public interface SlashCommand extends JCommand {
	//	LocalizationFunction localizationFunction = ResourceBundleLocalizationFunction
//			.fromBundles("lang.bottext", DiscordLocale.RUSSIAN, DiscordLocale.ENGLISH_US)
//			.build();
//
//	CommandData getCommandData();
//
//	String getName();
//
	String getDescription();

//	void execute(SlashContext ctx);


	default List<OptionData> getOptions() {
		return List.of();
	}

	default List<Command.Choice> getChoicesOption() {
		return List.of();
	}
}
