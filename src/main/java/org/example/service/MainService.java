package org.example.service;

import net.dv8tion.jda.api.entities.Activity;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MainService {
//	private final GuildsService guildsService;
//	private final SettingsGuildService settingsGuildService;
//	private final GuildEventsHandler guildEventsHandler;
//
//	@Autowired
//	public MainService(GuildsService guildsService, SettingsGuildService settingsGuildService, GuildEventsHandler guildEventsHandler) {
//		this.guildsService = guildsService;
//		this.settingsGuildService = settingsGuildService;
//		this.guildEventsHandler = guildEventsHandler;
//	}

	private ShardManager shardManager;

	@Value("${bot.token}")
	private String TOKEN;

	public void startBot() {
		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(TOKEN);
		builder.setActivity(Activity.customStatus("Wants to be human..."))
				.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES)
				.setMemberCachePolicy(MemberCachePolicy.ALL)
				.setChunkingFilter(ChunkingFilter.ALL);
//		builder.enableCache(CacheFlag.)
		shardManager = builder.build();


//		guildsService.initGuilds();
//		CommandInit.initCommands();

//		shardManager.addEventListener(new CommandRegistry());
//		shardManager.addEventListener(guildEventsHandler);
	}

	public ShardManager getShardManager() {
		return shardManager;
	}

	public void registerListeners(Object... listeners) {
		shardManager.addEventListener(listeners);
	}
}
