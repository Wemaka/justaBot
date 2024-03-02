package org.example;

import org.example.commandmeta.CommandInit;
import org.example.event.GuildEventsHandler;
import org.example.event.SlashEventsHandler;
import org.example.service.GuildsService;
import org.example.service.MainService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApplicationBot implements CommandLineRunner {
	private final MainService mainService;
	private final GuildsService guildsService;
//	private final SettingsGuildService settingsGuildService;
	//	private final GuildEventsHandler guildEventsHandler;
//	private final CommandInit commandInit;


	public static void main(String[] args) {
		SpringApplication.run(ApplicationBot.class, args);
	}

	public ApplicationBot(MainService mainService, GuildsService guildsService) {
		this.mainService = mainService;
		this.guildsService = guildsService;
//		this.settingsGuildService = settingsGuildService;
//		this.guildEventsHandler = guildEventsHandler;
//		this.commandInit = commandInit;
	}


	@Override
	public void run(String... args) throws Exception {
		mainService.startBot();

		guildsService.initGuilds();
		CommandInit.initCommands();

		mainService.registerListeners(
				new SlashEventsHandler(),
				new GuildEventsHandler()
		);
	}
}
