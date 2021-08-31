package fr.realcraft.host.commands;

import fr.realcraft.host.Host;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HostCommand implements CommandExecutor {

    private final Host host;

    public HostCommand(Host host) {
        this.host = host;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
