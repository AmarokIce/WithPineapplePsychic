package club.someoneice.pineapplepsychic.api.config;

import club.someoneice.pineapplepsychic.core.PPcMain;
import club.someoneice.pineapplepsychic.api.anno.Config;
import club.someoneice.pineapplepsychic.common.lazy.Lazy;
import club.someoneice.pineapplepsychic.feature.FieldCodec;
import club.someoneice.pineapplepsychic.feature.ObjectLambda;
import club.someoneice.pineapplepsychic.feature.function.FieldHandler;
import club.someoneice.pineapplepsychic.feature.function.ILazy;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public abstract class AutoConfig<T> implements IConfig<T> {
  /**
   * The way you handle things might be different depending on the document's language. But once you
   * start processing, the processor should refuse to handle any new variable structures.
   */
  protected ILazy<ImmutableMap<String, FieldCodec<IConfig<T>, T>>> fieldHandler;

  public AutoConfig(@Nonnull Supplier<ImmutableMap<String, FieldCodec<IConfig<T>, T>>> configHandler) {
    fieldHandler = new Lazy<>(configHandler);
  }

  private void readField(Field field, T root, Config tag) {
    final String name = tag.value().isEmpty() ? field.getName() : tag.value();
    try {
      final String signName = name.isEmpty() ? field.getName() : name;
      final Object obj = field.get(this);
      final String typeName = obj.getClass().getSimpleName();

      @CheckForNull
      final FieldHandler<IConfig<T>, T> handler =
          ObjectLambda.objectLet(fieldHandler.get().get(typeName), FieldCodec::getDecoder);

      if (Objects.isNull(handler) || !handler.handle(field, this, root, signName)) {
        PPcMain.LOGGER.error(
            "Can't load root data from config! value: {}, type: {}", signName, typeName);
      }
    } catch (final IllegalAccessException e) {
      PPcMain.LOGGER.error("Can't load root data from tile entity!", e);
    }
  }

  private void writeField(Field field, T root, Config tag) {
    final String name = tag.value().isEmpty() ? field.getName() : tag.value();
    try {
      final String signName = name.isEmpty() ? field.getName() : name;
      final Object obj = field.get(this);
      final String typeName = obj.getClass().getSimpleName();

      @CheckForNull
      final FieldHandler<IConfig<T>, T> handler =
          ObjectLambda.objectLet(fieldHandler.get().get(typeName), FieldCodec::getEncoder);

      if (Objects.isNull(handler) || !handler.handle(field, this, root, signName)) {
        PPcMain.LOGGER.error(
            "Can't save root data from config! value: {}, type: {}", signName, typeName);
      }
    } catch (final IllegalAccessException e) {
      PPcMain.LOGGER.error("Can't load root data from tile entity!", e);
    }
  }

  @Override
  public T save(T root) {
    Arrays.stream(this.getClass().getDeclaredFields())
        .filter(it -> it.isAnnotationPresent(Config.class))
        .peek(it -> it.setAccessible(true))
        .forEach(it -> readField(it, root, it.getDeclaredAnnotation(Config.class)));

    return root;
  }

  @Override
  public void load(T root) {
    Arrays.stream(this.getClass().getDeclaredFields())
        .filter(it -> it.isAnnotationPresent(Config.class))
        .peek(it -> it.setAccessible(true))
        .forEach(it -> writeField(it, root, it.getDeclaredAnnotation(Config.class)));
  }
}
