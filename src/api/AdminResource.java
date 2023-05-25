package api;

import java.util.Collection;
import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {
	private static final AdminResource ADMINRESOURCE = new AdminResource();
	private final CustomerService customerService = CustomerService.getCusService();
	private final ReservationService reservationService = ReservationService.getReservationService();

	public static AdminResource getAdminResource() {
		return ADMINRESOURCE;
	}

	public Customer getCustomer(String email) {
		return customerService.getCustomer(email);
	}

	public void addRoom(Collection<IRoom> rooms) {
		for (IRoom r : rooms) {
			reservationService.addRoom(r);
		}
	}

	public Collection<IRoom> getAllRooms() {
		return reservationService.getRooms();
	}

	public Collection<Customer> getAllCustomers() {
		return customerService.getAllCustomer();
	}

	public void displayAllReservations() {
		reservationService.printAllReservation();
	}

}
