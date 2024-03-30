package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "guilds")
public class GuildEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "guild_id", unique = true, nullable = false)
	private Long guildId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "guild_settings_id", referencedColumnName = "id")
	private GuildSettingsEntity guildSettings;

	public GuildEntity() {
	}

	public GuildEntity(Long guildId, String name, GuildSettingsEntity settingsGuild) {
		this.guildId = guildId;
		this.name = name;
		this.guildSettings = settingsGuild;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getGuildId() {
		return guildId;
	}

	public GuildSettingsEntity getGuildSettings() {
		return guildSettings;
	}

	public GuildEntity setName(String name) {
		this.name = name;
		return this;
	}

	public GuildEntity setGuildId(Long guildId) {
		this.guildId = guildId;
		return this;
	}

	public GuildEntity setGuildSettings(GuildSettingsEntity guildSettings) {
		this.guildSettings = guildSettings;
		return this;
	}
}
