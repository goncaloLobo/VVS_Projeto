package part2;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeFalse;
import static part2.DBSetupUtils.DB_PASSWORD;
import static part2.DBSetupUtils.DB_URL;
import static part2.DBSetupUtils.DB_USERNAME;
import static part2.DBSetupUtils.DELETE_ALL;
import static part2.DBSetupUtils.INSERT_CUSTOMER_ADDRESS_DATA;
import static part2.DBSetupUtils.NUM_INIT_CUSTOMERS;
import static part2.DBSetupUtils.NUM_INIT_SALES;
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
import webapp.services.CustomersDTO;
import webapp.services.SaleDTO;
import webapp.services.SaleDeliveryDTO;
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

		Operation initDBOperations = Operations.sequenceOf(DELETE_ALL, INSERT_CUSTOMER_ADDRESS_DATA);

		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);

		// Use the tracker to launch the DbSetup. This will speed-up tests
		// that do not not change the BD. Otherwise, just use dbSetup.launch();
		dbSetupTracker.launchIfNecessary(dbSetup);

	}

	@Test
	public void deleteCustomerAndDeliveries() throws ApplicationException {
		CustomerDTO customer = CustomerService.INSTANCE.getCustomerByVat(197672337);
		assertNotNull(customer);

		SalesDeliveryDTO salesDelivery = SaleService.INSTANCE.getSalesDeliveryByVat(customer.vat);
		assertFalse(salesDelivery.sales_delivery.isEmpty());

		CustomerService.INSTANCE.removeCustomer(customer.vat);
		// SaleService.INSTANCE.removeSalesDelivery(customer.vat);
	}

	@Test
	public void querySalesNumberTest() throws ApplicationException {
		// read-only test: unnecessary to re-launch setup after test has been run
		// dbSetupTracker.skipNextLaunch();

		int expected = NUM_INIT_SALES;
		int actual = SaleService.INSTANCE.getAllSales().sales.size();

		assertEquals(expected, actual);
	}

	@Test
	public void addSalesSizeTest() throws ApplicationException {

		SaleService.INSTANCE.addSale(218802374);
		int size = SaleService.INSTANCE.getAllSales().sales.size();

		assertEquals(NUM_INIT_SALES + 1, size);
	}

	private boolean hasSale(int id) throws ApplicationException {
		SalesDTO salesDTO = SaleService.INSTANCE.getAllSales();

		for (SaleDTO sale : salesDTO.sales)
			if (sale.id == id)
				return true;
		return false;
	}

	@Test
	public void addSaleTest() throws ApplicationException {

		assumeFalse(hasSale(3));
		SaleService.INSTANCE.addSale(218802374);
		assertTrue(hasSale(3));
	}

	// e)
	@Test
	public void addSaleDelivery() throws ApplicationException {
		int vat = 197672337;
		int vats, numberSalesDelBefore = 0, numberSalesDelAfter = 0;
		CustomerDTO customer = CustomerService.INSTANCE.getCustomerByVat(vat);
		assertNotNull(customer);

		List<CustomerDTO> listCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		for (CustomerDTO customerDTO : listCustomers) {
			vats = customerDTO.vat;
			numberSalesDelBefore += SaleService.INSTANCE.getSalesDeliveryByVat(vats).sales_delivery.size();
		}

		// adicionar nova sale delivery
		SaleService.INSTANCE.addSaleDelivery(1, 100);

		listCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		for (CustomerDTO customerDTO : listCustomers) {
			vats = customerDTO.vat;
			numberSalesDelAfter += SaleService.INSTANCE.getSalesDeliveryByVat(vats).sales_delivery.size();
		}

		assertEquals(numberSalesDelAfter, numberSalesDelBefore + 1);

	}

}
