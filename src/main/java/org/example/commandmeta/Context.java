package org.example.commandmeta;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.example.entity.GuildSettingsEntity;
import org.example.i18n.I18n;
import org.example.service.BotDb;
import net.dv8tion.jda.api.exceptions.ErrorHandler;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public abstract class Context<T extends GenericCommandInteractionEvent> {
	protected static final BotDb db = BotDb.getDb();

	protected final T event;
	protected final I18n i18n;
	private boolean ephemeral = false;
	private boolean defered = false;

	public Context(T event) {
		this.event = event;
//		this.i18n = i18n;
		this.i18n = new I18n(event.getGuild().getIdLong());
	}

	public T getEvent() {
		return event;
	}

	public JDA getJDA() {
		return event.getJDA();
	}

	public Context<T> ephemeral() {
		ephemeral = true;
		return this;
	}

	public void defer() {
		if (ephemeral) {
			event.deferReply().setEphemeral(true).queue();
		} else {
			event.deferReply().queue();
		}

		defered = true;
	}

	public User getUser() {
		return event.getUser();
	}

	public String getUserAvatarUrl() {
		return getUser().getAvatarUrl();
	}

	public User getSelfUser() {
		return event.getJDA().getSelfUser();
	}

	public String getSelfUserAvatarUrl() {
		return getSelfUser().getAvatarUrl();
	}

	public Guild getGuild() {
		return event.getGuild();
	}

	public Member getMember() {
		return event.getMember();
	}

	public String getMemberEffectiveName() {
		return getMember().getEffectiveName();
	}

	public List<Member> getMembers() {
		return getGuild().getMembers();
	}

//	public ReplyCallbackAction getReply(String msg) {
//		return event.reply(msg);
//	}
//
//	public ReplyCallbackAction getReply(MessageEmbed embed) {
//		return event.replyEmbeds(embed);
//	}

	public void reply(String msg) {
		if (defered) {
			event.getHook().sendMessage(msg).queue();
		} else {
			event.reply(msg).setEphemeral(ephemeral).queue();
		}

		if (ephemeral) {
			ephemeral = false;
		}
	}

	public void reply(MessageEmbed embed) {
		if (defered) {
			event.getHook().sendMessageEmbeds(embed).queue();
		} else {
			event.replyEmbeds(embed).setEphemeral(ephemeral).queue();
		}

		if (ephemeral) {
			ephemeral = false;
		}
	}

	public void sendPrivateMessage(long id, String msg) {
//		member.getUser().openPrivateChannel().queue(
//				(channel) -> channel.sendMessage(msg).complete()
//		);

		getJDA().retrieveUserById(id).queue(
				(user) -> user.openPrivateChannel().queue(
						(channel) -> channel.sendMessage(msg).queue(null, new ErrorHandler()
								.ignore(ErrorResponse.CANNOT_SEND_TO_USER)),
						(failure) -> {
						}
				),
				new ErrorHandler().ignore(ErrorResponse.UNKNOWN_USER)
		);

//		member.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessage(msg)).complete();
	}

	public void sendPrivateMessage(Member member, MessageEmbed embed) {
		member.getUser().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(
				embed
		)).complete();
	}

	public I18n getI18n() {
		return i18n;
	}

	public String getI18n(String key) {
		return i18n.i18n(key);
	}

	public String getI18n(String key, String... params) {
		return i18n.i18nFormat(key, params);
	}

	public EmbedBuilder getEmbed() {
		return new EmbedBuilder();
	}

	public EmbedBuilder getBaseEmbed() {
		return getEmbed().setColor(Color.WHITE);
	}

	public boolean hasPermission(Permission permission) {
		return Objects.requireNonNull(event.getMember()).hasPermission(permission);
	}

	public OptionMapping getOption(String name) {
		return event.getOption(name);
	}

	public User getOptionAsUser(String name, User dflt) {
		OptionMapping option = getOption(name);
		if (option == null) {
			return dflt;
		}

		return option.getAsUser();
	}

	public User getOptionAsUser(String name) {
		return getOptionAsUser(name, null);
	}

	public String getOptionAsString(String name, String dflt) {
		OptionMapping option = getOption(name);
		if (option == null) {
			return dflt;
		}

		return option.getAsString();
	}

	public String getOptionAsString(String name) {
		return getOptionAsString(name, null);
	}

	public Member getOptionAsMember(String name, Member dflt) {
		OptionMapping option = getOption(name);
		if (option == null || option.getAsMember() == null) {
			return dflt;
		}

		return option.getAsMember();
	}

	public Member getOptionAsMember(String name) {
		return getOptionAsMember(name, null);
	}

	public int getOptionAsInt(String name, int dflt) {
		OptionMapping option = getOption(name);
		if (option == null) {
			return dflt;
		}

		return option.getAsInt();
	}

	public int getOptionAsInt(String name) {
		return getOptionAsInt(name, 0);
	}

	public boolean getOptionAsBoolean(String name, boolean dflt) {
		OptionMapping option = getOption(name);
		if (option == null) {
			return dflt;
		}

		return option.getAsBoolean();
	}

	public boolean getOptionAsBoolean(String name) {
		return getOptionAsBoolean(name, false);
	}

	public GuildChannelUnion getOptionAsChannel(String name, GuildChannelUnion dflt) {
		OptionMapping option = getOption(name);
		if (option == null) {
			return dflt;
		}

		return option.getAsChannel();
	}


	public GuildChannelUnion getOptionAsChannel(String name) {
		return getOptionAsChannel(name, null);
	}

	public User getUserById(Long id, User dflt) {
		User user = getJDA().getUserById(id);
		if (user == null) {
			return dflt;
		}

		return user;
	}

	public User getUserById(String id, User dflt) {
		if (id.contains("@")) {
			id = id.substring(id.indexOf("@") + 1, id.length() - 1);
		}

		Long userId;
		try {
			userId = Long.parseLong(id);
		} catch (Exception ignore) {
			return null;
		}

		return getUserById(userId, dflt);
	}

	public User getUserById(Long id) {
		return getUserById(id, null);
	}

	public User getUserById(String id) {
		return getUserById(id, null);
	}

	public BotDb getDb() {
		return db;
	}

	public GuildSettingsEntity getGuildSettingsDb() {
		return db.getGuildSettingsById(getGuild().getIdLong());
	}
}
