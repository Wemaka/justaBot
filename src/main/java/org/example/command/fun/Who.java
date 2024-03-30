package org.example.command.fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.SlashContext;
import org.example.commandmeta.util.Emoji;
import org.example.commandmeta.SlashCommand;

import java.util.List;

public class Who implements SlashCommand {
	private final String name = "who";
	private final String description = "selects a random user";
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
		List<Member> allMembers = ctx.getMembers();
		Member randomMember = allMembers.get((int) Math.floor(Math.random() * allMembers.size()));

		EmbedBuilder embed = ctx.getBaseEmbed()
				.setAuthor(ctx.getMemberEffectiveName() + " - /" + ctx.getI18n("who.name") + " " + text, null, ctx.getUserAvatarUrl())
				.setDescription(ctx.getI18n("whoEmbedDescription", Emoji.THINKING, randomMember.getEffectiveName(), text));

		ctx.reply(embed.build());
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptionOne, true)
		);
	}
}
