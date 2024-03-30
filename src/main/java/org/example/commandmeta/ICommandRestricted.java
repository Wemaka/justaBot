package org.example.commandmeta;

import net.dv8tion.jda.api.Permission;

public interface ICommandRestricted {
	Permission getPermission();
}
