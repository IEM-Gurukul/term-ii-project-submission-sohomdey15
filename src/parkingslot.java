public class parkingslot {
    private final int slotId;
    private final String type;
    private boolean isOccupied;

    public parkingslot(int slotId, String type) {
        this.slotId = slotId;
        this.type = type;
        this.isOccupied = false;
    }

    public boolean isAvailable() {
        return !isOccupied;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void free() {
        isOccupied = false;
    }

    public int getSlotId() {
        return slotId;
    }

    public String getType() {
        return type;
    }
}