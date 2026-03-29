import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        parkinglot lot = new parkinglot(5);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Park Vehicle");
            System.out.println("2. Exit Vehicle");
            System.out.println("3. Display Status");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter vehicle number: ");
                    String num = sc.next();
                    vehicle v = new vehicle(num, "CAR");
                    lot.parkVehicle(v);
                    break;

                case 2:
                    System.out.print("Enter ticket ID: ");
                    int id = sc.nextInt();
                    lot.exitVehicle(id);
                    break;

                case 3:
                    lot.displayStatus();
                    break;

                case 4:
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}