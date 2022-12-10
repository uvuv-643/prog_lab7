package Input.Validation;

/**
 * Обёрточный класс для данных, которые прошли проверку валидатором
 * @param <T>
 * @see Validator
 */
public class ValidatedData<T> {

    /** Данные, которые прошли проверку валидатора */
    private T validatedData;

    /**
     * Конструктор объекта ValidatedData
     * @param validatedData - данные, которые прошли проверку валидатора
     */
    public ValidatedData(T validatedData) {
        this.validatedData = validatedData;
    }

    /**
     * getter для данных, которые прошли проверку валидатора
     * @return T - обобщённый тип данных, валидные данные
     */
    public T getValidatedData() {
        return validatedData;
    }

    /**
     * setter для данных, которые прошли проверку валидатора
     * @param validatedData - обобщённый тип данных, валидные данные
     */
    public void setValidatedData(T validatedData) {
        this.validatedData = validatedData;
    }
}
