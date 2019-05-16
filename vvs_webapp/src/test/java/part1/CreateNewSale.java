package part1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class CreateNewSale {

	private String npc = "218802374";
	private String designation = "Gonçalo";
	private String phoneNumber = "969149742";
	private int idVenda;

	private static HtmlPage page;
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";

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
	public void createNewSale() throws IOException {
		// get a specific link
		HtmlAnchor insertNewSale = page.getAnchorByHref("addSale.html");

		// click on it
		HtmlPage nextPage = (HtmlPage) insertNewSale.openLinkInNewWindow();

		// check if title is the one expected
		assertEquals("New Sale", nextPage.getTitleText());

		// get the page first form:
		HtmlForm insertNewSaleForm = nextPage.getForms().get(0);

		// place data at form
		HtmlInput vatInput = insertNewSaleForm.getInputByName("customerVat");
		vatInput.setValueAttribute(npc);

		// submit form
		HtmlInput submit = insertNewSaleForm.getInputByValue("Add Sale");

		// check if report page includes the proper values
		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();
		assertTrue(textReportPage.contains(npc));

		listAllSales();
	}

	public void listAllSales() throws FailingHttpStatusCodeException, IOException {

		HtmlPage reportPageAux;
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "GetSalePageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("customerVat", npc));

			reportPageAux = webClient.getPage(requestSettings);
			assertEquals(HttpMethod.GET, reportPageAux.getWebResponse().getWebRequest().getHttpMethod());
		}

		// checks if NPC is correct
		assertTrue(reportPageAux.asXml().contains(npc));

		final HtmlTable salesTable = (HtmlTable) reportPageAux.getByXPath("//table[@class='w3-table w3-bordered']")
				.toArray()[0];

		for (final HtmlTableRow row : salesTable.getRows()) {
			// this table has six columns, we need the 2nd and 3rd
			List<HtmlTableCell> saleInfo = row.getCells();
			if (saleInfo.get(0).asText().contains("Name"))
				continue; // skip header
			else if (saleInfo.get(4).asText().equals(npc)) {
				// 0 - id
				// 1 - data
				// 2 - total
				// 3 - status
				// 4 - customer vat number
				idVenda = Integer.parseInt(saleInfo.get(0).asText());
				
			}
		}
	}

	@Test
	public void createNewSaleDelivery() throws IOException {
		// get a specific link
		HtmlAnchor insertNewSaleDelivery = page.getAnchorByHref("saleDeliveryVat.html");

		// click on it
		HtmlPage nextPage = (HtmlPage) insertNewSaleDelivery.openLinkInNewWindow();

		// check if title is the one expected
		assertEquals("Enter Name", nextPage.getTitleText());

		// get the page first form:
		HtmlForm insertNewSaleDeliveryForm = nextPage.getForms().get(0);

		// place data at form
		HtmlInput vatInput = insertNewSaleDeliveryForm.getInputByName("vat");
		vatInput.setValueAttribute(npc);

		// submit form
		HtmlInput submit = insertNewSaleDeliveryForm.getInputByValue("Add Sale");

		// check if report page includes the proper values
		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();
		assertTrue(textReportPage.contains(npc));

	}
}
