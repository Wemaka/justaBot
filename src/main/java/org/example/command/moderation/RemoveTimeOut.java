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

public class RemoveTimeOut implements SlashCommand, ICommandRestricted {
	private final String name = "remove-timeout";
	private final String description = "Deletes the timeout early.";
	private final String optionNameOne = "user";
	private final String optionDescriptionOne = "The user to remove timeout.";
	private final Permission permission = Permission.MODERATE_MEMBERS;

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

		if (member == null || member.isOwner() || member.getUser().isBot()) {
			ctx.ephemeral().reply(ctx.getI18n("insufficientPermissionUseCommand"));
			return;
		}

		member.removeTimeout().queue();
		ctx.reply(ctx.getI18n("remove-timeoutResponseMessage", member.getUser().getAsMention()));
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.USER, optionNameOne, optionDescriptionOne, true)
		);
	}
}
