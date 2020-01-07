package me.prodigy4532.smpcore.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import me.prodigy4532.smpcore.Chat.Channel;
import me.prodigy4532.smpcore.Chat.Message;
import me.prodigy4532.smpcore.Utils.ChatConstant;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand {
    @Command(
            aliases = {"broadcast", "br"},
            desc = "Broadcasts a message to all players.",
            usage = "[msg]",
            min = 1
    )
    @CommandPermissions("smp.core.broadcast")
    public static void broadcast(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            String[] arguments = args.getOriginalArgs();

            Message.Text text = new Message.Text(arguments, 1);
            Message msg = new Message(text, Channel.GLOBAL, player);

            msg.broadcast();
        } else {
            sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
        }
    }
}
