package org.example.command.fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.JCommand;
import org.example.commandmeta.SlashCommand;
import org.example.commandmeta.SlashContext;
import org.example.commandmeta.util.Emoji;

import java.util.List;

public class Should implements SlashCommand {
	private final String name = "should";
	private final String description = "Outputs \"should\" or \"shouldn't\".";
	private final String optionNameOne = "text";
	private final String optionDescriptionOne = "It can be any text or action.";

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
		String text = ctx.getOptionAsString(optionNameOne);
		String[] answer = {"Should", "Shouldn't"};
		int randomNum = (int) Math.floor(Math.random() * 2);

		EmbedBuilder embed = ctx.getBaseEmbed()
				.setAuthor(ctx.getMemberEffectiveName() + " - /" + ctx.getI18n("should.name") + " " + text, null, ctx.getUserAvatarUrl())
				.setDescription(ctx.getI18n(
						"shouldEmbedDescription",
						ctx.getMemberEffectiveName(),
						ctx.getI18n("shouldResponse" + answer[randomNum]), randomNum == 0 ? Emoji.OKHAND : Emoji.RAISEDHAND)
				);

		ctx.reply(embed.build());
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptionOne, true)
		);
	}
}
