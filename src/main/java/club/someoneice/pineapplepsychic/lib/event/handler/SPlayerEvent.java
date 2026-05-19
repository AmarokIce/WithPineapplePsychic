package club.someoneice.pineapplepsychic.lib.event.handler;

import club.someoneice.pineapplepsychic.lib.event.PlayerEatenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class SPlayerEvent {
  @SubscribeEvent
  public void onPlayerFinishUsing(final PlayerUseItemEvent.Finish event) {
    if (!(event.item.getItem() instanceof ItemFood)) {
      return;
    }

    final PlayerEatenEvent eve = new PlayerEatenEvent(event.entityPlayer, event.item, event.result);
    MinecraftForge.EVENT_BUS.post(eve);
  }
}
