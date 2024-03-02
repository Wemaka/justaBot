package org.example.command.settings;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.ICommandRestricted;
import org.example.commandmeta.JCommand;
import org.example.commandmeta.SlashCommand;
import org.example.commandmeta.SlashContext;
import org.example.entity.GuildSettingsEntity;

import java.util.List;

public class Greeting implements SlashCommand, ICommandRestricted {
	private final String name = "greeting";
	private final String description = "Customize the welcome to new members.";
	private final String optionNameOne = "channel";
	private final String optionDescriptionOne = "Text channel (preferably public).";
	private final String optionNameTwo = "message";
	private final String optionDescriptionTwo = "Make up a greeting. Variable {member} - will mark the new member.";
	private final String optionNameThree = "disable";
	private final String optionDescriptionThree = "Do you want to disable the greeting ?";
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
		GuildChannelUnion textChannel = ctx.getOptionAsChannel(optionNameOne);
		String message = ctx.getOptionAsString(optionNameTwo);
		boolean disable = ctx.getOptionAsBoolean(optionNameThree);

		GuildSettingsEntity guildSettings = ctx.getGuildSettingsDb();
		if (textChannel != null) {
			guildSettings.setGreetingChannelId(textChannel.asTextChannel().getIdLong());
		}

		if (message != null) {
			guildSettings.setGreetingMessage(message);
		}

		guildSettings.setGreetingDisable(disable);

		ctx.getDb().updateDb(guildSettings);
		ctx.ephemeral().reply(ctx.getI18n("greetingResponseMessage"));
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.CHANNEL, optionNameOne, optionDescriptionOne, false)
						.setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD),
				new OptionData(OptionType.STRING, optionNameTwo, optionDescriptionTwo, false)
						.setMaxLength(255),
				new OptionData(OptionType.BOOLEAN, optionNameThree, optionDescriptionThree, false)
		);
	}
}
