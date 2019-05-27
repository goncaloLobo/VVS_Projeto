package part1;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class InsertNewAddress {

	// dados da nova morada
	private final String NPC = "197672337";
	private final String ADDRESS = "RUA NUMERO 2";
	private final String DOOR = "15";
	private final String POSTALCODE = "2800-081";
	private final String LOCALITY = "Almada";

	private static HtmlPage page;
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_11_webappdemo/";

	private int nRows = 0;
	private int nRowsAfter = 0;

	private HtmlTable addressTable;

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
	 * Obtem numero de linhas iniciais na tabela
	 * 
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException
	 */
	@Before
	public void getNumberRows() throws MalformedURLException, FailingHttpStatusCodeException, IOException {
		HtmlPage reportPage;

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "GetCustomerPageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			// Set the request parameters
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			System.out.println("NPC: " + NPC);
			requestSettings.getRequestParameters().add(new NameValuePair("vat", NPC));
			requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

			reportPage = webClient.getPage(requestSettings);
			assertEquals(HttpMethod.GET, reportPage.getWebResponse().getWebRequest().getHttpMethod());
		} // try

		try {
			// obtem a tabela de addresses
			addressTable = (HtmlTable) reportPage.getByXPath("//table[@class='w3-table w3-bordered']").toArray()[0];
		} catch (Exception e) {
			// se não for possível é porque a tabela não tem nenhuma linha
			nRows = 0;
		}
		if (addressTable != null) {
			// se for possível vai obter o número de linhas
			List<HtmlTableRow> list = addressTable.getRows();
			nRows = list.size();
		}
	}

	/**
	 * Teste que insere uma nova morada para um customer existente
	 * 
	 * @throws IOException
	 */
	@Test
	public void insertNewAddressTest() throws IOException {
		// get a specific link
		HtmlAnchor insertNewAddressLink = page.getAnchorByHref("addAddressToCustomer.html");

		// click on it
		HtmlPage nextPage = (HtmlPage) insertNewAddressLink.openLinkInNewWindow();

		// check if title is the one expected
		assertEquals("Enter Address", nextPage.getTitleText());

		// get the page first form:
		HtmlForm insertAddressForm = nextPage.getForms().get(0);

		// place data at form
		HtmlInput vatInput = insertAddressForm.getInputByName("vat");
		vatInput.setValueAttribute(NPC);

		HtmlInput addressInput = insertAddressForm.getInputByName("address");
		addressInput.setValueAttribute(ADDRESS);

		HtmlInput doorInput = insertAddressForm.getInputByName("door");
		doorInput.setValueAttribute(DOOR);

		HtmlInput postalCodeInput = insertAddressForm.getInputByName("postalCode");
		postalCodeInput.setValueAttribute(POSTALCODE);

		HtmlInput localityInput = insertAddressForm.getInputByName("locality");
		localityInput.setValueAttribute(LOCALITY);

		// submit form
		HtmlInput submit = insertAddressForm.getInputByValue("Insert");

		// check if report page includes the proper values
		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();
		assertTrue(textReportPage.contains(NPC));
		assertTrue(textReportPage.contains(ADDRESS));
		assertTrue(textReportPage.contains(DOOR));
		assertTrue(textReportPage.contains(POSTALCODE));
		assertTrue(textReportPage.contains(LOCALITY));

		// verificar se inseriu a address e se o número de colunas aumentou
		HtmlPage reportPageAux;
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "GetCustomerPageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			// Set the request parameters
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("vat", NPC));
			requestSettings.getRequestParameters().add(new NameValuePair("submit", "Get+Customer"));

			reportPageAux = webClient.getPage(requestSettings);
			assertEquals(HttpMethod.GET, reportPageAux.getWebResponse().getWebRequest().getHttpMethod());
		}

		// verificar que os dados foram inseridos
		assertTrue(reportPageAux.asXml().contains(ADDRESS));
		assertTrue(reportPageAux.asXml().contains(DOOR));
		assertTrue(reportPageAux.asXml().contains(POSTALCODE));
		assertTrue(reportPageAux.asXml().contains(LOCALITY));

		// obter a tabela de addresses
		final HtmlTable addressTable = (HtmlTable) reportPageAux.getByXPath("//table[@class='w3-table w3-bordered']")
				.toArray()[0];
		List<HtmlTableRow> list = addressTable.getRows();
		nRowsAfter = list.size();
		// verificar que o numero de linhas anterior é -1 que o número de linhas atual
		assertEquals("numero de linhas", nRows - 1, nRowsAfter - 2);
	}
}
