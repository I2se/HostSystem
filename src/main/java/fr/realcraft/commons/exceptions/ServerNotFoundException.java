package fr.realcraft.commons.exceptions;

import org.bukkit.entity.Player;

public class ServerNotFoundException extends Exception{

    public ServerNotFoundException(Player player) {
        super("Le serveur n'a pas était trouvé");
    }
}
