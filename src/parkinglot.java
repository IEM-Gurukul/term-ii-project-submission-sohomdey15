import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class parkinglot {
    public List<parkingslot> slots = new ArrayList<>();
    public Map<Integer, ticket> activeTickets = new HashMap<>();

    public parkinglot(int capacity) {
        for (int i = 1; i <= capacity; i++) {
            slots.add(new parkingslot(i, "CAR"));
        }
    }

    public ticket parkVehicle(vehicle v) {
        for (parkingslot slot : slots) {
            if (slot.isAvailable()) {
                slot.occupy();
                ticket tkt = new ticket(v, slot);
                activeTickets.put(tkt.getTicketId(), tkt);
                System.out.println("Vehicle parked at slot " + slot.getSlotId());
                return tkt;
            }
        }
        System.out.println("Parking Full!");
        return null;
    }

    public void exitVehicle(int ticketId) {
        if (!activeTickets.containsKey(ticketId)) {
            System.out.println("Invalid Ticket!");
            return;
        }

        ticket tkt = activeTickets.remove(ticketId);
        parkingslot slot = tkt.getSlot();
        slot.free();

        LocalDateTime exitTime = LocalDateTime.now();
        long hours = Duration.between(tkt.getEntryTime(), exitTime).toHours() + 1;

        int fee = (int) hours * 20;

        System.out.println("Vehicle exited from slot " + slot.getSlotId());
        System.out.println("Total Fee: Rs. " + fee);
    }

    public void displayStatus() {
        for (parkingslot slot : slots) {
            System.out.println("Slot " + slot.getSlotId() + ": " +
                    (slot.isAvailable() ? "Available" : "Occupied"));
        }
    }
}