package org.example.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.commandmeta.CommandRegistry;
import org.example.entity.GuildEntity;
import org.example.entity.GuildSettingsEntity;
import org.example.service.BotDb;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class GuildEventsHandler extends ListenerAdapter {
	private static final BotDb db = BotDb.getDb();

	private void saveGuild(GenericGuildEvent event) {
		Guild guild = event.getGuild();

		if (!db.getGuildsService().existsGuild(guild.getIdLong())) {
			String locale = guild.getLocale().getLocale();

			if (locale.contains("-")) {
				locale = String.join("_", locale.split("-"));
			}

			GuildSettingsEntity settingsGuild = new GuildSettingsEntity();

			DefaultGuildChannelUnion channel = event.getGuild().getDefaultChannel();
			if (channel != null) {
				settingsGuild.setGreetingChannelId(channel.getIdLong());
			}

			settingsGuild
					.setLanguage(locale)
					.setGreetingMessage("Hi, {member}!")
					.setGreetingDisable(false)
					.setSendingHelp("unhidden");

			db.getGuildsService().save(new GuildEntity(guild.getIdLong(), guild.getName(), settingsGuild));
		}
	}

	@Override
	public void onGuildReady(@NotNull GuildReadyEvent event) {
		saveGuild(event);
	}

	@Override
	public void onGuildJoin(@NotNull GuildJoinEvent event) {
		DefaultGuildChannelUnion channel = event.getGuild().getDefaultChannel();
		if (channel != null) {
			channel.asTextChannel().sendMessage("Hi. I'm \"Just A Bot\"\nUse **/help** to see what I can do.").queue();
		}
		saveGuild(event);
	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		CommandRegistry commandRegistry = new CommandRegistry();
		commandRegistry.registerGlobalCommands(event);
	}

	@Override
	public void onGuildLeave(@NotNull GuildLeaveEvent event) {
		db.getGuildsService().delete(event.getGuild().getIdLong());
	}

	@Override
	public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
		GuildSettingsEntity guildSettings = db.getGuildsService().getGuilds().get(event.getGuild().getIdLong()).getGuildSettings();

		if (guildSettings.getGreetingDisable()) {
			return;
		}

		String textForm = guildSettings.getGreetingMessage().replaceAll("\\{member\\}", "{0}");
		String greetingMessage = MessageFormat.format(textForm, event.getMember().getAsMention());
		Long textChannelId = guildSettings.getGreetingChannelId();

		if (textChannelId != null) {
			event.getGuild().getTextChannelById(textChannelId).sendMessage(greetingMessage).queue();
		} else {
			DefaultGuildChannelUnion channel = event.getGuild().getDefaultChannel();

			if (channel != null) {
				channel.asTextChannel().sendMessage(greetingMessage).queue();
			}
		}
	}
}
