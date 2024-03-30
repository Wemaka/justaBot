package org.example.command.fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.SlashContext;
import org.example.commandmeta.util.Emoji;
import org.example.commandmeta.SlashCommand;

import java.util.List;

public class Info implements SlashCommand {
	private final String name = "info";
	private final String description = "Outputs probability from 0 to 100%.";
	private final String optionNameOne = "text";
	private final String optionDescriptonOne = "The probability of what event/action you want to know.";

	@Override
	public CommandData getCommandData() {
		return Commands.slash(name, description)
				.setLocalizationFunction(localizationFunction)
				.addOptions(getOptions());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override    //	@Override
	public void execute(SlashContext ctx) {
		String text = ctx.getOptionAsString(optionNameOne);

		EmbedBuilder embed = ctx.getBaseEmbed()
				.setAuthor(ctx.getMemberEffectiveName() + " - /" + ctx.getI18n("info.name") + " " + text, null, ctx.getUserAvatarUrl())
				.setDescription(ctx.getI18n("infoEmbedDescription", Emoji.CRYSTALBALL, text, String.valueOf((int) Math.floor(Math.random() * 100))));

		ctx.reply(embed.build());
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptonOne, true)
		);
	}
}
