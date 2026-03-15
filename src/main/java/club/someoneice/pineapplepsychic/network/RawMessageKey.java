package club.someoneice.pineapplepsychic.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public abstract class RawMessageKey<T extends RawMessageKey<?>> {
  abstract public IMessage onMessage(MessagePackage message, MessageContext ctx);

  abstract public void writeTo(ByteBuf buf);

  abstract public void readFrom(ByteBuf buf);
}
