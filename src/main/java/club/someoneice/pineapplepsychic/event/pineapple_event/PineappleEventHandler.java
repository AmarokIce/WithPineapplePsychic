package club.someoneice.pineapplepsychic.event.pineapple_event;

import club.someoneice.pineapplepsychic.event.pineapple_event.anno.PineappleEvent;
import tw.pearki.mcmod.muya.util.MethodHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class PineappleEventHandler {
    public static List<Object> eventList = new ArrayList<>();

    public PineappleEventHandler(Class<? extends IPineappleEvent> eventObj) {
        for (Object obj : eventList) {
            try {
                for (Method method : MethodHelper.GetAllMethod(obj.getClass())) {
                    PineappleEvent event = method.getAnnotation(PineappleEvent.class);
                    if (event != null && method.getParameterTypes().length >= 1 && method.getParameterTypes()[0].equals(eventObj)) {
                        method.setAccessible(true);
                        method.invoke(obj, eventObj);
                    }
                }
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }
    }

    public PineappleEventHandler(Object eventObj) {
        for (Object obj : eventList) {
            try {
                for (Method method : MethodHelper.GetAllMethod(obj.getClass())) {
                    PineappleEvent event = method.getAnnotation(PineappleEvent.class);
                    if (event != null && method.getParameterTypes().length >= 1 && method.getParameterTypes()[0].equals(eventObj.getClass())) {
                        method.setAccessible(true);
                        method.invoke(obj, eventObj);
                    }
                }
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }
    }
}
