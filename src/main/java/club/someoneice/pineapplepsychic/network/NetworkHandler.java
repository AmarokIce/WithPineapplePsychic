package club.someoneice.pineapplepsychic.network;

import club.someoneice.pineapplepsychic.PineappleMain;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

import java.util.List;

public final class NetworkHandler {
  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(PineappleMain.MODID);

  static final List<Class<? extends RawMessageKey<?>>> MESSAGES = Lists.newArrayList();

  public static void registerPackage(Class<? extends RawMessageKey<?>> clazz) {
    MESSAGES.add(clazz);
  }

  public static void init() {
    INSTANCE.registerMessage(Handler.class, MessagePackage.class, 0, Side.CLIENT);
    INSTANCE.registerMessage(Handler.class, MessagePackage.class, 0, Side.SERVER);
  }

  public static final class Handler implements IMessageHandler<MessagePackage, IMessage> {
    @Override
    public IMessage onMessage(MessagePackage message, final MessageContext ctx) {
      return message.messageKey.onMessage(message, ctx);
    }
  }
}
