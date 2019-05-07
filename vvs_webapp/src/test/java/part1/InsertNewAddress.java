package part1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class InsertNewAddress {

	private final String NPC = "218802374";
	private final String ADDRESS = "RUA";
	private final String DOOR = "15";
	private final String POSTALCODE = "2800-081";
	private final String LOCALITY = "Almada";

	private static HtmlPage page;
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";

	private static final int nRows = 0;
	private static final int nRowsAfter = 0;

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
	public void insertNewAddressTest() throws IOException {
		// get a specific link
		HtmlAnchor insertNewAddress = page.getAnchorByHref("addAddressToCustomer.html");
		// click on it
		HtmlPage nextPage = (HtmlPage) insertNewAddress.openLinkInNewWindow();
		// check if title is the one expected
		assertEquals("Enter Address", nextPage.getTitleText());

		// get the first page form:
		HtmlForm insertAddressForm = nextPage.getForms().get(0);

		// place data at form
		HtmlInput vatInput = insertAddressForm.getInputByName("vat");
		vatInput.setValueAttribute(NPC);

		HtmlInput addressInput = insertAddressForm.getInputByName("address");
		addressInput.setValueAttribute(ADDRESS);

		HtmlInput doorInput = insertAddressForm.getInputByName("door");
		doorInput.setValueAttribute(DOOR);

		HtmlInput postalCodeInput = insertAddressForm.getInputByName("postalcode");
		postalCodeInput.setValueAttribute(POSTALCODE);

		HtmlInput localityInput = insertAddressForm.getInputByName("locality");
		localityInput.setValueAttribute(LOCALITY);

		// submit form
		HtmlInput submit = insertAddressForm.getInputByName("submit");

		// check if report page includes the proper values
		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();
		assertTrue(textReportPage.contains(NPC));
		assertTrue(textReportPage.contains(ADDRESS));
		assertTrue(textReportPage.contains(DOOR));
		assertTrue(textReportPage.contains(POSTALCODE));
		assertTrue(textReportPage.contains(LOCALITY));

		// verificar se inseriu a address e se o número de colunas aumentou
		// Build a GET request
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "GetCustomerPageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			// Set the request parameters
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("vat", NPC));
			requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

			reportPage = webClient.getPage(requestSettings);
		}

		assertTrue(reportPage.asXml().contains(NPC));
		assertTrue(reportPage.asXml().contains(ADDRESS));
		assertTrue(reportPage.asXml().contains(DOOR));
		assertTrue(reportPage.asXml().contains(POSTALCODE));
		assertTrue(reportPage.asXml().contains(LOCALITY));
	}
}
