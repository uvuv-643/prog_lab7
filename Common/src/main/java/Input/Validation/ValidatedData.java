package Input.Validation;

public class ValidatedData<T> {
    private T validatedData;

    public ValidatedData(T validatedData) {
        this.validatedData = validatedData;
    }

    public T getValidatedData() {
        return validatedData;
    }

    public void setValidatedData(T validatedData) {
        this.validatedData = validatedData;
    }
}
