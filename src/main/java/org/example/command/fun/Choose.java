package org.example.command.fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.JCommand;
import org.example.commandmeta.SlashContext;
import org.example.commandmeta.util.Emoji;
import org.example.commandmeta.SlashCommand;

import java.util.List;

public class Choose implements SlashCommand {
	private final String name = "choose";
	private final String description = "Randomly selects <text1> or <text2>";
	private final String optionNameOne = "text1";
	private final String optionDescriptionOne = "The first of the two values";
	private final String optionNameTwo = "text2";
	private final String optionDescriptionTwo = "The second of the two values";

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

	@Override
	public void execute(SlashContext ctx) {
		String[] texts = {ctx.getOptionAsString(optionNameOne), ctx.getOptionAsString(optionNameTwo)};

		EmbedBuilder embed = ctx.getBaseEmbed()
				.setAuthor(ctx.getMemberEffectiveName() + " - /" + ctx.getI18n("choose.name") + " " + texts[0] + ", " + texts[1], null, ctx.getUserAvatarUrl())
				.setDescription(ctx.getI18n("chooseEmbedDescription", Emoji.THINKING, texts[(int) Math.floor(Math.random() * 2)]));

		ctx.reply(embed.build());
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptionOne, true),
				new OptionData(OptionType.STRING, optionNameTwo, optionDescriptionTwo, true)
		);
	}
}
