package me.prodigy4532.smpcore.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import me.prodigy4532.smpcore.Chat.Message;
import me.prodigy4532.smpcore.Chat.PrivateMessage;
import me.prodigy4532.smpcore.SMP;
import me.prodigy4532.smpcore.Utils.ChatConstant;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.prodigy4532.smpcore.Commands.ChatCommand.buildMessageFromCommandArgs;

public class PrivateMessageCommand {
    @Command(
            aliases = {"message", "msg", "pm"},
            desc = "Sends a private message.",
            usage = "[player] [msg]",
            min = 2
    )
    @CommandPermissions("smp.chat.message")
    public static void message(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Player receiver = Bukkit.getPlayer(args.getString(0));
            String[] arguments = args.getOriginalArgs();
            Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments, 2));
            if (receiver != null) {
                PrivateMessage privateMessage = new PrivateMessage(player, receiver, text);
                privateMessage.send();
                SMP.getReplyQueue.setReplicantPair(player, receiver);
            } else {
                player.sendMessage(ChatConstant.PLAYER_IS_OFFLINE.formatAsException());
            }
        } else {
            sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
        }
    }
}
