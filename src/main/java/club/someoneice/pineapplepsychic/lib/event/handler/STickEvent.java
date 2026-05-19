package club.someoneice.pineapplepsychic.lib.event.handler;

import club.someoneice.pineapplepsychic.lib.event.TimeEvent;
import com.google.common.collect.EnumMultiset;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;

public class STickEvent {
  private final EnumMultiset<TimeEvent.Type> seconds = EnumMultiset.create(TimeEvent.Type.class);
  private final EnumMultiset<TimeEvent.Type> minutes = EnumMultiset.create(TimeEvent.Type.class);

  @SubscribeEvent
  public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.START) {
      return;
    }

    seconds.add(TimeEvent.Type.PLAYER);
    minutes.add(TimeEvent.Type.PLAYER);

    if (seconds.count(TimeEvent.Type.PLAYER) >= 20) {
      MinecraftForge.EVENT_BUS.post(new TimeEvent.SecondsEvent(TimeEvent.Type.PLAYER));
      seconds.setCount(TimeEvent.Type.PLAYER, 0);
    }

    if (minutes.count(TimeEvent.Type.PLAYER) >= 1200) {
      MinecraftForge.EVENT_BUS.post(new TimeEvent.MinutesEvent(TimeEvent.Type.PLAYER));
      minutes.setCount(TimeEvent.Type.PLAYER, 0);
    }
  }

  @SubscribeEvent
  public void onWorldTick(final TickEvent.WorldTickEvent event) {
    if (event.phase == TickEvent.Phase.START) {
      return;
    }

    seconds.add(TimeEvent.Type.WORLD);
    minutes.add(TimeEvent.Type.WORLD);

    if (seconds.count(TimeEvent.Type.WORLD) >= 20) {
      MinecraftForge.EVENT_BUS.post(new TimeEvent.SecondsEvent(TimeEvent.Type.WORLD));
      seconds.setCount(TimeEvent.Type.WORLD, 0);
    }

    if (minutes.count(TimeEvent.Type.WORLD) >= 1200) {
      MinecraftForge.EVENT_BUS.post(new TimeEvent.MinutesEvent(TimeEvent.Type.WORLD));
      minutes.setCount(TimeEvent.Type.WORLD, 0);
    }
  }

  @SubscribeEvent
  public void onServerTick(final TickEvent.ServerTickEvent event) {
    if (event.phase == TickEvent.Phase.START) {
      return;
    }

    seconds.add(TimeEvent.Type.SERVER);
    minutes.add(TimeEvent.Type.SERVER);

    if (seconds.count(TimeEvent.Type.SERVER) >= 20) {
      MinecraftForge.EVENT_BUS.post(new TimeEvent.SecondsEvent(TimeEvent.Type.SERVER));
      seconds.setCount(TimeEvent.Type.SERVER, 0);
    }

    if (minutes.count(TimeEvent.Type.SERVER) >= 1200) {
      MinecraftForge.EVENT_BUS.post(new TimeEvent.SecondsEvent(TimeEvent.Type.SERVER));
      minutes.setCount(TimeEvent.Type.SERVER, 0);
    }
  }

  @SubscribeEvent
  public void onClientTick(final TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.START) {
      return;
    }

    seconds.add(TimeEvent.Type.CLIENT);
    minutes.add(TimeEvent.Type.CLIENT);

    if (seconds.count(TimeEvent.Type.CLIENT) >= 20) {
      MinecraftForge.EVENT_BUS.post(new TimeEvent.SecondsEvent(TimeEvent.Type.CLIENT));
      seconds.setCount(TimeEvent.Type.CLIENT, 0);
    }

    if (minutes.count(TimeEvent.Type.CLIENT) >= 1200) {
      MinecraftForge.EVENT_BUS.post(new TimeEvent.SecondsEvent(TimeEvent.Type.CLIENT));
      minutes.setCount(TimeEvent.Type.CLIENT, 0);
    }
  }
}
