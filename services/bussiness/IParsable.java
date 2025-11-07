package services.bussiness;

public abstract interface IParsable<T> {
    public abstract T parse(String line);
}
