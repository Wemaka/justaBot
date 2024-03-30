package org.example.commandmeta;

import org.example.command.nsfw.Hentai;
import org.example.command.settings.Greeting;
import org.example.command.settings.HelpSettings;
import org.example.command.settings.Lang;
import org.example.commandmeta.util.Module;
import org.example.command.fun.*;
import org.example.command.info.*;
import org.example.command.moderation.*;

//@Component
public class CommandInit {
	public static void initCommands() {
		CommandRegistry adminModule = new CommandRegistry(Module.MODERATION);
		adminModule.addCommands(
				new Ban(),
				new Unban(),
				new TimeOut(),
				new RemoveTimeOut(),
				new Kick()
		);

		CommandRegistry infoModule = new CommandRegistry(Module.INFO);
		infoModule.addCommands(
				new Help()
		);

		CommandRegistry funModule = new CommandRegistry(Module.FUN);
		funModule.addCommands(
				new Who(),
				new Info(),
				new Choose(),
				new Should()
		);

		CommandRegistry nsfwModule = new CommandRegistry(Module.NSFW);
		nsfwModule.addCommands(
				new Hentai()
		);

		CommandRegistry settingsModule = new CommandRegistry(Module.SETTINGS);
		settingsModule.addCommands(
				new Lang(),
				new Greeting(),
				new HelpSettings()
		);
	}
}
