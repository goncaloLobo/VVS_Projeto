package part1;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class InsertFirstCustomerAgain {

	private static HtmlPage page;
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_11_webappdemo/";

	// array para os customers existentes, os seus npcs, designations e phonenumbers
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
	 * 
	 * @throws MalformedURLException
	 */
	@Before
	public void getFirstCustomer() throws MalformedURLException {
		int i = 0; // para andar pelos elementos de um customer
		int j = 0; // index para o tamanho do array customers
		HtmlAnchor removeCustomerLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) removeCustomerLink.openLinkInNewWindow();

		// obter a tabela com os customers
		final HtmlTable customersTable = (HtmlTable) nextPage.getByXPath("//table[@class='w3-table w3-bordered']")
				.toArray()[0];

		for (final HtmlTableRow row : customersTable.getRows()) {
			// this table has six columns, we need the 2nd and 3rd
			List<HtmlTableCell> customerInfo = row.getCells();
			if (customerInfo.get(0).asText().contains("Name"))
				continue; // skip header
			else if (j < customers.length) {
				customers[i + 1] = customerInfo.get(0).asText(); // Designation
				customers[i] = customerInfo.get(2).asText(); // VAT
				customers[i + 2] = customerInfo.get(1).asText(); // Phone
				j += 3;
			}
		}
	}

	/**
	 * Teste que re-insere o primeiro customer
	 * @throws IOException
	 */
	@Test
	public void insertCustomerAgain() throws IOException {
		String npc = customers[0];
		String designation = customers[1];
		String phoneNumber = customers[2];
		String erro = null;

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

		HtmlPage reportTextPage = null;

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "AddCustomerPageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.POST);

			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("vat", npc));
			requestSettings.getRequestParameters().add(new NameValuePair("designation", designation));
			requestSettings.getRequestParameters().add(new NameValuePair("phone", phoneNumber));
			reportTextPage = webClient.getPage(requestSettings);
		}

		// ir buscar o erro dentro da tag li
		List<DomElement> liElements = reportTextPage.getElementsByTagName("li");
		for (DomElement domElement : liElements) {
			erro = domElement.asText();
		}

		// verificar que o erro é igual ao esperado
		assertEquals("It was not possible to fulfill the request: Can't add customer with vat number " + npc + ".",
				erro);
	}
}
