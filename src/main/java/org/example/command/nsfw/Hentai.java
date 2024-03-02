package org.example.command.nsfw;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.api.Rule34Api;
import org.example.commandmeta.JCommand;
import org.example.commandmeta.SlashCommand;
import org.example.commandmeta.SlashContext;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class Hentai implements SlashCommand {
	private final String name = "hentai";
	private final String description = "Sends a photo/video/gif from the rule34 website.";
	private final String optionNameOne = "tags";
	private final String optionDescriptionOne = "Read more on rule34. (tag1 tag2 - tag1 and tag2, ( tag1 ~ tag2 ) - tag1 or tag2, -tag - exclude tag)";
	private final String optionNameTwo = "format";
	private final String optionDescriptionTwo = "File Format.";
	private final String optionNameThree = "show";
	private final String optionDescriptionThree = "Do you want to show the result of the query to all participants ?";

	@Override
	public CommandData getCommandData() {
		return Commands.slash(name, description)
				.setLocalizationFunction(localizationFunction)
				.setNSFW(true)
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
		String format = ctx.getOptionAsString(optionNameTwo);
		String tagsOpt = ctx.getOptionAsString(optionNameOne);
		boolean show = !ctx.getOptionAsBoolean(optionNameThree, true);

		if (show) {
			ctx.ephemeral();
		}
		ctx.defer();

		List<String> tags = new ArrayList<>();
		if (tagsOpt != null) {
			tags.addAll(List.of(tagsOpt.split(" ")));
		}
		if (format != null) {
			switch (format) {
				case "video" -> {
					tags.add("video");
				}
				case "image" -> {
					tags.add("-video");
					tags.add("-animated");
				}
				case "gif" -> {
					tags.add("-video");
					tags.add("animated");
				}
			}
		}

		Element r = new Rule34Api.PostBuilder().tags(tags).build().getRandomPost();
		if (r != null) {
			ctx.reply(r.attr("file_url"));
		} else {
			ctx.ephemeral().reply(ctx.getI18n("postsNotFoundRule34"));
		}

	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptionOne, false),
				new OptionData(OptionType.STRING, optionNameTwo, optionDescriptionTwo, false)
						.addChoices(
								new Command.Choice("video", "video"),
								new Command.Choice("image", "image"),
								new Command.Choice("gif", "gif")
						),
				new OptionData(OptionType.BOOLEAN, optionNameThree, optionDescriptionThree, false)

		);
	}
}
