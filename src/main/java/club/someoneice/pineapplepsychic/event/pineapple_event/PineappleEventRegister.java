package club.someoneice.pineapplepsychic.event.pineapple_event;


@Deprecated
public class PineappleEventRegister {
    public static void registryEvent(Object obj) {
        PineappleEventHandler.eventList.add(obj);
    }
}
