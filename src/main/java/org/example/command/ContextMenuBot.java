package org.example.command;

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ContextMenuBot extends ListenerAdapter {
	@Override
	public void onUserContextInteraction(UserContextInteractionEvent event) {
		if (event.getName().equals("Get user avatar")) {
			event.reply("Avatar: " + event.getTarget().getEffectiveAvatarUrl()).queue();
		}
	}

	@Override
	public void onMessageContextInteraction(MessageContextInteractionEvent event) {
		if (event.getName().equals("Count words")) {
			event.reply("Слов: " + event.getTarget().getContentRaw().split("\\s+").length).queue();
		}
	}
}