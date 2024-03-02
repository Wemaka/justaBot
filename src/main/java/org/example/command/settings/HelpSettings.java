package org.example.command.settings;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.ICommandRestricted;
import org.example.commandmeta.SlashCommand;
import org.example.commandmeta.SlashContext;
import org.example.entity.GuildSettingsEntity;

import java.util.List;

public class HelpSettings implements SlashCommand, ICommandRestricted {
	private final String name = "help-settings";
	private final String description = "Configure the Help command.";
	private final String optionNameOne = "how-send";
	private final String optionDescriptionOne = "How you want to get help.";
	private final Permission permission = Permission.ADMINISTRATOR;

	@Override
	public Permission getPermission() {
		return permission;
	}

	@Override
	public CommandData getCommandData() {
		return Commands.slash(name, description)
				.setLocalizationFunction(localizationFunction)
				.addOptions(getOptions())
				.setDefaultPermissions(DefaultMemberPermissions.enabledFor(permission));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void execute(SlashContext ctx) {
		String send = ctx.getOptionAsString(optionNameOne);

		GuildSettingsEntity guildSettings = ctx.getGuildSettingsDb();
		guildSettings.setSendingHelp(send);
		ctx.getDb().updateDb(guildSettings);

		ctx.ephemeral().reply(ctx.getI18n("help-settingsResponseMessage", send));
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptionOne, true)
						.addChoices(
								new Command.Choice("hidden", "hidden"),
								new Command.Choice("unhidden", "unhidden"),
								new Command.Choice("private-messages", "private-messages")
						)
		);
	}
}
