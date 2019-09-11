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

import java.util.UUID;

import static me.prodigy4532.smpcore.Commands.ChatCommand.buildMessageFromCommandArgs;

public class ReplyCommand {
    @Command(
            aliases = {"reply", "r"},
            desc = "Replies to your last message receiver",
            usage = "[player] [msg]"
    )
    @CommandPermissions("smp.chat.reply")
    public static void reply(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String receiverUUID = SMP.getReplyQueue.getPlayerReplier(player);
            if (SMP.getReplyQueue.getPlayerReplier(player) != null) {
                Player receiver = Bukkit.getPlayer(UUID.fromString(receiverUUID));
                assert receiver != null;
                if (receiver.isOnline()) {
                    String[] arguments = args.getOriginalArgs();
                    Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments, 1));
                    PrivateMessage pm = new PrivateMessage(player, receiver.getPlayer(), text);
                    pm.send();
                } else {
                    SMP.getReplyQueue.removePair(player);
                    sender.sendMessage(ChatConstant.PLAYER_IS_OFFLINE.formatAsException());
                }
            } else {
                sender.sendMessage(ChatConstant.NOBODY_TO_REPLY_TO.formatAsException());
            }
        } else {
            sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
        }
    }
}
