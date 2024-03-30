package org.example.repository;

import org.example.entity.GuildSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsGuildRepository extends JpaRepository<GuildSettingsEntity, Long> {
}
