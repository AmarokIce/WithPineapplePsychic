package club.someoneice.pineapplepsychic.lib.event;

import cpw.mods.fml.common.eventhandler.Event;

public class TimeEvent extends Event {
  public enum Type {
    PLAYER,
    WORLD,
    SERVER,
    CLIENT
  }

  public final Type type;

  private TimeEvent(Type type) {
    this.type = type;
  }

  public static class SecondsEvent extends TimeEvent {
    public SecondsEvent(Type type) {
      super(type);
    }
  }

  public static class MinutesEvent extends TimeEvent {
    public MinutesEvent(Type type) {
      super(type);
    }
  }
}
