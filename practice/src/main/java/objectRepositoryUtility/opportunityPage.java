package objectRepositoryUtility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class opportunityPage {

	WebDriver driver;
	public opportunityPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}
		
	@FindBy(xpath = "//img[@src='themes/softed/images/btnL3Add.gif']")
    private WebElement createOpportunitybtn;
	
	
	
	public WebElement getCreateOpportunitytbtn() {
		return createOpportunitybtn;
	}
}
