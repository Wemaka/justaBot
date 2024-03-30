package org.example.service;

import org.example.entity.GuildEntity;
import org.example.entity.GuildSettingsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotDb {
	private static GuildsService guildsService;
	private static GuildSettingsService guildSettingsService;

	@Autowired
	public BotDb(GuildsService guildsService, GuildSettingsService guildSettingsService) {
		BotDb.guildsService = guildsService;
		BotDb.guildSettingsService = guildSettingsService;
	}

	public static BotDb getDb() {
		return new BotDb(guildsService, guildSettingsService);
	}

	public GuildsService getGuildsService() {
		return guildsService;
	}

	public GuildSettingsService getGuildSettingsService() {
		return guildSettingsService;
	}

	public GuildEntity getGuildById(long id) {
		return guildsService.findByGuildId(id);
	}

	public GuildSettingsEntity getGuildSettingsById(long id) {
		return getGuildById(id).getGuildSettings();
	}


	public void updateDb(GuildSettingsEntity guildSettingsEntity) {
		guildSettingsService.update(guildSettingsEntity);
	}

	public void updateDb(GuildEntity GuildEntity) {
		guildsService.update(GuildEntity);
	}
}
