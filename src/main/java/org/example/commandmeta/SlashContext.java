package org.example.commandmeta;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.i18n.I18n;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

public class SlashContext extends Context<SlashCommandInteractionEvent> {
	public SlashContext(SlashCommandInteractionEvent event) {
		super(event);
	}
}
