package org.example.commandmeta;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SlashContext extends Context<SlashCommandInteractionEvent> {
	public SlashContext(SlashCommandInteractionEvent event) {
		super(event);
	}
}
