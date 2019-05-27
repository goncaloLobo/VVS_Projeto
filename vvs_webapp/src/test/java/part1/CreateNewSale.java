package part1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

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
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class CreateNewSale {

	private String npc = "197672337";
	private int idNovaSale; // id da nova sale a ser introduzida
	private int idLastSale; // id da última sale
	private int idNovaSaleDelivery; // id da nova sale delivery a ser introduzida
	private int idLastSaleDelivery; // id da última sale delivery

	private static HtmlPage page;
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_11_webappdemo/";

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
	 * Obtém o id da última venda do cliente antes de uma nova venda
	 * 
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException
	 */
	@Before
	public void getLastSaleId() throws FailingHttpStatusCodeException, IOException {
		HtmlPage reportPage;
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "GetSalePageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			// Set the request parameters
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("customerVat", npc));

			reportPage = webClient.getPage(requestSettings);
			assertEquals(HttpMethod.GET, reportPage.getWebResponse().getWebRequest().getHttpMethod());
		}

		// verificar que está lá o customer
		assertTrue(reportPage.asXml().contains(npc));

		// obter o id da última venda do cliente antes de uma nova venda
		// para dps comparar esse id com o id de uma nova venda, e mostrar
		// que uma nova venda foi feita
		final HtmlTable salesTable = (HtmlTable) reportPage.getByXPath("//table[@class='w3-table w3-bordered']")
				.toArray()[0];
		idLastSale = Integer.parseInt(salesTable.getElementsByTagName("tr").get(salesTable.getRowCount() - 1)
				.getElementsByTagName("td").get(0).asText());
	}

	/**
	 * Método que adiciona uma nova sale do cliente
	 * 
	 * @throws IOException
	 */
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

		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();
		assertTrue(textReportPage.contains(npc));

		// verifica se a sale criada existe
		checkNewSaleId();
	}

	/**
	 * Verifica que a nova sale foi criada, ou seja, que o id da nova sale é
	 * diferente do id da sale anterior
	 * 
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException
	 */
	public void checkNewSaleId() throws FailingHttpStatusCodeException, IOException {
		HtmlPage reportPageAux;
		String status = null;

		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "GetSalePageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("customerVat", npc));

			reportPageAux = webClient.getPage(requestSettings);
			assertEquals(HttpMethod.GET, reportPageAux.getWebResponse().getWebRequest().getHttpMethod());
		}

		// verificar que o npc está na tabela
		assertTrue(reportPageAux.asXml().contains(npc));

		// obtem a tabela das sales
		final HtmlTable salesTable = (HtmlTable) reportPageAux.getByXPath("//table[@class='w3-table w3-bordered']")
				.toArray()[0];

		idNovaSale = Integer.parseInt(salesTable.getElementsByTagName("tr").get(salesTable.getRowCount() - 1)
				.getElementsByTagName("td").get(0).asText());
		status = salesTable.getElementsByTagName("tr").get(salesTable.getRowCount() - 1).getElementsByTagName("td")
				.get(3).asText();

		// verifica que a sale está aberta
		assertEquals("O", status);
		// verifica que o id da nova venda não é o id
		assertNotEquals(idLastSale, idNovaSale);
	}

	/**
	 * Teste que cria uma nova sale delivery
	 * @throws IOException
	 */
	@Test
	public void createNewSaleDelivery() throws IOException {
		// obter o id da ultima sale delivery, para dps verificar que a nova
		// sale delivery é +1 da anterior
		getLastSaleDeliveryId();

		// get a specific link
		HtmlAnchor insertNewDeliveryForm = page.getAnchorByHref("saleDeliveryVat.html");

		// click on it
		HtmlPage nextPage = (HtmlPage) insertNewDeliveryForm.openLinkInNewWindow();

		// check if title is the one expected
		assertEquals("Enter Name", nextPage.getTitleText());

		// get the page first form:
		HtmlForm insertNewSaleForm = nextPage.getForms().get(0);

		// place data at form
		HtmlInput vatInput = insertNewSaleForm.getInputByName("vat");
		vatInput.setValueAttribute(npc);

		// submit form
		HtmlInput submit = insertNewSaleForm.getInputByValue("Get Customer");

		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();
		// verificar que contem lá o npc do cliente
		assertTrue(textReportPage.contains(npc));

		// verificar se inseriu a address e se o número de colunas aumentou
		HtmlPage reportPageAux;
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "AddSaleDeliveryPageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			// Set the request parameters
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("vat", npc));

			reportPageAux = webClient.getPage(requestSettings);
			assertEquals(HttpMethod.GET, reportPageAux.getWebResponse().getWebRequest().getHttpMethod());
		}

		String addressId = "1";
		String saleId = "1";

		// get the page first form:
		HtmlForm insertNewDeliveryForm2 = reportPageAux.getForms().get(0);

		// place data at form
		HtmlInput addressInput = insertNewDeliveryForm2.getInputByName("addr_id");
		addressInput.setValueAttribute(addressId);
		HtmlInput saleInput = insertNewDeliveryForm2.getInputByName("sale_id");
		saleInput.setValueAttribute(saleId);

		// submit form
		HtmlInput submitNewDelivery = insertNewDeliveryForm2.getInputByValue("Insert");

		HtmlPage reportPage2 = submitNewDelivery.click();
		String textReportPage2 = reportPage2.asText();
		// verificar que contem lá o id da morada e o id da venda
		assertTrue(textReportPage2.contains(addressId));
		assertTrue(textReportPage2.contains(saleId));

		// verifica se a delivery criada existe
		checkNewDeliveryId();
	}

	/**
	 * Obtem o id da ultima sale delivery
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException
	 */
	public void getLastSaleDeliveryId() throws FailingHttpStatusCodeException, IOException {
		// get a specific link
		HtmlAnchor showDeliveryForm = page.getAnchorByHref("showDelivery.html");

		// click on it
		HtmlPage nextPage = (HtmlPage) showDeliveryForm.openLinkInNewWindow();

		// check if title is the one expected
		assertEquals("Enter Name", nextPage.getTitleText());

		// get the page first form:
		HtmlForm insertNewSaleForm = nextPage.getForms().get(0);

		// place data at form
		HtmlInput vatInput = insertNewSaleForm.getInputByName("vat");
		vatInput.setValueAttribute(npc);

		// submit form
		HtmlInput submit = insertNewSaleForm.getInputByValue("Get Customer");

		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();
		// verificar que contem lá o npc do cliente
		assertTrue(textReportPage.contains(npc));

		HtmlPage anoterReportPage;
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "GetSaleDeliveryPageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			// Set the request parameters
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("vat", npc));

			anoterReportPage = webClient.getPage(requestSettings);
			assertEquals(HttpMethod.GET, anoterReportPage.getWebResponse().getWebRequest().getHttpMethod());
		}

		// obter o id da última venda do cliente antes de uma nova venda
		// para dps comparar esse id com o id de uma nova venda, e mostrar
		// que uma nova venda foi feita
		final HtmlTable salesTable = (HtmlTable) anoterReportPage.getByXPath("//table[@class='w3-table w3-bordered']")
				.toArray()[0];
		idLastSaleDelivery = Integer.parseInt(salesTable.getElementsByTagName("tr").get(salesTable.getRowCount() - 1)
				.getElementsByTagName("td").get(0).asText());
	}

	/**
	 * Método que obtem o id da nova sale delivery
	 * 
	 * @throws FailingHttpStatusCodeException
	 * @throws IOException
	 */
	public void checkNewDeliveryId() throws FailingHttpStatusCodeException, IOException {
		// get a specific link
		HtmlAnchor showDeliveryForm = page.getAnchorByHref("showDelivery.html");

		// click on it
		HtmlPage nextPage = (HtmlPage) showDeliveryForm.openLinkInNewWindow();

		// check if title is the one expected
		assertEquals("Enter Name", nextPage.getTitleText());

		// get the page first form:
		HtmlForm insertNewSaleForm = nextPage.getForms().get(0);

		// place data at form
		HtmlInput vatInput = insertNewSaleForm.getInputByName("vat");
		vatInput.setValueAttribute(npc);

		// submit form
		HtmlInput submit = insertNewSaleForm.getInputByValue("Get Customer");

		HtmlPage reportPage = submit.click();
		String textReportPage = reportPage.asText();
		// verificar que contem lá o npc do cliente
		assertTrue(textReportPage.contains(npc));

		// verificar se inseriu a address e se o número de colunas aumentou
		HtmlPage reportPageAux;
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
			java.net.URL url = new java.net.URL(APPLICATION_URL + "GetSaleDeliveryPageController");
			WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);

			// Set the request parameters
			requestSettings.setRequestParameters(new ArrayList<NameValuePair>());
			requestSettings.getRequestParameters().add(new NameValuePair("vat", npc));

			reportPageAux = webClient.getPage(requestSettings);
			assertEquals(HttpMethod.GET, reportPageAux.getWebResponse().getWebRequest().getHttpMethod());
		}

		// obtem a tabela das sale deliveries
		final HtmlTable saleDeliveryTable = (HtmlTable) reportPageAux
				.getByXPath("//table[@class='w3-table w3-bordered']").toArray()[0];
		idNovaSaleDelivery = Integer.parseInt(saleDeliveryTable.getElementsByTagName("tr")
				.get(saleDeliveryTable.getRowCount() - 1).getElementsByTagName("td").get(0).asText());
		assertEquals("ids delivery", idNovaSaleDelivery, idLastSaleDelivery + 1);
	}
}