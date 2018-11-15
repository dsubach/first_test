package com.gmail.hellaiser2973.pages;

import com.gmail.hellaiser2973.ui.tables.ITable;
import com.gmail.hellaiser2973.ui.tables.ITableCriterion;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage extends BasePage implements ITable {

    private static int numbOfRecord;
    public static final String TITLE = "Message List";
    @FindBy(css = "a.nextLink")
    private WebElement nextPage;
    @FindBy(xpath = "//tbody//tr[last()]/td[2]")
    private WebElement inpHeadline;
    @FindBy(xpath = "//tbody//tr[last()]/td[3]")
    private WebElement inpText;
    @FindBy(xpath = "//div[@class='paginateButtons']/a[last()-1]")
    private WebElement lastPage;
    @FindBy(css = "a.step")
    private WebElement pageButton;

    MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    int counter = 0;

    WebElement all;

    public boolean rowExistsOnThisPage(ITableCriterion rowCriterion) {
        List<WebElement> allData = driver.findElements(By.xpath("//tbody//tr"));
        for (WebElement column : allData) {
            if (rowCriterion.matches(column)) {
                all = column;
                return true;
            }
            counter++;
        }
        return false;
    }

    public boolean rowExists(ITableCriterion rowCriterion) {

        int pageNumber;

        if (driver.findElements(By.className("step")).size() > 0) {       // если кнопки номера страницы есть, значит берем посл. номер страницы
            pageNumber = Integer.parseInt(lastPage.getText());
        } else pageNumber = 1;
        for (int i = 1; i <= pageNumber; i++) {
            if (rowExistsOnThisPage(rowCriterion)) {
                return true;
            }
            if (i != pageNumber) nextPage.click();
        }
        return false;
    }

    public WebElement getRow(ITableCriterion rowCriterion) {
        Assert.assertTrue(rowExists(rowCriterion));
        return all;


    }

    public WebElement getRowOnThisPage(ITableCriterion rowCriterion) {
        Assert.assertTrue(rowExistsOnThisPage(rowCriterion));
        return all;
    }

    public void isMessageCreated(String head, String text) {
        FindMessages rowCriterion = new FindMessages(head, text);
        Assert.assertTrue(rowExists(rowCriterion));
    }


    //  public void isMessageCreated() {
    //     Assert.assertTrue(findMessage(head, text));
    // }


//предусмотреть чтобы при переходам по страницам сначала открывалась первая стр


















    private boolean findMessage(String head, String text) {
        int currCell;  //текущее поле в таблице
        int pageNumber; //номер страницы
        if (driver.findElements(By.className("step")).size() > 0) {       // если кнопки номера страницы есть, значит берем посл. номер страницы
            pageNumber = Integer.parseInt(lastPage.getText());
        } else pageNumber = 1;
        for (int i = 1; i <= pageNumber; i++) {
            currCell = 0;
            List<WebElement> allHeadlines = driver.findElements(By.cssSelector("tr td:nth-child(2)"));
            List<WebElement> allText = driver.findElements(By.cssSelector("tr td:nth-child(3)"));
            for (WebElement column : allHeadlines) {
                if (column.getText().equals(head)) {
                    if (allText.get(currCell).getText().equals(text)) {
                        MainPage.numbOfRecord = currCell + 1;
                        return true;
                    }
                }
                currCell++;
            }
            if (i != pageNumber) nextPage.click();
        }
        return false;
    }

    void deleteLastMsg() {
        String a = "//tbody/tr[" + MainPage.numbOfRecord + "]/td";
        WebElement dltButton = driver.findElement(By.xpath(a)).findElement(By.linkText("Delete"));
        dltButton.click();
    }

    void isMessageDeleted(String head, String text) {
        Assert.assertFalse(findMessage(head, text));
    }
}


