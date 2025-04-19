import java.util.Scanner;

    public class PLaneManagement {

        private static final int AVAILABLE = 0;      //Represent seat availability
        private static final int SOLD = 1;          //Constants declared as "static final" are easily recognizable as constants throughout the codebase. Developers understand that these values are not meant to change during runtime.
        private static int[][] seatMatrix;          // Seat matrix (2D Array) to keep track of seat availability

        // Array to store all tickets sold in the session
        private static Ticket[] tickets;

        // Maximum number of tickets allowed in the session
        private static final int MAX_TICKETS = 52;
        // Variable to track the number of tickets sold
        private static int numTicketsSold = 0;

        public static void main(String[] args) {
            System.out.println("Welcome to the Plane Management application");

            seatMatrix = initializeSeats();  // Initialize and manage seat availability

            // Initialize the array to store tickets
            tickets = new Ticket[MAX_TICKETS];
            Scanner scanner = new Scanner(System.in);   // Create a Scanner object to read user input

            boolean flag = true;
            // User menu loop
            while (flag) {
                // Display menu options
                System.out.println("\n*************************************************");
                System.out.println("*                 MENU OPTIONS                  *");
                System.out.println("*************************************************");
                System.out.println("      1) Buy a seat");
                System.out.println("      2) Cancel a seat");
                System.out.println("      3) Find first available seat");
                System.out.println("      4) Show seating plan");
                System.out.println("      5) Print tickets information and total sales");
                System.out.println("      6) Search ticket");
                System.out.println("      0) Quit");
                System.out.println("*************************************************");
                System.out.print("Please select an option: ");

                // Read user input
                int option = scanner.nextInt();

                // Perform actions based on user input
                switch (option) {
                    case 0:
                        flag = false;                                    //Exiting the program
                        System.out.println("Thank you for booking with us...!!");
                        return;
                    case 1:
                        buy_seat(scanner);   // Booking the seat (Task 2)
                        break;
                    case 2:
                        cancel_seat(scanner);  // Cancel a seat (Task 3)
                        break;
                    case 3:
                        find_first_available();  // Find first available seat (Task 4)
                        break;
                    case 4:
                        show_seating_plan();  // Show seating plan (Task 5)
                        break;
                    case 5:
                        print_tickets_info();  // Print tickets information and total sales (Task 6)
                        break;
                    case 6:
                        search_ticket(scanner);  // Search ticket (Task 7)
                        break;
                    default:
                        System.out.println("Invalid option. Please select a valid option.");
                }
            }
        }

        private static void find_first_available() {
            for (int i = 0; i < seatMatrix.length; i++) {
                for (int j = 0; j < seatMatrix[i].length; j++) {
                    if (seatMatrix[i][j] == AVAILABLE) {
                        char rowLetter = (char) ('A' + i);
                        int seatNumber = j + 1;
                        System.out.println("First available seat: " + rowLetter + seatNumber);
                        return; // Return after finding the first available seat
                    }
                }
            }
            System.out.println("No available seats found.");
        }

        // Method to initialize seats
        private static int[][] initializeSeats() {
            // Define the seating arrangement
            int[][] seatMatrix = {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row A (14 seats)
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},    // Row B (12 seats)
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row C (12 seats)
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}     // Row D (14 seats)
            };

            return seatMatrix;
        }

        // Method to buy a seat
        private static void buy_seat(Scanner scanner) {
            // Check if maximum tickets have been sold
            if (numTicketsSold >= MAX_TICKETS) {
                System.out.println("Maximum number of tickets sold. Cannot sell more tickets.");
                return;
            }

            // Prompt user to input row letter and seat number
            System.out.print("Enter the row letter (A-D): ");
            char rowLetter = scanner.next().toUpperCase().charAt(0); // Convert to uppercase to handle lowercase input
            System.out.print("Enter the seat number: ");
            int seatNumber = scanner.nextInt();

            // Convert row letter to array index
            int row;
            switch(rowLetter) {
                case 'A':
                    row = 0;
                    break;
                case 'B':
                    row = 1;
                    break;
                case 'C':
                    row = 2;
                    break;
                case 'D':
                    row = 3;
                    break;
                default:
                    System.out.println("Invalid row letter. Please enter a valid row (A-D).");
                    return;
            }

            // Check if the seat number is valid
            if (seatNumber < 1 || seatNumber > seatMatrix[row].length) {
                System.out.println("Invalid seat number. Please enter a valid seat number.");
                return;
            }

            // Check if the seat is available
            if (seatMatrix[row][seatNumber - 1] == AVAILABLE) {    //array indexing starts from 0
                seatMatrix[row][seatNumber - 1] = SOLD;            // Record the seat as sold
                System.out.print("Enter person's name: ");         // Prompt user to input person's information
                String name = scanner.next();
                System.out.print("Enter person's surname: ");
                String surname = scanner.next();
                System.out.print("Enter person's email: ");
                String email = scanner.next();
                Person person = new Person(name, surname, email);    // Create a new Person object
                double price = calculatePrice(seatNumber);             // Calculate price using calculatePrice()  function. It's based on seat locatio
                Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);  // Create a new Ticket object
                tickets[numTicketsSold++] = ticket;              // Add the ticket to the array of tickets
                System.out.println("Seat " + rowLetter + seatNumber + " reserved successfully.");
            } else {
                System.out.println("Seat " + rowLetter + seatNumber + " is already sold.");
            }
        }

        // Calculate price based on seat location
        private static double calculatePrice(int seatNumber) {
            double price;
            if (seatNumber >= 1 && seatNumber <= 5) {
                price = 200; // £200 for seat numbers 1 to 5
            } else if (seatNumber >= 6 && seatNumber <= 9) {
                price = 150; // £150 for seat numbers 6 to 9
            } else {
                price = 180; // £180 For seat numbers 10 to 14 for rows A and D or 10 to 12 for rows B and C
            }
            return price;
        }

        private static void cancel_seat(Scanner scanner) {
            // Prompt user to input row letter and seat number
            System.out.print("Enter the row letter you want to cancel (A-D): ");
            char rowLetter = scanner.next().toUpperCase().charAt(0); // Convert to uppercase to handle lowercase input
            System.out.print("Enter the seat number you want to cancel: ");
            int seatNumber = scanner.nextInt();

            // Convert row letter to array index
            int row;
            switch (rowLetter) {
                case 'A':
                    row = 0;
                    break;
                case 'B':
                    row = 1;
                    break;
                case 'C':
                    row = 2;
                    break;
                case 'D':
                    row = 3;
                    break;
                default:
                    System.out.println("Invalid row letter. Please enter a valid row (A-D).");
                    return;
            }

            // Check if the seat number is valid
            if (seatNumber < 1 || seatNumber > seatMatrix[row].length) {
                System.out.println("Invalid seat number. Please enter a valid seat number.");
                return;
            }


            if (seatMatrix[row][seatNumber - 1] == SOLD) {       // Check if the seat is sold
                seatMatrix[row][seatNumber - 1] = AVAILABLE;     // Mark the seat as available
                for (int i = 0; i < numTicketsSold; i++) {       // Remove the corresponding ticket from the array of tickets
                    Ticket ticket = tickets[i];
                    if (ticket.getRow() == rowLetter && ticket.getSeat() == seatNumber) {
                        for (int j = i; j < numTicketsSold - 1; j++) {            // Move tickets one position left to fill the gap
                            tickets[j] = tickets[j + 1];
                        }
                        numTicketsSold--;                                       // Reduce the count of tickets sold
                        System.out.println("Seat " + rowLetter + seatNumber + " has been successfully canceled.");
                        return;
                    }
                }
            } else {
                System.out.println("Seat " + rowLetter + seatNumber + " is already available. Nothing to cancel.");
            }
        }

        // Method to show seating plan
        private static void show_seating_plan() {
            // Loop through the seat matrix and display 'O' for available seats and 'X' for sold seats
            for (int i = 0; i < seatMatrix.length; i++) {

                for (int j = 0; j < seatMatrix[i].length; j++) {
                    if (seatMatrix[i][j] == AVAILABLE) {
                        System.out.print("O ");
                    } else {
                        System.out.print("X ");
                    }
                }
                System.out.println(); // Move to the next line after each row
            }
        }

        // Method to print tickets information and calculate total price
        private static void print_tickets_info() {
            double totalPrice = 0;
            System.out.println("Tickets Information:");

            // Iterate through the tickets array and print ticket information
            for (int i = 0; i < numTicketsSold; i++) {
                Ticket ticket = tickets[i];
                System.out.println("Ticket " + (i + 1) + ":");
                System.out.println("Row: " + ticket.getRow());
                System.out.println("Seat: " + ticket.getSeat());
                System.out.println("Price: £" + ticket.getPrice());
                System.out.println("Person Information:");
                System.out.println("Name: " + ticket.getPerson().getName());
                System.out.println("Surname: " + ticket.getPerson().getSurname());
                System.out.println("Email: " + ticket.getPerson().getEmail());
                totalPrice += ticket.getPrice();
                System.out.println("---------------------------------------");
            }
            System.out.println("Total Price of Tickets Sold: £" + totalPrice);
        }

        // Method to search for a ticket
        private static void search_ticket(Scanner scanner) {
            // Prompt user to input row letter and seat number
            System.out.print("Enter the row letter (A-D): ");
            char rowLetter = scanner.next().toUpperCase().charAt(0); // Convert to uppercase to handle lowercase input
            System.out.print("Enter the seat number: ");
            int seatNumber = scanner.nextInt();

            // Convert row letter to array index
            int row;
            switch (rowLetter) {
                case 'A':
                    row = 0;
                    break;
                case 'B':
                    row = 1;
                    break;
                case 'C':
                    row = 2;
                    break;
                case 'D':
                    row = 3;
                    break;
                default:
                    System.out.println("Invalid row letter. Please enter a valid row (A-D).");
                    return;
            }

            // Check if the seat number is valid
            if (seatNumber < 1 || seatNumber > seatMatrix[row].length) {
                System.out.println("Invalid seat number. Please enter a valid seat number.");
                return;
            }

            // Check if the seat is sold
            if (seatMatrix[row][seatNumber - 1] == SOLD) {
                // Search for the ticket in the tickets array
                for (int i = 0; i < numTicketsSold; i++) {
                    Ticket ticket = tickets[i];
                    if (ticket.getRow() == rowLetter && ticket.getSeat() == seatNumber) {
                        // Print ticket and person information
                        System.out.println("Ticket Information:");
                        System.out.println("Row: " + ticket.getRow());
                        System.out.println("Seat: " + ticket.getSeat());
                        System.out.println("Price: £" + ticket.getPrice());
                        System.out.println("Person Information:");
                        System.out.println("Name: " + ticket.getPerson().getName());
                        System.out.println("Surname: " + ticket.getPerson().getSurname());
                        System.out.println("Email: " + ticket.getPerson().getEmail());
                        return;
                    }
                }
            } else {
                System.out.println("This seat is available.");
            }
        }
    }


