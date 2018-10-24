package com.gmail.hellaiser2973.pages;

import org.junit.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.UUID;

public class CreateMsgPage {

    public CreateMsgPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    WebDriver driver;
    @FindBy (id ="headline")
    private WebElement headline;

    @FindBy (id ="text")
    private WebElement text;

    @FindBy (id = "create")
    private WebElement CreateButton;


    public  String getRandomString(int length)

    {
        String randomStr = UUID.randomUUID().toString();
        while(randomStr.length() < length) {
            randomStr += UUID.randomUUID().toString();
        }
        return randomStr.substring(0, length);
    }


    public ShowMessagePage createMessage(String head, String txt) {
        headline.sendKeys(head);
        text.sendKeys(txt);
        CreateButton.click();
        return new ShowMessagePage(driver);
    }


    public void isOpened() {
        Assert.assertEquals("Create Message", driver.getTitle());
    }

}
