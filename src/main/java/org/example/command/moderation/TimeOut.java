package org.example.command.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.Command;
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

public class TimeOut implements SlashCommand, ICommandRestricted {
	private final String name = "timeout";
	private final String description = "Closes access to text and voice channels for a certain period of time (no more than 28 days).";
	private final String optionNameOne = "user";
	private final String optionDescriptionOne = "The user to timeout.";
	private final String optionNameTwo = "time_unit";
	private final String optionDescriptionTwo = "Time unit.";
	private final String optionNameThree = "time_period";
	private final String optionDescriptionThree = "Duration of the timeout (maximum 28 days).";
	private final int maxDayTimeOut = 28;
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
		String timeUnitStr = ctx.getOptionAsString(optionNameTwo);
		int timePeriod = ctx.getOptionAsInt(optionNameThree);

		if (member == null || member.isOwner() || member.getUser().isBot()) {
			ctx.ephemeral().reply(ctx.getI18n("insufficientPermissionUseCommand"));
			return;
		}

		TimeUnit timeUnit = switch (timeUnitStr) {
			case "days" -> TimeUnit.DAYS;
			case "hours" -> TimeUnit.HOURS;
			case "minutes" -> TimeUnit.MINUTES;
			case "seconds" -> TimeUnit.SECONDS;
			default -> throw new IllegalStateException("Unexpected value: " + timeUnitStr);
		};

		member.timeoutFor(timePeriod, timeUnit).queue();
		ctx.reply(ctx.getI18n(
				"timeoutResponseMessage",
				member.getAsMention(),
				String.valueOf(timePeriod),
				ctx.getI18n("timeoutResponseMessage" + timeUnitStr))
		);
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.USER, optionNameOne, optionDescriptionOne, true),
				new OptionData(OptionType.STRING, optionNameTwo, optionDescriptionTwo, true)
						.addChoices(
								new Command.Choice("Days", "days"),
								new Command.Choice("Hours", "hours"),
								new Command.Choice("Minutes", "minutes"),
								new Command.Choice("Seconds", "seconds")
						),
				new OptionData(OptionType.INTEGER, optionNameThree, optionDescriptionThree, true)
						.setMinValue(1)
		);
	}
}
