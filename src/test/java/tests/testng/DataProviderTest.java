package tests.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderTest {

    @Test(dataProvider = "username")
    public void testDataProvider(User user) {
        System.out.println(user);
    }

    @DataProvider(name = "username")
    public User[] users() {
        User ti = new User("Ti", 18);
        User teo = new User("Teo", 19);
        User tun = new User("Tun", 20);
        return new User[] {ti, teo, tun};
    }

    public static class User {

        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
