import org.asynchttpclient.util.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestP24 {

    String PAYMENTS_URL = "https://next.privat24.ua/payments/dashboard";
    String IBAN = "UA333510050000026005325079000";
    String PAYER_FIO = "Тарасов Егор Викторович";
    String RECEIVER = "Сервис Плюс";
    String EDRPOU = "00032129";
    String PURPOSE = "товар за накладною №50";
    String USER_CARD_NUMBER = "4552331448138217";
    String CARD_HOLDER_NAME = "Егор";
    String CARD_HOLDER_SURNAME = "Тарасов";

    By newPayment = By.xpath("//input[@data-qa-node='query']");
    By details = By.xpath("//a[contains(@href, '/payments/form')]");
    By payer = By.xpath("//textarea[@data-qa-node='FIO']");
    By receiver = By.xpath("//textarea[@data-qa-node='CUSTOM_COMPANY']");
    By ipnEDRPOU = By.xpath("//textarea[@data-qa-node='CUSTOM_OKPO']");
    By destination = By.xpath("//textarea[@data-qa-node='DEST']");
    By amount = By.xpath("//input[@data-qa-node='SUM']");
    By cardNumber = By.xpath("//input[@data-qa-node='numberdebitSource']");
    By expDate = By.xpath("//input[@data-qa-node='expiredebitSource']");
    By cvv = By.xpath("//input[@data-qa-node='cvvdebitSource']");
    By holderName = By.xpath("//input[@data-qa-node='firstNamedebitSource']");
    By holderSurname = By.xpath("//input[@data-qa-node='lastNamedebitSource']");
    By submit = By.xpath("//button[@type='submit']");
    By placeInBasket = By.xpath("//button[contains(text(), 'Додати в кошик')]");
    By expectedPurpose = By.xpath("//div[@data-qa-node='details']");
    By expectedCard = By.xpath("//td[@data-qa-node='card']");
    By expectedAmount = By.xpath("//div[@data-qa-node='amount']");
    By expectedCommission = By.xpath("//span[@data-qa-node='commission']");

    @Test
    public void makePaymentByDetails() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        driver.get(PAYMENTS_URL);
        driver.findElement(newPayment).sendKeys(IBAN);
        driver.findElement(details).click();
        driver.findElement(payer).sendKeys(PAYER_FIO);
        driver.findElement(receiver).sendKeys(RECEIVER);
        driver.findElement(ipnEDRPOU).sendKeys(EDRPOU);
        driver.findElement(destination).sendKeys(PURPOSE);
        driver.findElement(amount).sendKeys("5000");
        driver.findElement(cardNumber).sendKeys(USER_CARD_NUMBER);
        driver.findElement(expDate).sendKeys("0525");
        driver.findElement(cvv).sendKeys("105");
        driver.findElement(holderName).sendKeys(CARD_HOLDER_NAME);
        driver.findElement(holderSurname).sendKeys(CARD_HOLDER_SURNAME);
        driver.findElement(submit).submit();
        driver.findElement(placeInBasket).click();

        Assert.assertEquals("Сплата за " + PURPOSE, driver.findElement(expectedPurpose).getText());
        Assert.assertEquals("4552 **** **** 8217", driver.findElement(expectedCard).getText());
        Assert.assertEquals("5 000 UAH", driver.findElement(expectedAmount).getText());
        Assert.assertEquals("3", driver.findElement(expectedCommission).getText());
    }
}