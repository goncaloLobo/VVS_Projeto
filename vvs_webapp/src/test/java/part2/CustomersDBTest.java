package part2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static part2.DBSetupUtils.DB_PASSWORD;
import static part2.DBSetupUtils.DB_URL;
import static part2.DBSetupUtils.DB_USERNAME;
import static part2.DBSetupUtils.DELETE_ALL;
import static part2.DBSetupUtils.INSERT_CUSTOMER_ADDRESS_DATA;
import static part2.DBSetupUtils.NUM_INIT_CUSTOMERS;
import static part2.DBSetupUtils.startApplicationDatabaseForTesting;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

public class CustomersDBTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

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
	public void queryCustomerNumberTest() throws ApplicationException {
		// read-only test: unnecessary to re-launch setup after test has been run
		dbSetupTracker.skipNextLaunch();

		int expected = NUM_INIT_CUSTOMERS;
		int actual = CustomerService.INSTANCE.getAllCustomers().customers.size();

		assertEquals(expected, actual);
	}

	// a)
	@Test
	public void checkCustomerUpdate() throws ApplicationException {
		int npc = 197672337;
		int phone = 969149742;
		CustomerDTO customer = CustomerService.INSTANCE.getCustomerByVat(npc);
		CustomerService.INSTANCE.updateCustomerPhone(customer.vat, phone);
		CustomerDTO otherCustomer = CustomerService.INSTANCE.getCustomerByVat(customer.vat);

		assertNotEquals(customer.phoneNumber, otherCustomer.phoneNumber);
		assertEquals(otherCustomer.phoneNumber, phone);
	}

	// b)
	@Test
	public void deleteAllButOneCustomer() throws ApplicationException {
		List<CustomerDTO> listCustomers = CustomerService.INSTANCE.getAllCustomers().customers;
		int size = listCustomers.size();
		int vat = listCustomers.get(0).vat;
		int phone = listCustomers.get(0).phoneNumber;

		for (int i = 0; i < size - 1; i++) {
			CustomerService.INSTANCE.removeCustomer(listCustomers.get(i).vat);
		}
		List<CustomerDTO> listCustomersAfter = CustomerService.INSTANCE.getAllCustomers().customers;
		int sizeAfter = listCustomersAfter.size();

		assertEquals(1, sizeAfter);
		assertEquals(197672337, vat);
		assertEquals(914276732, phone);
	}

	// d)
	@Test
	public void addDeletedCustomer() throws ApplicationException {
		int npc = 197672337;
		CustomerDTO customer = CustomerService.INSTANCE.getCustomerByVat(npc);
		assertNotNull(customer);

		CustomerService.INSTANCE.removeCustomer(npc);
		SaleService.INSTANCE.removeSale(npc);
		CustomerService.INSTANCE.addCustomer(customer.vat, customer.designation, customer.phoneNumber);

		CustomerDTO sameCustomer = CustomerService.INSTANCE.getCustomerByVat(customer.vat);
		assertNotNull(sameCustomer);

	}
}
