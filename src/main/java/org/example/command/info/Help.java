package org.example.command.info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.CommandRegistry;
import org.example.commandmeta.SlashContext;
import org.example.commandmeta.util.Module;
import org.example.commandmeta.SlashCommand;
import org.example.i18n.I18n;


import java.util.ArrayList;
import java.util.List;

import static org.example.commandmeta.util.Module.parseToModule;

public class Help implements SlashCommand {
	private final String name = "help";
	private final String description = "Get help on commands and settings.";
	private final String optionNameOne = "category";
	private final String optionDescriptionOne = "Command category.";

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
		EmbedBuilder embed = ctx.getBaseEmbed();
		String category = ctx.getOptionAsString(optionNameOne);

		if (category != null) {
			embed.setTitle(ctx.getI18n("helpEmbedTitleCategory", ctx.getI18n("help.options.category.choices." + category + ".name")));

			String desc = ctx.getI18n("helpEmbedDescriptionTitle") +
					(category.equals("nsfw") ? "\n Only in nsfw channels." : "") +
					"\n\n";

			for (SlashCommand val : CommandRegistry.getCommandModule(parseToModule(category).get()).getCommands()) {
				String options = getOptionCommand(val, ctx.getI18n());
				desc += "`/" + val.getName() + " " + options + "`\n" + ctx.getI18n(val.getName() + ".description") + "\n\n";
			}

			embed.setDescription(desc);
		} else {
			embed.setTitle(ctx.getI18n("helpEmbedTitle"));
			embed.setThumbnail(ctx.getSelfUserAvatarUrl());

			for (Module module : CommandRegistry.getKeysModule()) {
				String key = "help.options.category.choices." + module.getAltName() + ".name";
				embed.addField(ctx.getI18n(key) + " " + module.getEmoji(), "`/help" + " " + module.getAltName() + "`", true);
			}
			embed.addBlankField(true);
		}

		switch (ctx.getGuildSettingsDb().getSendingHelp()) {
			case "hidden": {
				ctx.ephemeral().reply(embed.build());
				break;
			}
			case "unhidden": {
				ctx.reply(embed.build());
				break;
			}
			case "private-messages": {
				ctx.sendPrivateMessage(ctx.getMember(), embed.build());
				break;
			}
		}
//		ctx.reply(embed.build());
	}

	private String getOptionCommand(SlashCommand command, I18n i18n) {
		return String.join(" ", command.getOptions().stream().map(
						opt -> {
							String key = command.getName() + ".options." + opt.getName() + ".name";
							return opt.isRequired() ? "<" + i18n.i18n(key) + ">" : "[" + i18n.i18n(key) + "]";
						}
				).toList()
		);
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptionOne, false)
						.addChoices(getChoicesOption())
//				new OptionData(OptionType.STRING, "command", "Название команды для отображения справки по ней.", false, false)
		);
	}

	@Override
	public List<Command.Choice> getChoicesOption() {
		List<Command.Choice> choices = new ArrayList<>();

		for (Module module : CommandRegistry.getKeysModule()) {
			String nameModule = module.getAltName();
			choices.add(new Command.Choice(nameModule, nameModule));
		}

		return choices;
	}
}