package service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import model.Customer;
import model.IRoom;
import model.Reservation;

public class ReservationService {
	private static final ReservationService RESERVATIONSERVICE = new ReservationService();

	private final Map<String, IRoom> rooms = new HashMap<>();
    private final Set<Reservation> reservations = new HashSet<>();

	public static ReservationService getReservationService() {
		return RESERVATIONSERVICE;
	}

	public Collection<IRoom> getRooms() {
		return rooms.values();
	}

	public void addRoom(IRoom room) {
		rooms.put(room.getRoomNumber(),room);
	}

	public IRoom getARoom(String roomId) {
		return rooms.get(roomId);
	}

	public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
		
		if (room.isFree()&&checkDate(room, checkInDate, checkOutDate)) {
			Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
			reservations.add(reservation);

			return reservation;
		} else
			return null;
	}

	public boolean checkDate(IRoom room, Date checkInDate, Date checkOutDate) {
		for (Reservation r : reservations) {
			if (r.getRoom().equals(room) && checkInDate.before(r.getCheckOutDate())
					&& checkOutDate.after(r.getCheckInDate()))
				return false;
		}
		return true;
	}

	public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
		if (reservations.isEmpty())
			return rooms.values();
		Set<IRoom> listRooms = new HashSet<IRoom>();
		for (IRoom r : rooms.values()) {
			if (r.isFree() && checkDate(r, checkInDate, checkOutDate))
				listRooms.add(r);
		}
		return listRooms;
	}

	public Collection<Reservation> getCustomersReservation(Customer customer) {
		List<Reservation> listCustomers = new LinkedList<Reservation>();
		for (Reservation r : reservations) {
			if (r.getCustomer().equals(customer))
				listCustomers.add(r);
		}
		return listCustomers;
	}

	public void printAllReservation() {
		if ((reservations == null) || (reservations.isEmpty()))
			System.out.println("no reservations found\n");
		else {
			for (Reservation r : reservations) {
				System.out.println(r);
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(reservations, rooms);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservationService other = (ReservationService) obj;
		return Objects.equals(reservations, other.reservations) && Objects.equals(rooms, other.rooms);
	}
}
