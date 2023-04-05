package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;


/*
 * This example shows how to use selenium testing using the web driver
 * with Chrome browser.
 *
 *  - Buttons, input, and anchor elements are located using XPATH expression.
 *  - onClick( ) method is used with buttons and anchor tags.
 *  - Input fields are located and sendKeys( ) method is used to enter test data.
 *  - Spring Boot JPA is used to initialize, verify and reset the database before
 *      and after testing.
 *
 *    Make sure that TEST_COURSE_ID is a valid course for TEST_SEMESTER.
 *
 *    URL is the server on which Node.js is running.
 */

@SpringBootTest
public class EndToEndAddStudentTest {

    public static final String CHROME_DRIVER_FILE_LOCATION = "/usr/local/bin/geckodriver";

    public static final String URL = "http://localhost:3000";

    public String TEST_STUDENT_EMAIL = "lutest@csumb.edu";

    public String TEST_STUDENT_NAME = "luis";

    public static final int SLEEP_DURATION = 1000; // 1 second.

    /*
     * When running in @SpringBootTest environment, database repositories can be used
     * with the actual database.
     */

    @Autowired
    StudentRepository studentRepository;

    /*
     * Admin add student TEST_STUDENT_NAME .
     */
    @Test
    public void addStudentTest() throws Exception {

        /*
         * if student is already enrolled, then delete the enrollment.
         */

        Student x = null;
        do {
            x = studentRepository.findByEmail(TEST_STUDENT_EMAIL);
            if (x != null)
                studentRepository.delete(x);
        } while (x != null);

        // set the driver location and start driver
        //@formatter:off
        // browser	property name 				Java Driver Class
        // edge 	webdriver.edge.driver 		EdgeDriver
        // FireFox 	webdriver.firefox.driver 	FirefoxDriver
        // IE 		webdriver.ie.driver 		InternetExplorerDriver
        //@formatter:on

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        // Puts an Implicit wait for 10 seconds before throwing exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {

            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

            // select the last of the radio buttons on the list of semesters page.

            WebElement we = driver.findElement(By.xpath("(//input[@type='radio'])[last()]"));
            we.click();

            // Locate and click "Add Student" button
            driver.findElement(By.xpath("//button[contains(text(),'Add Student')]")).click();
            Thread.sleep(SLEEP_DURATION);

            // enter student name and email click button
            // Find the input fields by their respective labels
            driver.findElement(By.xpath("//label[contains(text(),'Name:')]/input")).sendKeys(TEST_STUDENT_NAME);
            driver.findElement(By.xpath("//label[contains(text(),'Email:')]/input")).sendKeys(TEST_STUDENT_EMAIL);
            driver.findElement(By.xpath("//button[contains(text(),'Add Student')]")).click();
            Thread.sleep(SLEEP_DURATION);
        } catch (Exception ex) {
            throw ex;
        } finally {
            // clean up database.
            Student e = studentRepository.findByEmail(TEST_STUDENT_EMAIL);
            if (e != null)
                studentRepository.delete(e);

            driver.quit();
        }

    }
}
