package org.example.command.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.ICommandRestricted;
import org.example.commandmeta.SlashCommand;
import org.example.commandmeta.SlashContext;

import java.util.List;

public class Kick implements SlashCommand, ICommandRestricted {
	private final String name = "kick";
	private final String description = "Kicks the user.";
	private final String optionNameOne = "user";
	private final String optionDescriptionOne = "A user who needs to be kicked.";
	private final String optionNameTwo = "reason";
	private final String optionDescriptionTwo = "The reason to kick.";
	private final Permission permission = Permission.KICK_MEMBERS;

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
		Member member = ctx.getOptionAsMember(optionNameOne);
		String reason = ctx.getOptionAsString(optionNameTwo);

		if (member == null || member.isOwner() || member.getUser().isBot()) {
			ctx.ephemeral().reply(ctx.getI18n("insufficientPermissionUseCommand"));
			return;
		}

		member.kick().reason(reason).queue();
		ctx.reply(ctx.getI18n(reason == null ? "kickResponseMessageNoReason" : "kickResponseMessage", member.getAsMention(), reason));
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.USER, optionNameOne, optionDescriptionOne, true),
				new OptionData(OptionType.STRING, optionNameTwo, optionDescriptionTwo, false)
		);
	}
}
