package tests;

import com.google.common.reflect.ClassPath;
import driver.BrowserType;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args){
        //Get list of test templates
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> testClasses = new ArrayList<>();

        try {
            for (ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                if(info.getName().startsWith("tests") && !info.getName().equalsIgnoreCase("tests.BaseTest") && !info.getName().equalsIgnoreCase("tests.Main")) {
                    testClasses.add(info.load());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Get browser
        String browser = System.getProperty("browser");
        if (browser == null) {
            throw new RuntimeException("Please provide browser via -Dbrowser");
        }

        try {
            BrowserType.valueOf(browser);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("[ERR] " + browser + " is not supported, we covered for " + Arrays.toString(BrowserType.values()));
        }

        //Parallel test cases base on parallel maximum session
        final int MAX_PARALLEL_SESSION = 4;
        List<String> testGroupNames = new ArrayList<>();
        for (int index = 0; index < MAX_PARALLEL_SESSION; index++) {
            testGroupNames.add("Group " + (index + 1));
        }

        //Divide test classes into group
        int testNumEachGroup = testClasses.size()/testGroupNames.size();
        HashMap<String, List<Class<?>>> desiredCaps = new HashMap<>();
        for (int groupIndex = 0; groupIndex < testGroupNames.size(); groupIndex++) {
            int startIndex = groupIndex * testNumEachGroup;
            int endIndex = groupIndex == testGroupNames.size() - 1 ? testClasses.size() : (startIndex + testNumEachGroup);
            List<Class<?>> subTestList = testClasses.subList(startIndex, endIndex);
            desiredCaps.put(testGroupNames.get(groupIndex), subTestList);
        }

        //Build dynamic test suite
        TestNG testNG = new TestNG();
        XmlSuite xmlSuite = new XmlSuite();
        xmlSuite.setName("Regression");

        //Put all test classes into group
        List<XmlTest> allTest = new ArrayList<>();
        for (String groupName: desiredCaps.keySet()) {
            XmlTest xmlTest = new XmlTest(xmlSuite);
            xmlTest.setName(groupName);

            List<XmlClass> xmlClasses = new ArrayList<>();
            List<Class<?>> dedicatedClasses = desiredCaps.get(groupName);
            for (Class<?> dedicatedClass : dedicatedClasses) {
                xmlClasses.add(new XmlClass(dedicatedClass.getName()));
            }

            xmlTest.setXmlClasses(xmlClasses);
            xmlTest.addParameter("browser", browser);
            allTest.add(xmlTest);
        }

        //Add all test into suite
        boolean isTestingOnSafari = browser.equals("safari");
        xmlSuite.setTests(allTest);
        xmlSuite.setParallel(XmlSuite.ParallelMode.TESTS);
        xmlSuite.setThreadCount(isTestingOnSafari ? 1 : MAX_PARALLEL_SESSION);

        //Run a group of test
        if(isTestingOnSafari) {
            xmlSuite.addIncludedGroup("smoke");
        } else {
            String targetGroup = args.length != 0 ? args[0] : null;
            if (targetGroup != null) {
                xmlSuite.addIncludedGroup(targetGroup);
            }
        }

        System.out.println(xmlSuite.toXml());

        //Add the suite to the suite list
        List<XmlSuite> xmlSuites = new ArrayList<>();
        xmlSuites.add(xmlSuite);

        //Invoke run() method
        testNG.setXmlSuites(xmlSuites);
        testNG.run();
    }
}
