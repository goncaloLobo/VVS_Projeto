package part2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static part2.DBSetupUtils.DB_PASSWORD;
import static part2.DBSetupUtils.DB_URL;
import static part2.DBSetupUtils.DB_USERNAME;
import static part2.DBSetupUtils.DELETE_ALL;
import static part2.DBSetupUtils.INSERT_CUSTOMER_SALE_DATA;
import static part2.DBSetupUtils.NUM_INIT_SALES;
import static part2.DBSetupUtils.NUM_INIT_SALES_DELIVERIES;
import static part2.DBSetupUtils.startApplicationDatabaseForTesting;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;
import webapp.services.CustomerService;
import webapp.services.SaleService;
import webapp.services.SalesDTO;
import webapp.services.SalesDeliveryDTO;

public class SalesDBTest {

	private static Destination dataSource;

	// the tracker is static because JUnit uses a separate Test instance for every
	// test method.
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

	@BeforeClass
	public static void setupClass() {
		startApplicationDatabaseForTesting();
		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	@Before
	public void setup() throws SQLException {

		Operation initDBOperations = Operations.sequenceOf(DELETE_ALL, INSERT_CUSTOMER_SALE_DATA);
		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);

		// Use the tracker to launch the DbSetup. This will speed-up tests
		// that do not not change the BD. Otherwise, just use dbSetup.launch();
		dbSetupTracker.launchIfNecessary(dbSetup);
	}

	/**
	 * Teste que após apagar um cliente existente verifica que as suas sales e as
	 * deliveries são eliminadas das base de dados
	 * 
	 * @throws ApplicationException
	 */
	// c)
	@Test
	public void deleteCustomerAndDeliveries() throws ApplicationException {
		List<CustomerDTO> listCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		// obter o primeiro customer de todos
		CustomerDTO customer = listCustomers.get(0);
		assertNotNull(customer);

		SalesDeliveryDTO salesDelivery = SaleService.INSTANCE.getSalesDeliveryByVat(customer.vat);
		// customer tem pelo menos uma sale delivery
		assertFalse(salesDelivery.sales_delivery.isEmpty());

		// remover o customer
		CustomerService.INSTANCE.removeCustomer(customer.vat);
		// remover as vendas do customer
		SaleService.INSTANCE.removeSale(customer.vat);
		// remover as sale deliveries
		SaleService.INSTANCE.removeSalesDelivery(customer.vat);

		// verificar que o customer foi removido
		List<CustomerDTO> listCustomersAfter = CustomerService.INSTANCE.getAllCustomers().customers;
		for (CustomerDTO customerDTO : listCustomersAfter) {
			assertNotEquals(customer.designation, customerDTO.designation);
		}

		// verificar que o customer não tem sales deliveries
		SalesDeliveryDTO salesDelDTO = SaleService.INSTANCE.getSalesDeliveryByVat(customer.vat);
		assertEquals(0, salesDelDTO.sales_delivery.size());

		// verificar que o customer não tem sales
		SalesDTO salesDTO = SaleService.INSTANCE.getSaleByCustomerVat(customer.vat);
		assertEquals(0, salesDTO.sales.size());
	}

	/**
	 * Teste que verifica que ao adicionar uma nova delivery verifica que o numero
	 * de deliveries aumentou mais um
	 * 
	 * @throws ApplicationException
	 */
	// e)
	@Test
	public void addSaleDelivery() throws ApplicationException {
		List<CustomerDTO> listCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		// obter o primeiro customer de todos
		CustomerDTO customer = listCustomers.get(0);
		int currentVat, numberSalesDelAfter = 0;
		assertNotNull(customer);

		// adicionar nova sale delivery
		SaleService.INSTANCE.addSaleDelivery(1, 100);

		// verificar que existe mais uma sale delivery de antes
		listCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		for (CustomerDTO customerDTO : listCustomers) {
			currentVat = customerDTO.vat;
			numberSalesDelAfter += SaleService.INSTANCE.getSalesDeliveryByVat(currentVat).sales_delivery.size();
		}
		assertEquals(numberSalesDelAfter, NUM_INIT_SALES_DELIVERIES + 1);

	}

	/**
	 * Teste extra que verifica que ao adicionar uma nova sale, o numero total de
	 * sales aumenta mais um
	 * 
	 * @throws ApplicationException
	 */
	@Test
	public void addSaleNumber() throws ApplicationException {
		int numeroVendasFinais;
		SalesDTO salesDTO = SaleService.INSTANCE.getAllSales();

		List<CustomerDTO> listCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		// obter o primeiro customer de todos
		CustomerDTO customer = listCustomers.get(0);
		assertNotNull(customer);

		// adicionar uma sale ao customer
		SaleService.INSTANCE.addSale(customer.vat);
		salesDTO = SaleService.INSTANCE.getAllSales();
		numeroVendasFinais = salesDTO.sales.size();

		assertEquals(numeroVendasFinais, NUM_INIT_SALES + 1);
	}

	/**
	 * Teste extra que tenta adicionar uma venda com um cliente inexistente, e lida
	 * com o erro produzido
	 * 
	 * @throws ApplicationException
	 */
	@Test
	public void addSaleVatInvalid() throws ApplicationException {
		int vat = 218802374;
		try {
			SaleService.INSTANCE.addSale(vat);
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid VAT number: " + vat);
		}
	}
}
