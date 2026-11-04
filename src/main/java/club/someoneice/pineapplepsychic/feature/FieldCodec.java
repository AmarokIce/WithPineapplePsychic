package club.someoneice.pineapplepsychic.feature;

import club.someoneice.pineapplepsychic.feature.function.FieldHandler;

public class FieldCodec<C, R> {
  private final FieldHandler<C, R> encoder;
  private final FieldHandler<C, R> decoder;

  /**
   * @param encoder The codec make data from class to root.
   * @param decoder The codec make data from root to class.
   */
  public FieldCodec(final FieldHandler<C, R> encoder, final FieldHandler<C, R> decoder) {
    this.encoder = encoder;
    this.decoder = decoder;
  }

  public FieldHandler<C, R> getDecoder() {
    return decoder;
  }

  public FieldHandler<C, R> getEncoder() {
    return encoder;
  }
}
