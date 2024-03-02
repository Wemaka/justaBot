package org.example.repository;

import org.example.entity.GuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuildsRepository extends JpaRepository<GuildEntity, Long> {
	GuildEntity findByGuildId(Long guildId);
}
