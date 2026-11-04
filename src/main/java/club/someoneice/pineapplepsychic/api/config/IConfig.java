package club.someoneice.pineapplepsychic.api.config;

public interface IConfig<T> {
  void load(final T root);
  T save(final T root);
}
