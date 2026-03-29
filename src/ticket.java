import java.time.LocalDateTime;

public class ticket {
    private static int counter = 1;
    private final int ticketId;
    private final vehicle vehicle;
    private final parkingslot slot;
    private final LocalDateTime entryTime;

    public ticket(vehicle v, parkingslot s) {
        this.ticketId = counter++;
        this.vehicle = v;
        this.slot = s;
        this.entryTime = LocalDateTime.now();
    }

    public int getTicketId() {
        return ticketId;
    }

    public vehicle getVehicle() {
        return vehicle;
    }

    public parkingslot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}