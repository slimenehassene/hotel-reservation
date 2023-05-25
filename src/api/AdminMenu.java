package api;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import model.Customer;
import model.FreeRoom;
import model.IRoom;
import model.RoomType;

public class AdminMenu {

	private static final AdminResource adminResource = AdminResource.getAdminResource();

	public static void adminMenu() {
		System.out.println("\nAdmin Menu\n");
		printMenu();
		chooseANumber();
	}

	private static void chooseANumber() {

		String line = "";
		final Scanner scanner = new Scanner(System.in);

		int number;
		try {
			do {
				line = scanner.nextLine();
				number = Integer.parseInt(line);

				switch (number) {
				case 1:
					displayAllCustomers();
					break;
				case 2:
					displayAllRooms();
					break;
				case 3:
					displayAllReservations();
					break;
				case 4:
					addRoom();
					break;
				case 5:
					MainMenu.printMainMenu();
					MainMenu.chooseANumber();
					break;
				default:
					System.out.println("Please Give a valid number!\n");
					MainMenu.chooseANumber();
					break;
				}
			} while ((number < 1) && (number > 5));
		} catch (IllegalArgumentException ex) {
			System.out.println("Error: Invalid input\n");
		}

		scanner.close();

	}

	private static void printMenu() {
		System.out.print("--------------------------------------------\n" + "1. See all Customers\n"
				+ "2. See all Rooms\n" + "3. See all Reservations\n" + "4. Add a Room\n" + "5. Back to Main Menu\n"
				+ "--------------------------------------------\n" + "Please select a number for the menu option:\n");
	}

	private static void addRoom() {

		Scanner scanner = new Scanner(System.in);
		final String roomNumber;

		final double roomPrice;
		final RoomType roomType;
		int temp;
		try {

			System.out.println("Enter room number:");
			roomNumber = scanner.nextLine();
			Integer.parseInt(roomNumber);
			System.out.println("Enter price per night:");
			roomPrice = Double.parseDouble(scanner.nextLine());

			do {
				System.out.println("Enter room type: 1 for single bed, 2 for double bed:");
				temp = Integer.parseInt(scanner.nextLine());
			} while ((temp != 1) && (temp != 2));

			if (temp == 1)
				roomType = RoomType.SINGLE;
			else if (temp == 2)
				roomType = RoomType.DOUBLE;
			else
				roomType = null;

			final FreeRoom room = new FreeRoom(roomNumber, roomPrice, roomType);
			adminResource.addRoom(Collections.singletonList(room));
			System.out.println("Room added successfully!");

		} catch (IllegalArgumentException ex) {
			System.out.println("Give a valide Inputs!!\n");
			addRoom();
		}

		System.out.println("Would like to add another room? Y/N");
		String anotherRoom = scanner.nextLine();
		boolean test = true;
		while (test) {
			if ((anotherRoom.charAt(0) == 'Y') || (anotherRoom.charAt(0) == 'y')) {
				addRoom();
				test = false;
			} else if ((anotherRoom.charAt(0) == 'N') || (anotherRoom.charAt(0) == 'n')) {
				printMenu();
				chooseANumber();
				test = false;
			}
		}
		scanner.close();
	}

	private static void displayAllRooms() {
		Collection<IRoom> rooms = adminResource.getAllRooms();

		if (rooms.isEmpty()) {
			System.out.println("No rooms found.");
			printMenu();
			chooseANumber();
		} else {
			for (IRoom r : adminResource.getAllRooms()) {
				System.out.println(r);
			}
			printMenu();
			chooseANumber();
		}
	}

	private static void displayAllCustomers() {
		Collection<Customer> customers = adminResource.getAllCustomers();

		if (customers.isEmpty()) {
			System.out.println("No customers found.");
			printMenu();
			chooseANumber();
		} else {
			for (Customer c : adminResource.getAllCustomers()) {
				System.out.println(c);
			}
			printMenu();
			chooseANumber();
		}
	}

	private static void displayAllReservations() {
		adminResource.displayAllReservations();
		printMenu();
		chooseANumber();
	}

}
