package api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import model.IRoom;
import model.Reservation;

public class MainMenu {

	private static final HotelResource hotelResource = HotelResource.getHotelResource();

	public static void main(String[] args) {
		System.out.println("Welcome to the Hotel reservation Application\n\n");
		printMainMenu();
		chooseANumber();
	}

	public static void chooseANumber() {

		String line = "";
		Scanner scanner = new Scanner(System.in);

		int number = 0;
		do {
			try {
				line = scanner.nextLine();
				number = Integer.parseInt(line);

				switch (number) {
				case 1:
					findAndReserveRoom();
					break;
				case 2:
					seeMyReservation();
					break;
				case 3:
					createAccount();
					break;
				case 4:
					AdminMenu.adminMenu();
					break;
				case 5:
					System.out.println("Exit");
					break;
				default:
					System.out.println("Please give a number between 1 and 5 \n");
					chooseANumber();
					break;
				}

			} catch (IllegalArgumentException ex) {
				System.out.println("Please give a valide Input\n");
				chooseANumber();
			}

		} while ((number < 1) && (number > 5));

		scanner.close();

	}

	private static void findAndReserveRoom() {
		final Scanner scanner = new Scanner(System.in);

		System.out.println("Enter Check-In Date mm/dd/yyyy");
		Date checkIn = getInputDate(scanner);

		System.out.println("Enter Check-Out Date mm/dd/yyyy");
		Date checkOut = getInputDate(scanner);

		if (checkIn != null && checkOut != null) {
			Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

			if (availableRooms.isEmpty()) {
				Collection<IRoom> availableAlternativeRooms = hotelResource.findARoom(plus10Days(checkIn),
						plus10Days(checkOut));
				if (availableAlternativeRooms.isEmpty()) {
					System.out.println("No rooms found.");
					printMainMenu();
					chooseANumber();
				} else {
					System.out.println("No rooms found in these dates, what about these rooms after 10 days?\n");
					printRooms(availableAlternativeRooms);

					reserveRoom(plus10Days(checkIn), plus10Days(checkOut), availableRooms);
				}

			} else {

				printRooms(availableRooms);

				reserveRoom(checkIn, checkOut, availableRooms);
			}
		}
	}

	public static Date plus10Days(Date date) {
		Calendar plus10 = Calendar.getInstance();
		plus10.setTime(date);
		plus10.add(Calendar.DATE, 10);
		return plus10.getTime();
	}

	private static Date getInputDate(final Scanner scanner) {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(scanner.nextLine());
		} catch (ParseException ex) {
			System.out.println("Error: Invalid date.");
			findAndReserveRoom();
		}
		return null;
	}

	private static void reserveRoom(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {

		Scanner scanner = new Scanner(System.in);

		String anser;
		do {
			System.out.println("Would you like to book? y/n");
			anser = scanner.nextLine();
		} while (!(("y".equals(anser)) || ("n".equals(anser))));
		if ("y".equals(anser)) {

			System.out.println("Enter Email format: name@domain.com");
			String customerEmail = scanner.nextLine();

			if (hotelResource.getCustomer(customerEmail) == null) {
				System.out.println("You need to create a new account./n");
				printMainMenu();
				chooseANumber();
			} else {
				String roomNumber;
				do {
					System.out.println("Give a room number:/n");
					roomNumber = scanner.nextLine();
				} while (!(roomNumber != null));
				IRoom room = hotelResource.getRoom(roomNumber);
				try {
				Reservation newReservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
				if(newReservation == null)
					System.out.println("Error! try again");
				else
					System.out.println("Reservation created successfully!");
				}catch(NullPointerException e) {
					System.out.println("give a valid inputs");
				}
				printMainMenu();
				chooseANumber();

			}

		} else if ("n".equals(anser)) {
			printMainMenu();
			chooseANumber();
		} else {
			reserveRoom(checkInDate, checkOutDate, rooms);
		}
		scanner.close();
	}

	private static void printRooms(final Collection<IRoom> rooms) {
		for (IRoom r : rooms) {
			System.out.println(r);
		}
	}

	private static void seeMyReservation() {
		final Scanner scanner = new Scanner(System.in);

		System.out.println("Enter your Email format: name@domain.com");
		String customerEmail = scanner.nextLine();
		Collection<Reservation> reservations = hotelResource.getCustomersReservations(customerEmail);
		if ((reservations.isEmpty()) || (reservations == null)) {
			System.out.println("No reservations found.\n");
			printMainMenu();
			chooseANumber();
		} else {
			for (Reservation r : reservations) {
				System.out.println(r);
			}
			printMainMenu();
			chooseANumber();
		}
		scanner.close();
	}

	private static void createAccount() {
		final Scanner scanner = new Scanner(System.in);
		final String emailRegex = "^(.+)@(.+).(.+)$";
		final Pattern pattern = Pattern.compile(emailRegex);
		String email;

		do {
			System.out.println("Enter Email format: name@domain.com\n");
			email = scanner.nextLine();

		} while (!pattern.matcher(email).matches());

		System.out.println("First Name:\n");
		String firstName = scanner.nextLine();

		System.out.println("Last Name:\n");
		String lastName = scanner.nextLine();

		try {
			hotelResource.createACustomer(email, firstName, lastName);
			System.out.println("Account created successfully!\n");
			printMainMenu();
			chooseANumber();
		} catch (IllegalArgumentException ex) {
			System.out.println("Error: try again\n");
			createAccount();
		}
		scanner.close();
	}

	public static void printMainMenu() {
		System.out.println("-----------------------------------------------------\n" + "1. Find and reserve a room \n"
				+ "2. See my reservations\n" + "3. Create an Account\n" + "4. Admin\n" + "5. Exit\n"
				+ "-----------------------------------------------------\n"
				+ "Please select a number for the menu option:");
	}

}
