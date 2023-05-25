package api;

import java.util.Collection;
import java.util.Date;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

public class HotelResource {
	private static final HotelResource HOTEL = new HotelResource();
	private final CustomerService customerService = CustomerService.getCusService();
	private final ReservationService reservationService = ReservationService.getReservationService();

	public static HotelResource getHotelResource() {
		return HOTEL;
	}

	public Customer getCustomer(String mail) {
		return customerService.getCustomer(mail);
	}

	public void createACustomer(String email, String firstName, String lastName) {
		customerService.addCustomer(firstName, lastName, email);
	}

	public IRoom getRoom(String RoomNamber) {
		return reservationService.getARoom(RoomNamber);
	}

	public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
		return reservationService.reserveARoom(customerService.getCustomer(customerEmail), room, checkInDate,
				checkOutDate);
	}

	public Collection<Reservation> getCustomersReservations(String CustomerEmail) {
		return reservationService.getCustomersReservation(customerService.getCustomer(CustomerEmail));
	}

	public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
		return reservationService.findRooms(checkIn, checkOut);
	}

}
