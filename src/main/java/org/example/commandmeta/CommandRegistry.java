package org.example.commandmeta;


import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.commandmeta.util.Module;
import org.example.i18n.I18n;
import org.springframework.stereotype.Component;

import java.util.*;

public class CommandRegistry {
	private Module module;
	private static Map<Module, CommandRegistry> modules = new LinkedHashMap<>();
	private Map<String, SlashCommand> commands = new LinkedHashMap<>();

	public CommandRegistry() {
	}

	public CommandRegistry(Module module) {
		this.module = module;

		modules.put(module, this);
	}

	public Module getModule() {
		return module;
	}

	public static CommandRegistry getCommandModule(Module module) {
		if (!modules.containsKey(module)) {
			throw new IllegalStateException("No such module registered: " + module.name());
		}

		return modules.get(module);
	}

	public static SlashCommand getCommand(String command) {
		for (CommandRegistry module : getValuesModule()) {
			if (module.commands.containsKey(command)) {
				return module.commands.get(command);
			}
		}
		return null;
	}

	public static List<Module> getKeysModule() {
		return new ArrayList<>(modules.keySet());
	}

	public static List<CommandRegistry> getValuesModule() {
		return new ArrayList<>(modules.values());
	}

	public List<SlashCommand> getCommands() {
		return new ArrayList<>(commands.values());
	}

	public Map<String, SlashCommand> getMapCommands() {
		return commands;
	}

	public void addCommands(SlashCommand... newCommands) {
		for (SlashCommand command : newCommands) {
			commands.put(command.getName(), command);
		}
	}

	public void registerCommands(GenericGuildEvent event) {
		for (CommandRegistry module : getValuesModule()) {
			for (SlashCommand command : module.getCommands()) {
				event.getGuild().upsertCommand(command.getCommandData()).queue();
			}
		}
	}

	//	@Override
//	public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
//		if (event.getName().equals("help")) {
//			System.out.println("penis");
//		}
//
//		for (CommandRegistry module : modules.values()) {
//			if (module.commands.containsKey(event.getName())) {
//				module.commands.get(event.getName()).autocomplete(event);
//				break;
//			}
//		}
//	}
}
