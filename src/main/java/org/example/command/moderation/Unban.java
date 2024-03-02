package org.example.command.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.ICommandRestricted;
import org.example.commandmeta.JCommand;
import org.example.commandmeta.SlashCommand;
import org.example.commandmeta.SlashContext;

import java.util.List;
import java.util.stream.Stream;

public class Unban implements SlashCommand, ICommandRestricted {
	private final String name = "unban";
	private final String description = "Unbanned user.";
	private final String optionNameOne = "id_or_tag";
	private final String optionDescriptionOne = "User id or tag.";
	private final Permission permission = Permission.BAN_MEMBERS;

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
		String userId = ctx.getOptionAsString(optionNameOne);

		User user = ctx.getUserById(userId);
		if (user == null) {
			ctx.ephemeral().reply(ctx.getI18n("userNotFound"));
			return;
		}

		ctx.getGuild().unban(user).queue(
				(success) -> {
					ctx.reply(ctx.getI18n("unbanResponseSuccessMessage", user.getAsMention()));

				},
				(failure) -> {
					ctx.ephemeral().reply(ctx.getI18n("unbanResponseFailureMessage"));
				}
		);
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptionOne, true)
		);
	}
}
