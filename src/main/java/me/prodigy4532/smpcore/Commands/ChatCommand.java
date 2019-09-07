package me.prodigy4532.smpcore.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import me.prodigy4532.smpcore.Chat.Channel;
import me.prodigy4532.smpcore.Chat.Message;
import me.prodigy4532.smpcore.SMP;
import me.prodigy4532.smpcore.Utils.ChatConstant;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand {
    static String buildMessageFromCommandArgs(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }

    @Command(
            aliases = {"admin", "a"},
            desc = "Sets player's chat to admin mode."
    )
    @CommandPermissions("smp.core.admin")
    public static void admin(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.argsLength() == 0) {
                if (SMP.getChannelRegistry.getPlayerChannel(player) != Channel.ADMIN) {
                    SMP.getChannelRegistry.setPlayerChannel(player, Channel.ADMIN);
                    player.sendMessage(ChatConstant.CHANNEL_SET_ADMIN.formatAsSuccess());
                } else {
                    player.sendMessage(ChatConstant.CHANNEL_ALREADY_ADMIN.formatAsException());
                }
            } else {
                String[] arguments = args.getOriginalArgs();
                Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments));
                Message msg = new Message(text, Channel.ADMIN, player);
                msg.send();
            }
        } else {
            sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
        }
    }

    @Command(
            aliases = {"global", "g"},
            desc = "Sets player's chat to global mode."
    )
    @CommandPermissions("smp.core.global")
    public static void global(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.argsLength() == 0) {
                if (SMP.getChannelRegistry.getPlayerChannel(player) != Channel.GLOBAL) {
                    SMP.getChannelRegistry.setPlayerChannel(player, Channel.GLOBAL);
                    player.sendMessage(ChatConstant.CHANNEL_SET_GLOBAL.formatAsSuccess());
                } else {
                    player.sendMessage(ChatConstant.CHANNEL_ALREADY_GLOBAL.formatAsException());
                }
            } else {
                String[] arguments = args.getOriginalArgs();
                Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments));
                Message msg = new Message(text, Channel.GLOBAL, player);
                msg.send();
            }
        } else {
            sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
        }
    }
}
