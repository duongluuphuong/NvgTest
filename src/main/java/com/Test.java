package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test {

	private static WebDriver driver = null;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<String> testData = Files.readAllLines(Paths.get("src/main/resources/testdata.txt"));
		testData.get(0);
		testData.get(1);
		
	}
	
	private static WebElement FindElement(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(by));
		actions.perform();
		return driver.findElement(by);
	}

	private static List<WebElement> FindElements(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return driver.findElements(by);
	}
}
