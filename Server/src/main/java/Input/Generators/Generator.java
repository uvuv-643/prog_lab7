package Input.Generators;

/**
 * Интерфейс для генерации значений
 * @param <T>
 * @author uvuv-643
 * @version 1.0
 */
public interface Generator<T> {
    /**
     * @return T - сгенерированное значение
     */
    T generate();
}
