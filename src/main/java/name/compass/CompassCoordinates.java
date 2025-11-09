package name.compass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.util.TypedActionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompassCoordinates implements ClientModInitializer {
    public static final String MOD_ID = "compass-coordinates";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClient && hand == Hand.MAIN_HAND) {
                if (player.getStackInHand(hand).isOf(Items.COMPASS)) {
                    ItemStack stack = player.getStackInHand(hand);

                    if (stack.contains(DataComponentTypes.LODESTONE_TRACKER)) {
                        LodestoneTrackerComponent tracker = stack.get(DataComponentTypes.LODESTONE_TRACKER);
                        if(tracker != null && tracker.target().isPresent()) {
                            BlockPos pos = tracker.target().get().pos();
                            player.sendMessage(Text.literal("Lodestone at X: " + pos.getX() + " Z: " + pos.getZ()), true);
                            return TypedActionResult.success(stack);
                        }
                    }

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