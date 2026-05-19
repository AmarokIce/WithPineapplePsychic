package club.someoneice.pineapplepsychic.lib.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerEatenEvent extends PlayerEvent {
  public final ItemStack food;
  public final ItemStack result;

  public PlayerEatenEvent(EntityPlayer player, ItemStack food, ItemStack result) {
    super(player);
    this.food = food;
    this.result = result;
  }
}
