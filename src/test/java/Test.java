public class Test {
    public static void main(String[] args) {
        String[] strings = "a-b-c".split("-", 2);
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
