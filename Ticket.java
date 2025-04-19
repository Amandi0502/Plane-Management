import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
        private char row;
        private int seat;
        private double price;
        private Person person;


        // Constructor
        public Ticket(char row, int seat, double price, Person person) {
            this.row = row;
            this.seat = seat;
            this.price = price;
            this.person = person;
            save();
        }
        // Getters and setters
        public char getRow() {
            return row;
        }

        public void setRow(char row) {
            this.row = row;
        }

        public int getSeat() {
            return seat;
        }

        public void setSeat(int seat) {
            this.seat = seat;
        }


        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        // Method to print information of a Ticket (including the information of the Person)
        public void printTicketInfo() {
            System.out.println("Row: " + row);
            System.out.println("Seat: " + seat);
            System.out.println("Price: £" + price);
            System.out.println("Person Information:");
            person.printPersonInfo();
        }

        // Method to save ticket information to a file
        public void save() {
            String fileName = row + String.valueOf(seat) + ".txt";
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                // Write ticket information to the file
                fileWriter.write("Ticket Information:\n");
                fileWriter.write("Row: " + row + "\n");
                fileWriter.write("Seat: " + seat + "\n");
                fileWriter.write("Price: £" + price + "\n");
                fileWriter.write("Person Information:\n");
                fileWriter.write("Name: " + person.getName() + "\n");
                fileWriter.write("Surname: " + person.getSurname() + "\n");
                fileWriter.write("Email: " + person.getEmail() + "\n");
                System.out.println(fileName + "for a ticket sold in row" + row + "seat"+seat );
            } catch (IOException e) {
                System.out.println("Error saving ticket information to file: " + e.getMessage());
            }
        }
    }

