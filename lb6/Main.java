package lb6;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int[][] cinema;
    private static int numRows = 5;
    private static int numSeats = 10;
    private static int[][] ticketIDs;

    public static void main(String[] args) {
        initCinema();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Reserve seats");
            System.out.println("2. Cancel reservation by ticket ID");
            System.out.println("3. Check seat availability");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            if (choice == 1) {
                bookSeats(scanner);
            } else if (choice == 2) {
                cancelBookingByID(scanner);
            } else if (choice == 3) {
                checkAvailability(scanner);
            } else if (choice == 4) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    private static void initCinema() {
        cinema = new int[numRows][numSeats];
        ticketIDs = new int[numRows][numSeats];

        for (int row = 0; row < numRows; row++) {
            for (int seat = 0; seat < numSeats; seat++) {
                cinema[row][seat] = 0;
                ticketIDs[row][seat] = 0;
            }
        }
    }

    private static void bookSeats(Scanner scanner) {
        System.out.println("Enter row number:");
        int row = scanner.nextInt();

        if (row < 1 || row > numRows) {
            System.out.println("Invalid row number.");
            return;
        }

        System.out.println("Enter seat number:");
        int seatNumber = scanner.nextInt();

        if (seatNumber < 1 || seatNumber > numSeats) {
            System.out.println("Invalid seat number.");
            return;
        }

        if (cinema[row - 1][seatNumber - 1] == 0) {
            int ticketID = generateTicketID();
            cinema[row - 1][seatNumber - 1] = 1;
            ticketIDs[row - 1][seatNumber - 1] = ticketID;
            System.out.println("You have successfully booked a seat!");
            System.out.println("Your ticket ID: " + ticketID);
        } else {
            System.out.println("This seat is already booked.");
        }
    }


    private static int generateTicketID() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    private static void cancelBookingByID(Scanner scanner) {
        System.out.println("Enter row number:");
        int row = scanner.nextInt();

        if (row < 1 || row > numRows) {
            System.out.println("Invalid row number.");
            return;
        }

        System.out.println("Enter seat number:");
        int seatNumber = scanner.nextInt();

        if (seatNumber < 1 || seatNumber > numSeats) {
            System.out.println("Invalid seat number.");
            return;
        }

        int ticketID = ticketIDs[row - 1][seatNumber - 1];

        if (ticketID != 0) {
            cinema[row - 1][seatNumber - 1] = 0;
            ticketIDs[row - 1][seatNumber - 1] = 0;
            System.out.println("Reservation canceled successfully.");
        } else {
            System.out.println("No reservation found for this seat.");
        }
    }

    private static void checkAvailability(Scanner scanner) {
        System.out.println("Enter row number:");
        int row = scanner.nextInt();

        if (row < 1 || row > numRows) {
            System.out.println("Invalid row number.");
            return;
        }

        System.out.println("Enter the number of consecutive seats you want to book:");
        int numSeatsToBook = scanner.nextInt();

        if (numSeatsToBook < 1 || numSeatsToBook > numSeats) {
            System.out.println("Invalid number of seats.");
            return;
        }

        int consecutiveAvailableSeats = 0;
        int maxConsecutiveAvailableSeats = 0;

        for (int seat = 0; seat < numSeats; seat++) {
            if (cinema[row - 1][seat] == 0) {
                consecutiveAvailableSeats++;
                if (consecutiveAvailableSeats > maxConsecutiveAvailableSeats) {
                    maxConsecutiveAvailableSeats = consecutiveAvailableSeats;
                }
                if (consecutiveAvailableSeats == numSeatsToBook) {
                    break;
                }
            } else {
                consecutiveAvailableSeats = 0;
            }
        }

        if (maxConsecutiveAvailableSeats >= numSeatsToBook) {
            System.out.println("Available " + maxConsecutiveAvailableSeats + " consecutive seats in row " + row + ".");
        } else {
            System.out.println("Not enough available consecutive seats.");
        }

        printCinemaStatus();
    }

    private static void printCinemaStatus() {
        System.out.println("Status of seats:");

        for (int row = 0; row < numRows; row++) {
            for (int seat = 0; seat < numSeats; seat++) {
                if (cinema[row][seat] == 0) {
                    System.out.print("\u001B[32mO\u001B[0m      ");
                } else {
                    System.out.print("\u001B[31mX\u001B[0m      "); 
                }
            }
            System.out.println();
        }
    }
}
