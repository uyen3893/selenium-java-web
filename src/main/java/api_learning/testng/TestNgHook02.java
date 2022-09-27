package api_learning.testng;

import org.testng.annotations.*;

public class TestNgHook02 {

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("Before suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("\t---Before test");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("\t\t---Before class");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("\t\t\t---Before method");
    }

    @Test
    public void testSth() {
        System.out.println("\t\t\t\t---Test something");
    }

    @Test
    public void testSthElse() {
        System.out.println("\t\t\t\t---Test something else");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("\t\t\t---After method");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("\t\t---After class");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("\t---After test");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After suite");
    }
}
