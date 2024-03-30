package org.example.service;

import org.example.entity.GuildEntity;
import org.example.repository.GuildsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GuildsService {
	private final GuildsRepository guildsRepository;
	private static Map<Long, GuildEntity> guilds = new HashMap<>();

	@Autowired
	public GuildsService(GuildsRepository guildsRepository) {
		this.guildsRepository = guildsRepository;
	}

	public void initGuilds() {
		for (GuildEntity guildEntity : guildsRepository.findAll()) {
			guilds.put(guildEntity.getGuildId(), guildEntity);
		}
	}

	public Map<Long, GuildEntity> getGuilds() {
		return guilds;
	}

	public void save(GuildEntity guildEntity) {
		guildsRepository.save(guildEntity);
		guilds.put(guildEntity.getGuildId(), guildEntity);
	}

	public void update(GuildEntity guildEntity) {
		guildsRepository.save(guildEntity);
		guilds.replace(guildEntity.getGuildId(), guildEntity);
	}

	public void delete(Long guildId) {
		guildsRepository.delete(findByGuildId(guildId));
		guilds.remove(guildId);
	}

	public GuildEntity findByGuildId(Long guildId) {
//		return guildsRepository.findByGuildId(guildId);
		return guilds.get(guildId);
	}

	public boolean existsGuild(Long guildId) {
//		return guildsRepository.findByGuildId(guildId) != null;
		return guilds.containsKey(guildId);
	}
}
