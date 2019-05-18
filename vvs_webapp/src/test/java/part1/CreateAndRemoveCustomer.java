package part1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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

public class CreateAndRemoveCustomer {

	private static HtmlPage page;
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";

	private String customers[] = { "197672337", "JOSE FARIA", "914276732", "168027852", "LUIS SANTOS", "964294317",
			"218802374", "Gonçalo", "969149742" };

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

	@Test
	public void insertNewCustomer() throws IOException {
		String npc = "123456789";
		String designation = "Duarte";
		String phoneNumber = "967775147";

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
		assertTrue(textReportPage.contains(npc));
		assertTrue(textReportPage.contains(designation));
		assertTrue(textReportPage.contains(phoneNumber));

		// Remover o cliente criado em cima
		HtmlAnchor removeCustomerLink = page.getAnchorByHref("RemoveCustomerPageController");
		nextPage = (HtmlPage) removeCustomerLink.openLinkInNewWindow();
		assertTrue(nextPage.asText().contains(npc));

		HtmlForm removeCustomerForm = nextPage.getForms().get(0);
		vatInput = removeCustomerForm.getInputByName("vat");
		vatInput.setValueAttribute(npc);
		submit = removeCustomerForm.getInputByName("submit");
		submit.click();

		// now check that the new client was erased
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertFalse(nextPage.asText().contains(npc));
		listAllClients();
	}

	/**
	 * Método que obtem todos os clientes da tabela de clientes
	 * 
	 * @throws MalformedURLException
	 */
	public void listAllClients() throws MalformedURLException {
		int i = 0; // para andar pelos elementos de um customer
		HtmlAnchor removeCustomerLink = page.getAnchorByHref("GetAllCustomersPageController");
		HtmlPage nextPage = (HtmlPage) removeCustomerLink.openLinkInNewWindow();

		final HtmlTable customersTable = (HtmlTable) nextPage.getByXPath("//table[@class='w3-table w3-bordered']")
				.toArray()[0];

		for (final HtmlTableRow row : customersTable.getRows()) {
			// this table has six columns, we need the 2nd and 3rd
			List<HtmlTableCell> customerInfo = row.getCells();
			if (customerInfo.get(0).asText().contains("Name"))
				continue; // skip header
			else if (customerInfo.get(0).asText().toString().equals(customers[i + 1])) {
				assertEquals(customers[i + 1], customerInfo.get(0).asText());
				assertEquals(customers[i], customerInfo.get(2).asText());
				assertEquals(customers[i + 2], customerInfo.get(1).asText());
				i += 3;
			}
		}

	}
}
