package org.example.commandmeta;

import net.dv8tion.jda.api.interactions.commands.Command;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public interface SlashCommand extends JCommand {
	String getDescription();

	default List<OptionData> getOptions() {
		return List.of();
	}

	default List<Command.Choice> getChoicesOption() {
		return List.of();
	}
}
