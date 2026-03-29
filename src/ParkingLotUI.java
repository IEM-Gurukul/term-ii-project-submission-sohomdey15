import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ParkingLotUI extends JFrame {
    private final parkinglot parkingLot;
    private JPanel slotsPanel;
    private JTextArea outputArea;
    private JTextField vehicleNumberField;
    private JTextField ticketIdField;
    private final Map<Integer, JButton> slotButtons;
    private JLabel statusLabel;

    public ParkingLotUI() {
        setTitle("Parking Lot Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        parkingLot = new parkinglot(12);
        slotButtons = new HashMap<>();

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel: Title
        JLabel titleLabel = new JLabel("Parking Lot Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center panel: Parking slots display
        slotsPanel = createSlotsPanel();
        JScrollPane slotsScrollPane = new JScrollPane(slotsPanel);
        slotsScrollPane.setBorder(new TitledBorder("Parking Slots"));
        mainPanel.add(slotsScrollPane, BorderLayout.CENTER);

        // Right panel: Controls
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.EAST);

        // Bottom panel: Output and status
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        outputArea = new JTextArea(6, 50);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Courier", Font.PLAIN, 11));
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(new TitledBorder("Messages"));
        bottomPanel.add(outputScrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Slots Available: 12");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createSlotsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.LIGHT_GRAY);

        int totalSlots = 12;
        for (int i = 1; i <= totalSlots; i++) {
            JButton slotButton = new JButton("Slot " + i + "\nEmpty");
            slotButton.setFont(new Font("Arial", Font.PLAIN, 12));
            slotButton.setBackground(Color.GREEN);
            slotButton.setForeground(Color.BLACK);
            slotButton.setFocusPainted(false);
            slotButton.setEnabled(false);

            slotButtons.put(i, slotButton);
            panel.add(slotButton);
        }

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder("Operations"));
        panel.setPreferredSize(new Dimension(250, 0));

        // Park Vehicle Section
        JPanel parkPanel = new JPanel();
        parkPanel.setLayout(new BoxLayout(parkPanel, BoxLayout.Y_AXIS));
        parkPanel.setBorder(new TitledBorder("Park Vehicle"));

        JLabel vehicleLabel = new JLabel("Vehicle Number:");
        vehicleNumberField = new JTextField(15);
        vehicleNumberField.setMaximumSize(new Dimension(200, 30));

        JButton parkButton = new JButton("Park Vehicle");
        parkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        parkButton.setMaximumSize(new Dimension(200, 40));
        parkButton.addActionListener(e -> parkVehicle());

        parkPanel.add(vehicleLabel);
        parkPanel.add(vehicleNumberField);
        parkPanel.add(Box.createVerticalStrut(10));
        parkPanel.add(parkButton);
        parkPanel.add(Box.createVerticalStrut(20));

        // Exit Vehicle Section
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new BoxLayout(exitPanel, BoxLayout.Y_AXIS));
        exitPanel.setBorder(new TitledBorder("Exit Vehicle"));

        JLabel ticketLabel = new JLabel("Ticket ID:");
        ticketIdField = new JTextField(15);
        ticketIdField.setMaximumSize(new Dimension(200, 30));

        JButton exitButton = new JButton("Exit Vehicle");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 40));
        exitButton.addActionListener(e -> exitVehicle());

        exitPanel.add(ticketLabel);
        exitPanel.add(ticketIdField);
        exitPanel.add(Box.createVerticalStrut(10));
        exitPanel.add(exitButton);
        exitPanel.add(Box.createVerticalStrut(20));

        // Display Status Button
        JButton statusButton = new JButton("Refresh Status");
        statusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusButton.setMaximumSize(new Dimension(200, 40));
        statusButton.addActionListener(e -> refreshDisplay());

        panel.add(parkPanel);
        panel.add(exitPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(statusButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private void parkVehicle() {
        String vehicleNumber = vehicleNumberField.getText().trim();

        if (vehicleNumber.isEmpty()) {
            showMessage("Please enter a vehicle number.", Color.RED);
            return;
        }

        vehicle v = new vehicle(vehicleNumber, "CAR");
        ticket t = parkingLot.parkVehicle(v);

        if (t != null) {
            showMessage("✓ Vehicle " + vehicleNumber + " parked successfully!\nTicket ID: " + t.getTicketId(), Color.BLUE);
            vehicleNumberField.setText("");
            refreshDisplay();
        } else {
            showMessage("✗ Parking lot is full! Cannot park vehicle.", Color.RED);
        }
    }

    private void exitVehicle() {
        String ticketIdStr = ticketIdField.getText().trim();

        if (ticketIdStr.isEmpty()) {
            showMessage("Please enter a ticket ID.", Color.RED);
            return;
        }

        try {
            int ticketId = Integer.parseInt(ticketIdStr);
            // Create a custom method to track exited vehicles and fees
            handleExit(ticketId);
            ticketIdField.setText("");
            refreshDisplay();
        } catch (NumberFormatException e) {
            showMessage("Invalid ticket ID. Please enter a number.", Color.RED);
        }
    }

    private void handleExit(int ticketId) {
        // Since parkingLot.exitVehicle prints to console, we'll capture the logic here
        java.util.Map<Integer, ticket> activeTickets = parkingLot.activeTickets;
        
        if (!activeTickets.containsKey(ticketId)) {
            showMessage("✗ Invalid Ticket ID! No vehicle found.", Color.RED);
            return;
        }

        ticket t = activeTickets.remove(ticketId);
        parkingslot slot = t.getSlot();
        slot.free();

        java.time.LocalDateTime exitTime = java.time.LocalDateTime.now();
        long hours = java.time.Duration.between(t.getEntryTime(), exitTime).toHours() + 1;
        int fee = (int) hours * 20;

        String message = "✓ Vehicle exited from slot " + slot.getSlotId() + "\n" +
                         "Duration: " + hours + " hours\n" +
                         "Total Fee: Rs. " + fee;
        showMessage(message, new Color(34, 139, 34));
    }

    private void refreshDisplay() {
        updateSlotsDisplay();
        updateStatus();
    }

    private void updateSlotsDisplay() {
        for (int i = 1; i <= 12; i++) {
            parkingslot slot = parkingLot.slots.get(i - 1);
            JButton button = slotButtons.get(i);

            if (slot.isAvailable()) {
                button.setText("Slot " + i + "\nEmpty");
                button.setBackground(Color.GREEN);
                button.setForeground(Color.BLACK);
            } else {
                button.setText("Slot " + i + "\nOccupied");
                button.setBackground(new Color(255, 100, 100));
                button.setForeground(Color.WHITE);
            }
        }
    }

    private void updateStatus() {
        long availableSlots = parkingLot.slots.stream().filter(parkingslot::isAvailable).count();
        statusLabel.setText("Slots Available: " + availableSlots + " / 12");
    }

    private void showMessage(String message, Color color) {
        outputArea.setForeground(color);
        outputArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParkingLotUI::new);
    }
}
