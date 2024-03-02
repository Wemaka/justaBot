package org.example.service;

import org.example.entity.GuildSettingsEntity;
import org.example.repository.SettingsGuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildSettingsService {
	private final SettingsGuildRepository settingsGuildRepository;

	@Autowired
	public GuildSettingsService(SettingsGuildRepository settingsGuildRepository) {
		this.settingsGuildRepository = settingsGuildRepository;
	}


	public void save(GuildSettingsEntity guildSettingsEntity) {
		settingsGuildRepository.save(guildSettingsEntity);
	}

	public void update(GuildSettingsEntity guildSettingsEntity) {
		settingsGuildRepository.save(guildSettingsEntity);
	}
}
