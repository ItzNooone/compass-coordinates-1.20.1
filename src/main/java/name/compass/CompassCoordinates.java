package name.compass;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.util.TypedActionResult;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompassCoordinates implements ModInitializer {
    public static final String MOD_ID = "compass-coordinates";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!world.isClient && hand == Hand.MAIN_HAND) {
                if (player.getStackInHand(hand).isOf(Items.COMPASS)) {
                    int x = (int) player.getX();
                    int z = (int) player.getZ();
                    player.sendMessage(Text.literal("X: " + x + " Z: " + z), true);
                    return TypedActionResult.success(player.getStackInHand(hand));
                }
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });


    }
}