package part1;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class InsertFirstCustomerAgain {

	private static HtmlPage page;
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";

	private String customers[] = new String[9];

	@BeforeClass
	public static void setUpClass() throws Exception {
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {

			// possible configurations needed to prevent JUnit tests to fail for complex
			// HTML pages
			webClient.setJavaScriptTimeout(15000);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setCssEnabled(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setThrowExceptionOnScriptError(false);

			page = webClient.getPage(APPLICATION_URL);
			assertEquals(200, page.getWebResponse().getStatusCode()); // OK status
		}
	}

	/**
	 * Obtem o primeiro cliente
	 * @throws MalformedURLException
	 */
	@Before
	public void getFirstCustomer() throws MalformedURLException {
		int i = 0; // para andar pelos elementos de um customer
		int j = 0; // index para o tamanho do array customers
		HtmlAnchor removeCustomerLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) removeCustomerLink.openLinkInNewWindow();

		final HtmlTable customersTable = (HtmlTable) nextPage.getByXPath("//table[@class='w3-table w3-bordered']")
				.toArray()[0];

		for (final HtmlTableRow row : customersTable.getRows()) {
			// this table has six columns, we need the 2nd and 3rd
			List<HtmlTableCell> customerInfo = row.getCells();
			if (customerInfo.get(0).asText().contains("Name"))
				continue; // skip header
			else if (j < customers.length) {
				customers[i + 1] = customerInfo.get(0).asText(); // Nome
				customers[i] = customerInfo.get(2).asText(); // VAT
				customers[i + 2] = customerInfo.get(1).asText(); // Phone
				j += 3;
			}
		}
	}

	@Test
	public void insertCustomerAgain() throws IOException {
		String npc = customers[0];
		String designation = customers[1];
		String phoneNumber = customers[2];

		// get a specific link
		HtmlAnchor insertNewCustomerLink = page.getAnchorByHref("addCustomer.html");

		// click on it
		HtmlPage nextPage = (HtmlPage) insertNewCustomerLink.openLinkInNewWindow();

		// check if title is the one expected
		assertEquals("Enter Name", nextPage.getTitleText());

		// get the page first form:
		HtmlForm insertNewCustomerForm = nextPage.getForms().get(0);

		// place data at form
		HtmlInput vatInput = insertNewCustomerForm.getInputByName("vat");
		vatInput.setValueAttribute(npc);

		HtmlInput nameInput = insertNewCustomerForm.getInputByName("designation");
		nameInput.setValueAttribute(designation);

		HtmlInput phoneNumberInput = insertNewCustomerForm.getInputByName("phone");
		phoneNumberInput.setValueAttribute(phoneNumber);

		// submit form
		HtmlInput submit = insertNewCustomerForm.getInputByValue("Get Customer");

		// check if report page includes the proper values
		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();

		// LIDAR COM A FALHA???
		
		
	}
}
