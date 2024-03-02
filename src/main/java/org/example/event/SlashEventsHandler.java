package org.example.event;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.restaction.pagination.BanPaginationAction;
import org.example.commandmeta.*;
import org.example.i18n.I18n;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SlashEventsHandler extends ListenerAdapter {
	@Override
	public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
		for (CommandRegistry commandRegistry : CommandRegistry.getValuesModule()) {
			if (commandRegistry.getMapCommands().containsKey(event.getName())) {
				SlashCommand command = commandRegistry.getMapCommands().get(event.getName());
				SlashContext slashContext = new SlashContext(event);

				if (command instanceof ICommandRestricted) {
					if (!slashContext.hasPermission(((ICommandRestricted) command).getPermission())) {
						slashContext.ephemeral().reply(slashContext.getI18n("noPermissionUseCommand"));
						return;
					}
				}

				command.execute(slashContext);
				break;
			}
		}
	}

//	@Override
//	public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
//		if (event.getName().equals("unban") && event.getFocusedOption().getName().equals("user")) {
//			List<Guild.Ban> bans = event.getGuild().retrieveBanList().stream().toList();
//
//
//			List<Command.Choice> option = new ArrayList<>();
//			for (Guild.Ban user : bans) {
//				option.add(new Command.Choice(user.getUser().getEffectiveName(), user.getUser().getId()));
//			}
//
//			event.replyChoices(option).queue();
//		}
//	}
}
