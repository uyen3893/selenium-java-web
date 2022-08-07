package models.components.global;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

@ComponentCssSelector(value = ".top-menu")
public class TopMenuComponent extends Component {

    public TopMenuComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public List<MainCateItemComponent> mainItemsElem() {
        return findComponents(MainCateItemComponent.class, driver);
    }

    @ComponentCssSelector(value = ".top-menu > li")
    public static class MainCateItemComponent extends Component{

        public MainCateItemComponent(WebDriver driver, WebElement component) {
            super(driver, component);
        }

        public WebElement catItemLinkElem() {
            return component.findElement(By.tagName("a"));
        }

        public List<CateItemComponent> cateItemComponentList() {
            Actions actions = new Actions(driver);
            actions.moveToElement(component).perform();
            return findComponents(CateItemComponent.class, driver);
        }
    }

    @ComponentCssSelector(value = ".sublist li a")
    public static class CateItemComponent extends Component {

        public CateItemComponent(WebDriver driver, WebElement component) {
            super(driver, component);
        }
    }
}
