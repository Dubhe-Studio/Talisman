public class T2 {
    private final Class<?>[] values;

    public T2(Class<?>... values) {
        this.values = values;
    }

    public void isInvalid(A a) {
        for (Class<?> value : values) {
            System.out.println(value.isAssignableFrom(a.getClass()));
        }
    }
}
