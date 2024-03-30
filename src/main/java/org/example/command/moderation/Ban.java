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
import java.util.concurrent.TimeUnit;


public class Ban implements SlashCommand, ICommandRestricted {
	private final String name = "ban";
	private final String description = "Bans a user.";
	private final String optionNameOne = "user";
	private final String optionDescriptionOne = "The user to be banned.";
	private final String optionNameTwo = "time_unit";
	private final String optionDescriptionTwo = "Time unit.";
	private final String optionNameThree = "reason";
	private final String optionDescriptionThree = "The reason to ban.";
	private final String optionNameFour = "cleaning-days";
	private final String optionDescriptionFour = "Delete user's messages for the last <time-period> days (maximum 7 days).";
	private final int maxDayDeleteMessage = 7;
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
		Member member = ctx.getOptionAsMember(optionNameOne);
//		String timeUnitStr = ctx.getOptionAsString(optionNameTwo);
		String reason = ctx.getOptionAsString(optionNameThree);
		int timePeriod = ctx.getOptionAsInt(optionNameFour, 0);

		if (member == null) {
			ctx.ephemeral().reply(ctx.getI18n("userNotMemberGuild"));
			return;
		}

		if (member.isOwner() || !ctx.getMember().canInteract(member)) {
			ctx.ephemeral().reply(ctx.getI18n("insufficientPermissionUseCommand"));
			return;
		}


//		TimeUnit timeUnit = switch (timeUnitStr) {
//			case "days" -> TimeUnit.DAYS;
//			case "hours" -> TimeUnit.HOURS;
//			case "minutes" -> TimeUnit.MINUTES;
//			case "seconds" -> TimeUnit.SECONDS;
//			default -> throw new IllegalStateException("Unexpected value: " + timeUnitStr);
//		};


		ctx.sendPrivateMessage(Long.parseLong(member.getId()), ctx.getI18n("userNotificationOfBan", ctx.getGuild().getName(), reason));

		ctx.getGuild().ban(member, timePeriod, TimeUnit.DAYS).queue();
		ctx.reply(ctx.getI18n(reason == null ? "banResponseMessageNoReason" : "banResponseMessage", member.getAsMention(), reason));
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.USER, optionNameOne, optionDescriptionOne, true),
//				new OptionData(OptionType.STRING, optionNameTwo, optionDescriptionTwo, true)
//						.addChoices(
//								new Command.Choice("Days", "days"),
//								new Command.Choice("Hours", "hours"),
//								new Command.Choice("Minutes", "minutes"),
//								new Command.Choice("Seconds", "seconds")
//						),
				new OptionData(OptionType.STRING, optionNameThree, optionDescriptionThree, false),
				new OptionData(OptionType.INTEGER, optionNameFour, optionDescriptionFour, false)
						.setMinValue(0)
						.setMaxValue(maxDayDeleteMessage)
		);
	}
}
