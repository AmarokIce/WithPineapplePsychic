package club.someoneice.pineapplepsychic.network;

import club.someoneice.pineapplepsychic.PineappleMain;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessagePackage implements IMessage {
  RawMessageKey<?> messageKey;

  public MessagePackage(RawMessageKey<?> messagePackage) {
    this.messageKey = messagePackage;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    if (!NetworkHandler.MESSAGES.contains(this.messageKey.getClass())) {
      PineappleMain.LOGGER.error("An unregister message package has not been received!");
      return;
    }

    int id = NetworkHandler.MESSAGES.indexOf(this.messageKey.getClass());
    buf.writeInt(id);
    this.messageKey.writeTo(buf);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    int id = buf.readInt();
    if (id < 0 || id > NetworkHandler.MESSAGES.size()) return;
    Class<? extends RawMessageKey<?>> clazz = NetworkHandler.MESSAGES.get(id);
    try {
      this.messageKey = clazz.newInstance();
      this.messageKey.readFrom(buf);
    } catch (InstantiationException | IllegalAccessException e) {
      PineappleMain.LOGGER.error(e);
    }
  }
}
