package org.example.command.settings;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.commandmeta.ICommandRestricted;
import org.example.commandmeta.JCommand;
import org.example.commandmeta.SlashCommand;
import org.example.commandmeta.SlashContext;
import org.example.entity.GuildSettingsEntity;
import org.example.i18n.I18n;
import org.example.i18n.Language;

import java.util.ArrayList;
import java.util.List;

public class Lang implements SlashCommand, ICommandRestricted {
	private final String name = "lang";
	private final String description = "Changes the language of the bot interface. The language of commands depends on the Discord language.";
	private final String optionNameOne = "language";
	private final String optionDescriptionOne = "Interface language";
	private final Permission permission = Permission.ADMINISTRATOR;

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
		String lang = ctx.getOptionAsString(optionNameOne);

		GuildSettingsEntity guildSettings = ctx.getGuildSettingsDb().setLanguage(lang);
		ctx.getDb().updateDb(guildSettings);

//		String key = "lang.options.language.choices." + Language.parseToLang(lang).get().getEnglishName().toLowerCase() + ".name";
//		ctx.reply(ctx.getI18n("langResponseMessage", ctx.getI18n(key)));

		// не актуальный объект i18n в контексте
		I18n i18n = new I18n(ctx.getGuild().getIdLong());
		String key = "lang.options.language.choices." + Language.parseToLang(lang).get().getEnglishName().toLowerCase() + ".name";
		ctx.reply(i18n.i18nFormat("langResponseMessage", i18n.i18n(key)));
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(
				new OptionData(OptionType.STRING, optionNameOne, optionDescriptionOne, true)
						.addChoices(getChoicesOption())
		);
	}

	@Override
	public List<Command.Choice> getChoicesOption() {
		List<Command.Choice> choices = new ArrayList<>();

		for (Language lang : Language.values()) {
			choices.add(new Command.Choice(lang.getEnglishName(), lang.getCode()));
		}

		return choices;
	}
}
