/**
 * @author xuexin
 * @date 2018/2/9
 */
public class MainTest {
    public static void main(String[] args) {
        String[] arr = "my.permission1.read".split("\\.");
        for (String s : arr) {
            System.out.println(s);
        }
        System.out.println(arr.length);
    }
}