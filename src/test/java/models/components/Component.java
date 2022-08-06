package models.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Component {

    protected WebDriver driver;
    protected WebElement component;
    protected WebDriverWait wait;

    public Component(WebDriver driver, WebElement component) {
        this.driver = driver;
        this.component = component;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public WebElement findElement(By by) {
        return component.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return component.findElements(by);
    }

    public <T extends  Component> T findComponent(Class<T> componentClass, WebDriver driver) {
        return findComponents(componentClass, driver).get(0);
    }

    public <T extends Component> List<T> findComponents(Class<T> componentClass, WebDriver driver) {
        By componentSelector;
        try {
            componentSelector = getComponentSelector(componentClass);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERR] The component must have a css selector");
        }
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(componentSelector));
        List<WebElement> results = component.findElements(componentSelector);

        Class<?>[] params = new Class[]{WebDriver.class, WebElement.class};
        Constructor<T> constructor;
        try {
            constructor = componentClass.getConstructor(params);
        } catch (Exception e) {
            throw new IllegalArgumentException
                    ("[ERR] The Component must have a constructor with parameters" + Arrays.toString(params));
        }

        List<T> components = results.stream().map(webElement -> {
            try {
                return constructor.newInstance(driver, webElement);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        return components;
    }

    private By getComponentSelector(Class<? extends Component> componentClass) {
        if (componentClass.isAnnotationPresent(ComponentCssSelector.class)) {
            return By.cssSelector(componentClass.getAnnotation(ComponentCssSelector.class).value());
        } else if (componentClass.isAnnotationPresent(ComponentXpathSelector.class)) {
            return By.xpath(componentClass.getAnnotation(ComponentXpathSelector.class).value());
        } else if (componentClass.isAnnotationPresent(ComponentIdSelector.class)) {
            return By.id(componentClass.getAnnotation(ComponentIdSelector.class).value());
        } else {
            throw new IllegalArgumentException("Component class " + componentClass + " must have annotation " + ComponentIdSelector.class.getSimpleName()
            + " or " + ComponentXpathSelector.class.getSimpleName() + " or " + ComponentCssSelector.class.getSimpleName());
        }
    }
}
