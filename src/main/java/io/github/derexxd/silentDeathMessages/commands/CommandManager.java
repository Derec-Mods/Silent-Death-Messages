package io.github.derexxd.silentDeathMessages.commands;

import io.github.derexxd.silentDeathMessages.SilentDeathMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("silentdeathmessages.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /sdm add <player>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Please specify a player to add.");
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found or is not currently online.");
                    return true;
                }

                String uuid = target.getUniqueId().toString();

                if (SilentDeathMessages.getInstance().getStorage().getIgnoredPlayers().contains(uuid)) {
                    sender.sendMessage(ChatColor.YELLOW + target.getName() + " is already on the ignore list.");
                    return true;
                }

                SilentDeathMessages.getInstance().getStorage().addPlayer(uuid);

                sender.sendMessage(ChatColor.GREEN + "Added " + target.getName() + " to the silent death messages list.");
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown command. Try /sdm add <player>");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if ("add".startsWith(args[0].toLowerCase())) {
                completions.add("add");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(p.getName());
                }
            }
        }

        return completions;
    }
}