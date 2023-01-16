package nucleusautomation;
import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddMerchant 
{

	public static void main(String[] args) throws IOException, InterruptedException, AWTException 
	{
		// TODO Auto-generated method stub

		

	
		//Searches for excel file and counts the total number of rows
		FileInputStream fileObj = new FileInputStream("C:\\Users\\Ajinkya\\OneDrive - AFFINITY GLOBAL ADVERTISING PVT LTD\\Desktop\\addmerchant.xlsx");
		XSSFWorkbook workbookobj = new XSSFWorkbook(fileObj);
		XSSFSheet sheetObj = workbookobj.getSheet("Sheet1");
		int rcount = sheetObj.getLastRowNum();
		System.out.println(rcount);
		int r;

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ajinkya\\Downloads\\ChromeDriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("https://nucleus.siteplug.com/login");
	
		
		String username = new String();
		String password = new String();
		username = "ajinkya97barge@gmail.com";
		password = "Ab@12345";
		
		//Enters User name
		WebElement email = driver.findElement(By.id("email"));
		email.sendKeys(username);

		//Enters Password
		WebElement pwd = driver.findElement(By.id("password"));
		pwd.sendKeys(password);

		//Click on sign in button
		WebElement signin = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/form/div[4]/button"));
		signin.click();
		
		//for loop for each row, it complete action of each row and then move to next row
		for ( r = 0; r < rcount; r++) 
		{	
			
			Thread.sleep(5000);
			//Clicks on Add button on home screen
			WebElement Add = driver.findElement(By.xpath("//*[@id=\"layout-wrapper\"]/div[2]/div/div/div/div[3]/div/div/div[2]/div/button"));
			Add.click();

			WebElement Add2 = driver.findElement(By.xpath("//*[@id=\"layout-wrapper\"]/div[2]/div/div/div/div[3]/div/div/div[2]/div/div/a"));
			Add2.click();

			//Click on merchant drop down
			WebElement merchant = driver.findElement(By.id("ac_brand_id"));
			merchant.click();

			//Gets the name of merchant from excel
			String test = sheetObj.getRow(r + 1).getCell(0).getStringCellValue();
			
			Thread.sleep(1000);
			
			//If country column is blank 
			if(test.isBlank())
			{
				System.out.println("Merchant name is blank");
				
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[1]/div[1]/div[1]/div[2]/ul/li[1]/span")).click();
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
				continue;		
			}
			
			try {
			//Enters the name of merchant in search box of merchant drop down
			WebElement entermerchant = driver.findElement(By.name("ac_brand_id"));
			entermerchant.sendKeys(test);
			
			Thread.sleep(1000);
			
			//Selects the merchant
			WebElement entermerchant1 = driver.findElement(By.xpath("//div[@class='custom-drodown-expand']/ul/li[1]"));
			entermerchant1.click();
			}
			catch (Exception e) {
				
				System.out.println("Merchant Name is invalid");
				driver.findElement(By.xpath("//*[@id=\"merchantName\"]/span")).click();
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[1]/div[1]/div[1]/div[2]/ul/li[1]/span")).click();
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
				
			}
			Thread.sleep(1000);
		
			//Clicks on Country Drop down
			driver.findElement(By.id("ac_cc")).click();
			Thread.sleep(1000);
			
			//Stores the name of countries in web element List
			java.util.List<WebElement> product1 = driver.findElements(By.xpath("//ul[@class='existing-mapping-list does_not_exists']/li"));
			
			//Gets the name of country from excel
			String test1 = sheetObj.getRow(r + 1).getCell(1).getStringCellValue();
			
			boolean labelSet = false;
			for (WebElement ptype:product1) 
						
			{
				//Compares the excel country name with List of countries
				if (ptype.getText().equals(test1))
				{
						
							ptype.click();
							labelSet = true;
							break;
				
				}
				
			}   
			//If country is not present in list it throws an error
			
			  if(!labelSet) 
			  { 
				  System.out.println("Country is Invalid"); 
					driver.findElement(By.xpath("//*[@id=\"merchantName\"]/span")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[1]/div[1]/div[1]/div[2]/ul/li[1]/span")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
					continue;
			  }
			 
				
			//Clicks on add demand partner
			driver.findElement(By.id("add_demand_partner")).click();
			
			
			//Gets the demand partner from excel
			String searchDP = sheetObj.getRow(r + 1).getCell(2).getStringCellValue();
			
			
			//Clicks on demand partner drop down
			driver.findElement(By.id("searchDD")).sendKeys(searchDP);
			Thread.sleep(1000);
			
			
			//Stores the name of demand partners in WebElement list.
			java.util.List<WebElement> searchdemandpartner = driver.findElements(By.xpath("//div[@class='dropdown-menu dropdown-menu-right show']/a"));
			
			
			boolean DPlst = false;
			for(WebElement DPlist : searchdemandpartner)
			{
				//Compares the excel value with the list of demand partners
				if (DPlist.getText().equals(searchDP))
				{
					DPlist.click();
					DPlst=true;
					break;
				}
			}
			if(!DPlst)
			{
				System.out.println("Demand partner is invalid");
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
				continue;
			}
			
			//Gets the Payment model name from excel
			String paymentname = sheetObj.getRow(r + 1).getCell(3).getStringCellValue();
			
			//Clicks on payment model drop down
			driver.findElement(By.id("bid_type_1")).click();
			
			//Stores the name of Payment models in WebElement list
			java.util.List<WebElement> findpayment = driver.findElements(By.xpath("//div[@class='form-group']/select/option"));
			
			
			boolean paymentlst = false;
			for (WebElement paymentlist : findpayment)
			{
				String name = paymentlist.getText();
				
				//Compares the excel value with the list of demand partners
				if (name.equals(paymentname))
				{
					paymentlist.click();
					paymentlst=true;
					break;
				}
			}
			if(!paymentlst)
			{
				System.out.println("Payment model is invalid");
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
				continue;
			}
			
			Thread.sleep(1000);
			
			//Clicks on Payment modal drop down again, to clos the drop down 
			driver.findElement(By.id("bid_type_1")).click();
			Thread.sleep(1000);
		
		
			//Checks if deeplink & Clickid is available (Yes/No)
			String deeplink = sheetObj.getRow(r + 1).getCell(4).getStringCellValue();
			String clickid = sheetObj.getRow(r + 1).getCell(5).getStringCellValue();
			
			//Gets Homepage URL from Excel
			String gethomepage = sheetObj.getRow(r + 1).getCell(6).getStringCellValue();
			
			//Using Data Formatter because EPc is Numeric value and it is stored in String
			
			  DataFormatter formatter = new DataFormatter();
			  String getEPC = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(8));
		
			  
			 
			if ( deeplink.equals("Yes")&&clickid.equals("Yes")) 
			{
				
				//Clicks on Clickid and Deeplink Checkboxes
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[1]/div[2]/div[1]/label")).click();	
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[1]/div[2]/div[2]/label")).click();
				
				Thread.sleep(1000);
				
				//Enters Homepage URL in Textbox
				driver.findElement(By.id("url_1")).sendKeys(gethomepage);
				
				//Gets Deeplink URL from Excel and Enters in Deplink Textbox
				String getdeeplink = sheetObj.getRow(r + 1).getCell(7).getStringCellValue();
				driver.findElement(By.id("dp_url_1")).sendKeys(getdeeplink);
			}   

			/*	//selects values from excel according to fields of Auto EPC
				String clientsecret = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(10));	
				String clienttoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(11));
				String clientid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(12));
				String clientdomain = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(13));
				String publisherid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(14));
				String oath2token = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(15));
				String affiliateid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(16));					
				String authkey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(17));
				String user = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(18));					
				String key = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(19));
				String apikey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(20));
				String authenticationtoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(21));
				String emaildgdip = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(22));
				String password1 = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(23));
				String projectid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(24));
				String accountid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(25));
				String accountsid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(26));
				String username1 = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(27));
				String userid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(28));
				String userapikey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(29));
				String campaignid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(30));
				String authtoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(31));
				String secretkey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(32));
				String organizationid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(33));
				
				java.util.List<WebElement> l = driver.findElements(By.id("ins_undefined_client_secret"));
				java.util.List<WebElement> m = driver.findElements(By.id("ins_undefined_clientDomain"));
				java.util.List<WebElement> n = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_affiliate_id"));
				java.util.List<WebElement> p = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_affiliate_id"));
				java.util.List<WebElement> q = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> s = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> t = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> u = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> v = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> w = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> x = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> a = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> b = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> c = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> d = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> e = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> f = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				java.util.List<WebElement> g = (java.util.List<WebElement>) driver.findElement(By.id("ins_undefined_user"));
				
				
				
				if (getEPC.isBlank())
				{
					//Clicks on Auto EPC toggle button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/span[1]")).click();
										
					//Puttting details of AutoEPC fields/Textbox for Admittad Demand partner
				      if(l.size()> 0)
				      {
				      

				    	  WebElement inputBox = driver.findElement(By.id("ins_undefined_client_secret"));

							// Check whether input field is blank
							if(inputBox.getAttribute("value").isEmpty())
							{
								
								
								driver.findElement(By.id("ins_undefined_client_secret")).sendKeys(clientsecret);
								driver.findElement(By.id("ins_undefined_client_id")).sendKeys(clientid);
								driver.findElement(By.id("ins_undefined_client_token")).sendKeys(clienttoken);
								driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
							}
							else 
							{
								driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
							
							}

				      }
						//Puttting details of AutoEPC fields/Textbox for Affiliate window Demand partner
				      else if(m.size()>0)
				      {

							WebElement inputBox = driver.findElement(By.id("ins_undefined_clientDomain"));
						
							// Check whether input field is blank
							if(inputBox.getAttribute("value").isEmpty())
							{
								
								
								driver.findElement(By.id("ins_undefined_clientDomain")).sendKeys(clientdomain);
								driver.findElement(By.id("ins_undefined_publisherId")).sendKeys(publisherid);
								driver.findElement(By.id("ins_undefined_oauth2_token")).sendKeys(oath2token);
								driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
							}
							else 
							{
								driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
							
							}
  	
				    	  
				      }
				      //Puttting details of AutoEPC fields/Textbox for Avantlink Demand partner
					  else if(n.size()>0))
					  {
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_affiliate_id"));

						// Check whether input field is blank
						if(inputBox.getAttribute("value").isEmpty())
						{
														
							driver.findElement(By.id("ins_undefined_affiliate_id")).sendKeys(affiliateid);
							driver.findElement(By.id("ins_undefined_auth_key")).sendKeys(authkey);
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						}
						else 
						{
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Brand Reward Demand partner
					else if(p.size()> 0)
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_user"));
						
						// Check whether input field is blank
						if(inputBox.getAttribute("value").isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_user")).sendKeys(user);
							driver.findElement(By.id("ins_undefined_key")).sendKeys(key);
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						}
						else 
						{
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Commission factory Demand partner
					else if(q.size()>0)
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						
						// Check whether input field is blank
						if(inputBox.getAttribute("value").isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						}
						else 
						{
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						
						}
						
					}
					
					//Puttting details of AutoEPC fields/Textbox for Commission junction Demand partner
					else if(s.size()>0)
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_pub_id"));
						
						// Check whether input field is blank
						if(inputBox.getAttribute("value").isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_pub_id")).sendKeys(publisherid);
							driver.findElement(By.id("")).sendKeys(authenticationtoken);
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						}
						else 
						{
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for DGDIP Demand partner
					else if(t.size()>0)
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_email"));

						// Check whether input field is blank
						if(inputBox.getAttribute("value").isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_email")).sendKeys(emaildgdip);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							driver.findElement(By.id("ins_undefined_project_id")).sendKeys(projectid);
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						}
						else 
						{
							driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]"));
						
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Flexoffers Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_apikey"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
						}
					}

					//Puttting details of AutoEPC fields/Textbox for Impact Radius Demand partner
					else if(driver.findElement(By.id("ins_undefined_account_id")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_account_id"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_account_id")).sendKeys(accountid);
							driver.findElement(By.id("ins_undefined_account_sid")).sendKeys(accountsid);
							driver.findElement(By.id("ins_undefined_auth_token")).sendKeys(authenticationtoken);
						}
					}
					
					//Putting details of AutoEPC fields/Textbox for Kelkoo Demand partner
					else if(driver.findElement(By.id("ins_undefined_username")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_username"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_username")).sendKeys(username1);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for partnerize Demand partner
					else if(driver.findElement(By.id("ins_undefined_UserID")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_UserID"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_UserID")).sendKeys(userid);
							driver.findElement(By.id("ins_undefined_API_KEY")).sendKeys(apikey);
							driver.findElement(By.id("ins_undefined_USER_API_KEY")).sendKeys(userapikey);
							driver.findElement(By.id("ins_undefined_PUBLISHER_ID")).sendKeys(publisherid);
							driver.findElement(By.id("ins_undefined_CAMPAIGN_ID")).sendKeys(campaignid);
							
							}
					}

					//Puttting details of AutoEPC fields/Textbox for pepperjam Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_apikey"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Rakuten Linkshare Demand partner
					else if(driver.findElement(By.id("ins_undefined_auth_token")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_auth_token"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_auth_token")).sendKeys(authtoken);
							
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Share A sale Demand partner
					else if(driver.findElement(By.id("ins_undefined_secretkey")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_affiliate_id"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_affiliate_id")).sendKeys(affiliateid);
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							driver.findElement(By.id("ins_undefined_secretkey")).sendKeys(secretkey);
							
							
							}
					}					
					
					//Puttting details of AutoEPC fields/Textbox for Trade doubler Demand partner
					else if(driver.findElement(By.id("ins_undefined_organizationId")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_organizationId"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_organizationId")).sendKeys(organizationid);
							driver.findElement(By.id("ins_undefined_key")).sendKeys(key);
							
							
							}
					}					
					
					//Puttting details of AutoEPC fields/Textbox for Web gains Demand partner
					else if(driver.findElement(By.id("ins_undefined_username")).isDisplayed())
					{
						
						WebElement inputBox1 = driver.findElement(By.id("ins_undefined_username"));
						String value1 = inputBox1.getAttribute("value");

						// Check whether input field is blank
						if(value1.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_username")).sendKeys(username1);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							
							
							}
					}	
								
					
				
				else
				{
					driver.findElement(By.id("estimted_epc")).sendKeys(getEPC);
				}
				
				//Clicks on save button
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]")).click();
				Thread.sleep(1000);
				System.exit(0);
				
				//Click on Continue button
				WebElement continuebtn = driver.findElement(By.xpath("//*[@id=\"add-demand-partner-countinue\"]"));
			  
				//JavascriptExecutor to click element
			      JavascriptExecutor jse = (JavascriptExecutor) driver;
			      jse.executeScript("arguments[0].click();", continuebtn);
			      Thread.sleep(1000);
			      
			      
			    //Gets the value of Allocation or Auction from excel  
				String allocation = sheetObj.getRow(r + 1).getCell(9).getStringCellValue();
				
				if(allocation.equals("Allocation"))
				{
					//Clicks on allocate toggle button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[2]/div/div/span[2]/small")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[4]/span")).click();
					
					//Clicks on Save&Submit button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[5]/button[2]")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
					
				}
				else
				{
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[5]/button[2]")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
				}
				
					}}
			
			else if ( deeplink.equals("Yes")) 
			{
				
				//Clicks on Deeplink Checkbox
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[1]/div[2]/div[2]/label")).click();
				
				Thread.sleep(1000);
				
				//Enters Homepage URL in Textbox
				driver.findElement(By.id("url_1")).sendKeys(gethomepage);
				
				//Gets Deeplink URL from Excel and Enters in Deplink Textbox
				String getdeeplink1 = sheetObj.getRow(r + 1).getCell(7).getStringCellValue();
				driver.findElement(By.id("dp_url_1")).sendKeys(getdeeplink1);
		           
				if (getEPC.isBlank())
				{
					
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/span[1]")).click();
					
					
					//selects values from excel according to fields of Auto EPC
					String clientsecret = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(10));	
					String clienttoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(11));
					String clientid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(12));
					String clientdomain = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(13));
					String publisherid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(14));
					String oath2token = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(15));
					String affiliateid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(16));					
					String authkey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(17));
					String user = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(18));					
					String key = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(19));
					String apikey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(20));
					String authenticationtoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(21));
					String emaildgdip = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(22));
					String password1 = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(23));
					String projectid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(24));
					String accountid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(25));
					String accountsid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(26));
					String username1 = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(27));
					String userid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(28));
					String userapikey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(29));
					String campaignid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(30));
					String authtoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(31));
					String secretkey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(32));
					String organizationid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(33));
					
					
					//Puttting details of AutoEPC fields/Textbox for Admittad Demand partner
					if(driver.findElement(By.id("ins_undefined_client_secret")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_client_secret"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_client_secret")).sendKeys(clientsecret);
							driver.findElement(By.id("ins_undefined_client_id")).sendKeys(clientid);
							driver.findElement(By.id("ins_undefined_client_token")).sendKeys(clienttoken);
						}
					}	
					
					//Puttting details of AutoEPC fields/Textbox for Affiliate window Demand partner
					else if(driver.findElement(By.id("ins_undefined_clientDomain")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_clientDomain"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_clientDomain")).sendKeys(clientdomain);
							driver.findElement(By.id("ins_undefined_publisherId")).sendKeys(publisherid);
							driver.findElement(By.id("ins_undefined_oauth2_token")).sendKeys(oath2token);
						}
					}						

					//Puttting details of AutoEPC fields/Textbox for Avantlink Demand partner
					else if(driver.findElement(By.id("ins_undefined_affiliate_id")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_affiliate_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_affiliate_id")).sendKeys(affiliateid);
							driver.findElement(By.id("ins_undefined_auth_key")).sendKeys(authkey);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Brand Reward Demand partner
					else if(driver.findElement(By.id("ins_undefined_user")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_user"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_user")).sendKeys(user);
							driver.findElement(By.id("ins_undefined_key")).sendKeys(key);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Commission factory Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
						
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Commission junction Demand partner
					else if(driver.findElement(By.id("ins_undefined_auth_token")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_pub_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_pub_id")).sendKeys(publisherid);
							driver.findElement(By.id("")).sendKeys(authenticationtoken);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for DGDIP Demand partner
					else if(driver.findElement(By.id("ins_undefined_email")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_email"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_email")).sendKeys(emaildgdip);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							driver.findElement(By.id("ins_undefined_project_id")).sendKeys(projectid);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Flexoffers Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
						}
					}

					//Puttting details of AutoEPC fields/Textbox for Impact Radius Demand partner
					else if(driver.findElement(By.id("ins_undefined_account_id")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_account_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_account_id")).sendKeys(accountid);
							driver.findElement(By.id("ins_undefined_account_sid")).sendKeys(accountsid);
							driver.findElement(By.id("ins_undefined_auth_token")).sendKeys(authenticationtoken);
						}
					}
					
					//Putting details of AutoEPC fields/Textbox for Kelkoo Demand partner
					else if(driver.findElement(By.id("ins_undefined_username")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_username"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_username")).sendKeys(username1);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for partnerize Demand partner
					else if(driver.findElement(By.id("ins_undefined_UserID")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_UserID"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_UserID")).sendKeys(userid);
							driver.findElement(By.id("ins_undefined_API_KEY")).sendKeys(apikey);
							driver.findElement(By.id("ins_undefined_USER_API_KEY")).sendKeys(userapikey);
							driver.findElement(By.id("ins_undefined_PUBLISHER_ID")).sendKeys(publisherid);
							driver.findElement(By.id("ins_undefined_CAMPAIGN_ID")).sendKeys(campaignid);
							
							}
					}

					//Puttting details of AutoEPC fields/Textbox for pepperjam Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Rakuten Linkshare Demand partner
					else if(driver.findElement(By.id("ins_undefined_auth_token")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_auth_token"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_auth_token")).sendKeys(authtoken);
							
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Share A sale Demand partner
					else if(driver.findElement(By.id("ins_undefined_secretkey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_affiliate_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_affiliate_id")).sendKeys(affiliateid);
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							driver.findElement(By.id("ins_undefined_secretkey")).sendKeys(secretkey);
							
							
							}
					}					
					
					//Puttting details of AutoEPC fields/Textbox for Trade doubler Demand partner
					else if(driver.findElement(By.id("ins_undefined_organizationId")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_organizationId"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_organizationId")).sendKeys(organizationid);
							driver.findElement(By.id("ins_undefined_key")).sendKeys(key);
							
							
							}
					}					
					
					//Puttting details of AutoEPC fields/Textbox for Web gains Demand partner
					else if(driver.findElement(By.id("ins_undefined_username")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_username"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_username")).sendKeys(username1);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							
							
							}
					}	
								
					
					
				}
				else
				{
					driver.findElement(By.id("estimted_epc")).sendKeys(getEPC);
				}
				
				//Clicks on save button
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]")).click();
				Thread.sleep(1000);
				
				//Click on Continue button
				WebElement continuebtn = driver.findElement(By.xpath("//*[@id=\"add-demand-partner-countinue\"]"));
			  
				//JavascriptExecutor to click element
			      JavascriptExecutor jse = (JavascriptExecutor) driver;
			      jse.executeScript("arguments[0].click();", continuebtn);
			      Thread.sleep(1000);
			      
			      
			    //Gets the value of Allocation or Auction from excel  
				String allocation = sheetObj.getRow(r + 1).getCell(9).getStringCellValue();
				
				if(allocation.equals("Allocation"))
				{
					//Clicks on allocation toggle button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[2]/div/div/span[2]/small")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[4]/span")).click();
					
					//Clicks on Save&Submit button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[5]/button[2]")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
					
				}
				else
				{
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[5]/button[2]")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
				}

				
			}
			
			else if ( clickid.equals("Yes")) 
			{
				
				//Clicks on Click id Check box
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[1]/div[2]/div[1]/label")).click();
				
				Thread.sleep(1000);
				
				//Enters Home page URL in Text box
				driver.findElement(By.id("url_1")).sendKeys(gethomepage);
				   
				if (getEPC.isBlank())
				{
					
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/span[1]")).click();
					
					
					//selects values from excel according to fields of Auto EPC
					String clientsecret = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(10));	
					String clienttoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(11));
					String clientid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(12));
					String clientdomain = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(13));
					String publisherid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(14));
					String oath2token = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(15));
					String affiliateid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(16));					
					String authkey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(17));
					String user = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(18));					
					String key = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(19));
					String apikey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(20));
					String authenticationtoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(21));
					String emaildgdip = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(22));
					String password1 = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(23));
					String projectid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(24));
					String accountid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(25));
					String accountsid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(26));
					String username1 = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(27));
					String userid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(28));
					String userapikey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(29));
					String campaignid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(30));
					String authtoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(31));
					String secretkey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(32));
					String organizationid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(33));
					
					
					//Puttting details of AutoEPC fields/Textbox for Admittad Demand partner
					if(driver.findElement(By.id("ins_undefined_client_secret")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_client_secret"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_client_secret")).sendKeys(clientsecret);
							driver.findElement(By.id("ins_undefined_client_id")).sendKeys(clientid);
							driver.findElement(By.id("ins_undefined_client_token")).sendKeys(clienttoken);
						}
					}	
					
					//Puttting details of AutoEPC fields/Textbox for Affiliate window Demand partner
					else if(driver.findElement(By.id("ins_undefined_clientDomain")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_clientDomain"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_clientDomain")).sendKeys(clientdomain);
							driver.findElement(By.id("ins_undefined_publisherId")).sendKeys(publisherid);
							driver.findElement(By.id("ins_undefined_oauth2_token")).sendKeys(oath2token);
						}
					}						

					//Puttting details of AutoEPC fields/Textbox for Avantlink Demand partner
					else if(driver.findElement(By.id("ins_undefined_affiliate_id")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_affiliate_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_affiliate_id")).sendKeys(affiliateid);
							driver.findElement(By.id("ins_undefined_auth_key")).sendKeys(authkey);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Brand Reward Demand partner
					else if(driver.findElement(By.id("ins_undefined_user")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_user"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_user")).sendKeys(user);
							driver.findElement(By.id("ins_undefined_key")).sendKeys(key);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Commission factory Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
						
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Commission junction Demand partner
					else if(driver.findElement(By.id("ins_undefined_auth_token")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_pub_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_pub_id")).sendKeys(publisherid);
							driver.findElement(By.id("")).sendKeys(authenticationtoken);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for DGDIP Demand partner
					else if(driver.findElement(By.id("ins_undefined_email")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_email"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_email")).sendKeys(emaildgdip);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							driver.findElement(By.id("ins_undefined_project_id")).sendKeys(projectid);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Flexoffers Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
						}
					}

					//Puttting details of AutoEPC fields/Textbox for Impact Radius Demand partner
					else if(driver.findElement(By.id("ins_undefined_account_id")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_account_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_account_id")).sendKeys(accountid);
							driver.findElement(By.id("ins_undefined_account_sid")).sendKeys(accountsid);
							driver.findElement(By.id("ins_undefined_auth_token")).sendKeys(authenticationtoken);
						}
					}
					
					//Putting details of AutoEPC fields/Textbox for Kelkoo Demand partner
					else if(driver.findElement(By.id("ins_undefined_username")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_username"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_username")).sendKeys(username1);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for partnerize Demand partner
					else if(driver.findElement(By.id("ins_undefined_UserID")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_UserID"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_UserID")).sendKeys(userid);
							driver.findElement(By.id("ins_undefined_API_KEY")).sendKeys(apikey);
							driver.findElement(By.id("ins_undefined_USER_API_KEY")).sendKeys(userapikey);
							driver.findElement(By.id("ins_undefined_PUBLISHER_ID")).sendKeys(publisherid);
							driver.findElement(By.id("ins_undefined_CAMPAIGN_ID")).sendKeys(campaignid);
							
							}
					}

					//Puttting details of AutoEPC fields/Textbox for pepperjam Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Rakuten Linkshare Demand partner
					else if(driver.findElement(By.id("ins_undefined_auth_token")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_auth_token"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_auth_token")).sendKeys(authtoken);
							
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Share A sale Demand partner
					else if(driver.findElement(By.id("ins_undefined_secretkey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_affiliate_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_affiliate_id")).sendKeys(affiliateid);
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							driver.findElement(By.id("ins_undefined_secretkey")).sendKeys(secretkey);
							
							
							}
					}					
					
					//Puttting details of AutoEPC fields/Textbox for Trade doubler Demand partner
					else if(driver.findElement(By.id("ins_undefined_organizationId")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_organizationId"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_organizationId")).sendKeys(organizationid);
							driver.findElement(By.id("ins_undefined_key")).sendKeys(key);
							
							
							}
					}					
					
					//Puttting details of AutoEPC fields/Textbox for Web gains Demand partner
					else if(driver.findElement(By.id("ins_undefined_username")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_username"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_username")).sendKeys(username1);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							
							
							}
					}	
								
					
				}
				else
				{
					driver.findElement(By.id("estimted_epc")).sendKeys(getEPC);
				}
				
				//Clicks on save button
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]")).click();
				Thread.sleep(1000);
				
				//Click on Continue button
				WebElement continuebtn = driver.findElement(By.xpath("//*[@id=\"add-demand-partner-countinue\"]"));
			  
				//JavascriptExecutor to click element
			      JavascriptExecutor jse = (JavascriptExecutor) driver;
			      jse.executeScript("arguments[0].click();", continuebtn);
			      Thread.sleep(1000);
			      
			      
			    //Gets the value of Allocation or Auction from excel  
				String allocation = sheetObj.getRow(r + 1).getCell(9).getStringCellValue();
				
				if(allocation.equals("Allocation"))
				{
					//Clicks on allocation toggle button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[2]/div/div/span[2]/small")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[4]/span")).click();
					
					//Clicks on Save&Submit button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[5]/button[2]")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
					
				}
				else
				{
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[5]/button[2]")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
				}

				
			}
			else 
			{
				
				//Enters Home page URL in Text box
				driver.findElement(By.id("url_1")).sendKeys(gethomepage);
				   
				if (getEPC.isBlank())
				{
					
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/span[1]")).click();
					
					
					//selects values from excel according to fields of Auto EPC
					String clientsecret = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(10));	
					String clienttoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(11));
					String clientid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(12));
					String clientdomain = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(13));
					String publisherid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(14));
					String oath2token = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(15));
					String affiliateid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(16));					
					String authkey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(17));
					String user = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(18));					
					String key = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(19));
					String apikey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(20));
					String authenticationtoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(21));
					String emaildgdip = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(22));
					String password1 = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(23));
					String projectid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(24));
					String accountid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(25));
					String accountsid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(26));
					String username1 = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(27));
					String userid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(28));
					String userapikey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(29));
					String campaignid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(30));
					String authtoken = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(31));
					String secretkey = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(32));
					String organizationid = formatter.formatCellValue(sheetObj.getRow(r+1).getCell(33));
					
					
					//Puttting details of AutoEPC fields/Textbox for Admittad Demand partner
					if(driver.findElement(By.id("ins_undefined_client_secret")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_client_secret"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_client_secret")).sendKeys(clientsecret);
							driver.findElement(By.id("ins_undefined_client_id")).sendKeys(clientid);
							driver.findElement(By.id("ins_undefined_client_token")).sendKeys(clienttoken);
						}
					}	
					
					//Puttting details of AutoEPC fields/Textbox for Affiliate window Demand partner
					else if(driver.findElement(By.id("ins_undefined_clientDomain")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_clientDomain"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_clientDomain")).sendKeys(clientdomain);
							driver.findElement(By.id("ins_undefined_publisherId")).sendKeys(publisherid);
							driver.findElement(By.id("ins_undefined_oauth2_token")).sendKeys(oath2token);
						}
					}						

					//Puttting details of AutoEPC fields/Textbox for Avantlink Demand partner
					else if(driver.findElement(By.id("ins_undefined_affiliate_id")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_affiliate_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_affiliate_id")).sendKeys(affiliateid);
							driver.findElement(By.id("ins_undefined_auth_key")).sendKeys(authkey);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Brand Reward Demand partner
					else if(driver.findElement(By.id("ins_undefined_user")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_user"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_user")).sendKeys(user);
							driver.findElement(By.id("ins_undefined_key")).sendKeys(key);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Commission factory Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
						
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Commission junction Demand partner
					else if(driver.findElement(By.id("ins_undefined_auth_token")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_pub_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_pub_id")).sendKeys(publisherid);
							driver.findElement(By.id("")).sendKeys(authenticationtoken);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for DGDIP Demand partner
					else if(driver.findElement(By.id("ins_undefined_email")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_email"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_email")).sendKeys(emaildgdip);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							driver.findElement(By.id("ins_undefined_project_id")).sendKeys(projectid);
						}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Flexoffers Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
						}
					}

					//Puttting details of AutoEPC fields/Textbox for Impact Radius Demand partner
					else if(driver.findElement(By.id("ins_undefined_account_id")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_account_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_account_id")).sendKeys(accountid);
							driver.findElement(By.id("ins_undefined_account_sid")).sendKeys(accountsid);
							driver.findElement(By.id("ins_undefined_auth_token")).sendKeys(authenticationtoken);
						}
					}
					
					//Putting details of AutoEPC fields/Textbox for Kelkoo Demand partner
					else if(driver.findElement(By.id("ins_undefined_username")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_username"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_username")).sendKeys(username1);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for partnerize Demand partner
					else if(driver.findElement(By.id("ins_undefined_UserID")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_UserID"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							driver.findElement(By.id("ins_undefined_UserID")).sendKeys(userid);
							driver.findElement(By.id("ins_undefined_API_KEY")).sendKeys(apikey);
							driver.findElement(By.id("ins_undefined_USER_API_KEY")).sendKeys(userapikey);
							driver.findElement(By.id("ins_undefined_PUBLISHER_ID")).sendKeys(publisherid);
							driver.findElement(By.id("ins_undefined_CAMPAIGN_ID")).sendKeys(campaignid);
							
							}
					}

					//Puttting details of AutoEPC fields/Textbox for pepperjam Demand partner
					else if(driver.findElement(By.id("ins_undefined_apikey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_apikey"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Rakuten Linkshare Demand partner
					else if(driver.findElement(By.id("ins_undefined_auth_token")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_auth_token"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_auth_token")).sendKeys(authtoken);
							
							}
					}
					
					//Puttting details of AutoEPC fields/Textbox for Share A sale Demand partner
					else if(driver.findElement(By.id("ins_undefined_secretkey")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_affiliate_id"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_affiliate_id")).sendKeys(affiliateid);
							driver.findElement(By.id("ins_undefined_apikey")).sendKeys(apikey);
							driver.findElement(By.id("ins_undefined_secretkey")).sendKeys(secretkey);
							
							
							}
					}					
					
					//Puttting details of AutoEPC fields/Textbox for Trade doubler Demand partner
					else if(driver.findElement(By.id("ins_undefined_organizationId")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_organizationId"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_organizationId")).sendKeys(organizationid);
							driver.findElement(By.id("ins_undefined_key")).sendKeys(key);
							
							
							}
					}					
					
					//Puttting details of AutoEPC fields/Textbox for Web gains Demand partner
					else if(driver.findElement(By.id("ins_undefined_username")).isDisplayed())
					{
						
						WebElement inputBox = driver.findElement(By.id("ins_undefined_username"));
						String value = inputBox.getAttribute("value");

						// Check whether input field is blank
						if(value.isEmpty())
						{
							
							
							driver.findElement(By.id("ins_undefined_username")).sendKeys(username1);
							driver.findElement(By.id("ins_undefined_password")).sendKeys(password1);
							
							
							}
					}	
								
					
				}
				else
				{
					driver.findElement(By.id("estimted_epc")).sendKeys(getEPC);
				}
				
				//Clicks on save button
				driver.findElement(By.xpath("//*[@id=\"creation\"]/div[3]/div[2]/div[2]/div/div[2]/div[6]/button[2]")).click();
				Thread.sleep(1000);
				
				//Click on Continue button
				WebElement continuebtn = driver.findElement(By.xpath("//*[@id=\"add-demand-partner-countinue\"]"));
			  
				//JavascriptExecutor to click element
			      JavascriptExecutor jse = (JavascriptExecutor) driver;
			      jse.executeScript("arguments[0].click();", continuebtn);
			      Thread.sleep(1000);
			      
			      
			    //Gets the value of Allocation or Auction from excel  
				String allocation = sheetObj.getRow(r + 1).getCell(9).getStringCellValue();
				
				if(allocation.equals("Allocation"))
				{
					
					//Clicks on allocation toggle button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[2]/div/div/span[2]/small")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[4]/span")).click();
					
					//Clicks on Save&Submit button
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[5]/button[2]")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
					
				}
				else
				{
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[4]/div[5]/button[2]")).click();
					driver.findElement(By.xpath("//*[@id=\"creation\"]/div[2]/div")).click();
				}
				
			}
			
		}

		System.out.println("Adding Merchant is completed");
		*/
}}}

				
				
			
				


