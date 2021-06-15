public class Test {
    public static void main(String[] args) {
        T2 t2 = new T2(B.class);
        t2.isInvalid(new A());
        t2.isInvalid(new B());
        t2.isInvalid(new C());
        t2.isInvalid(new D());

    }

}
