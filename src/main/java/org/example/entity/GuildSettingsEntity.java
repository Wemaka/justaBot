package org.example.entity;

import jakarta.persistence.*;
//import org.checkerframework.checker.units.qual.C;

@Entity
@Table(name = "guild_settings")
public class GuildSettingsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne(mappedBy = "guildSettings")
	private GuildEntity guild;

	@Column(name = "language", nullable = false, length = 20)
	private String language;

	@Column(name = "greeting_channel_id")
	private Long greetingChannelId;

	@Column(name = "greeting_message", nullable = false)
	private String greetingMessage;

	@Column(name = "greeting_disable", nullable = false)
	private boolean greetingDisable;

	@Column(name = "sending_help", nullable = false)
	private String sendingHelp;

	public GuildSettingsEntity() {
	}

	public GuildSettingsEntity(String language, Long greetingChannelId, String greetingMessage, boolean greetingDisable, String sendingHelp) {
		this.language = language;
		this.greetingChannelId = greetingChannelId;
		this.greetingMessage = greetingMessage;
		this.greetingDisable = greetingDisable;
		this.sendingHelp = sendingHelp;
	}

	public GuildEntity getGuild() {
		return guild;
	}

	public String getLanguage() {
		return language;
	}

	public Long getGreetingChannelId() {
		return greetingChannelId;
	}

	public String getGreetingMessage() {
		return greetingMessage;
	}

	public boolean getGreetingDisable() {
		return greetingDisable;
	}

	public String getSendingHelp() {
		return sendingHelp;
	}

	public GuildSettingsEntity setGuild(GuildEntity guild) {
		this.guild = guild;
		return this;
	}

	public GuildSettingsEntity setLanguage(String language) {
		this.language = language;
		return this;
	}

	public GuildSettingsEntity setGreetingChannelId(Long greetingChannelId) {
		this.greetingChannelId = greetingChannelId;
		return this;
	}

	public GuildSettingsEntity setGreetingMessage(String greetingMessage) {
		this.greetingMessage = greetingMessage;
		return this;
	}

	public GuildSettingsEntity setGreetingDisable(boolean greetingDisable) {
		this.greetingDisable = greetingDisable;
		return this;
	}

	public GuildSettingsEntity setSendingHelp(String sendingHelp) {
		this.sendingHelp = sendingHelp;
		return this;
	}
}
