import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UpgradedUI extends JFrame {
    private final parkinglot parkingLot;
    private JPanel slotsPanel;
    private JTextArea outputArea;
    private JTextField vehicleNumberField;
    private JTextField ticketIdField;
    private final Map<Integer, JButton> slotButtons;
    private final Map<Integer, JLabel> slotStatusLabels;
    private final Map<Integer, JLabel> slotTimerLabels;
    private final Map<Integer, JLabel> slotCarLabels;
    private JLabel statusLabel;
    private JLabel totalCountLabel;
    private JLabel occupiedCountLabel;
    private JLabel availableCountLabel;
    private JLabel currentTimeLabel;
    private JLabel projectionLabel;
    private Timer uiTimer;

    public UpgradedUI() {
        setTitle("Parking Lot Management System - Upgraded UI");
        setSize(980, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        parkingLot = new parkinglot(12);
        slotButtons = new HashMap<>();
        slotStatusLabels = new HashMap<>();
        slotTimerLabels = new HashMap<>();
        slotCarLabels = new HashMap<>();

        initializeUI();
        setupTimer();
        setVisible(true);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(14, 24, 44));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Parking Lot Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(230, 230, 250));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel digitalSummary = createDigitalScreenPanel();
        topPanel.add(digitalSummary, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        slotsPanel = createSlotsPanel();
        JScrollPane slotsScrollPane = new JScrollPane(slotsPanel);
        slotsScrollPane.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0, 180, 255), 2), "Parking Slots"));
        mainPanel.add(slotsScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBackground(new Color(10, 10, 25));

        outputArea = new JTextArea(6, 50);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Courier New", Font.PLAIN, 13));
        outputArea.setBackground(new Color(10, 10, 25));
        outputArea.setForeground(new Color(0, 255, 180));
        outputArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 180, 255), 1), "Messages"));
        bottomPanel.add(outputScrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Slots Available: 12");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        statusLabel.setForeground(new Color(150, 255, 150));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createDigitalScreenPanel() {
        JPanel digitalPanel = new JPanel();
        digitalPanel.setLayout(new GridLayout(5, 1, 5, 5));
        digitalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN, 2), "Digital Display"),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        digitalPanel.setBackground(new Color(20, 20, 40));

        currentTimeLabel = new JLabel("Time: --:--:--", JLabel.CENTER);
        totalCountLabel = new JLabel("Total Slots: 12", JLabel.CENTER);
        occupiedCountLabel = new JLabel("Occupied: 0", JLabel.CENTER);
        availableCountLabel = new JLabel("Available: 12", JLabel.CENTER);
        projectionLabel = new JLabel("Current Fee Projection: Rs. 0", JLabel.CENTER);

        for (JLabel label : new JLabel[]{currentTimeLabel, totalCountLabel, occupiedCountLabel, availableCountLabel, projectionLabel}) {
            label.setFont(new Font("Digital-7", Font.BOLD, 16));
            label.setForeground(new Color(0, 255, 170));
            label.setOpaque(true);
            label.setBackground(new Color(10, 20, 40));
        }

        digitalPanel.add(currentTimeLabel);
        digitalPanel.add(totalCountLabel);
        digitalPanel.add(occupiedCountLabel);
        digitalPanel.add(availableCountLabel);
        digitalPanel.add(projectionLabel);

        return digitalPanel;
    }

    private JPanel createSlotsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(20, 30, 50));

        int totalSlots = 12;
        for (int i = 1; i <= totalSlots; i++) {
            JPanel slotCard = new JPanel(new BorderLayout());
            slotCard.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 255), 2, true));
            slotCard.setBackground(new Color(30, 40, 70));
            slotCard.setPreferredSize(new Dimension(140, 90));

            JLabel slotLabel = new JLabel("SLOT " + i, JLabel.CENTER);
            slotLabel.setFont(new Font("Arial", Font.BOLD, 14));
            slotLabel.setForeground(new Color(110, 200, 255));

            JLabel statusLabel = new JLabel("EMPTY", JLabel.CENTER);
            statusLabel.setFont(new Font("Courier New", Font.BOLD, 13));
            statusLabel.setForeground(new Color(0, 255, 170));
            statusLabel.setOpaque(true);
            statusLabel.setBackground(new Color(10, 50, 30));
            statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

            JLabel carLabel = new JLabel("No car", JLabel.CENTER);
            carLabel.setFont(new Font("Courier New", Font.PLAIN, 12));
            carLabel.setForeground(new Color(180, 220, 240));
            carLabel.setOpaque(true);
            carLabel.setBackground(new Color(18, 30, 55));
            carLabel.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));

            JLabel timerLabel = new JLabel("Idle", JLabel.CENTER);
            timerLabel.setFont(new Font("Courier New", Font.PLAIN, 12));
            timerLabel.setForeground(new Color(200, 255, 200));
            timerLabel.setOpaque(true);
            timerLabel.setBackground(new Color(20, 40, 70));
            timerLabel.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));

            slotStatusLabels.put(i, statusLabel);
            slotTimerLabels.put(i, timerLabel);
            slotCarLabels.put(i, carLabel);

            slotCard.add(slotLabel, BorderLayout.NORTH);
            JPanel centerBlock = new JPanel(new GridLayout(2, 1, 2, 2));
            centerBlock.setOpaque(false);
            centerBlock.add(statusLabel);
            centerBlock.add(carLabel);
            slotCard.add(centerBlock, BorderLayout.CENTER);
            slotCard.add(timerLabel, BorderLayout.SOUTH);

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setBackground(new Color(30, 40, 70));
            wrapper.add(slotCard, BorderLayout.CENTER);

            panel.add(wrapper);
        }

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder("Operations"));
        panel.setPreferredSize(new Dimension(280, 0));
        panel.setBackground(new Color(16, 22, 45));

        JPanel parkPanel = new JPanel();
        parkPanel.setLayout(new BoxLayout(parkPanel, BoxLayout.Y_AXIS));
        parkPanel.setBorder(new TitledBorder("Park Vehicle"));
        parkPanel.setOpaque(false);

        JLabel vehicleLabel = new JLabel("Vehicle Number:");
        vehicleLabel.setForeground(Color.WHITE);
        vehicleNumberField = new JTextField(15);
        vehicleNumberField.setMaximumSize(new Dimension(220, 30));

        JButton parkButton = new JButton("Park Vehicle");
        parkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        parkButton.setMaximumSize(new Dimension(220, 40));
        parkButton.addActionListener(e -> parkVehicle());

        parkPanel.add(vehicleLabel);
        parkPanel.add(vehicleNumberField);
        parkPanel.add(Box.createVerticalStrut(10));
        parkPanel.add(parkButton);
        parkPanel.add(Box.createVerticalStrut(20));

        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new BoxLayout(exitPanel, BoxLayout.Y_AXIS));
        exitPanel.setBorder(new TitledBorder("Exit Vehicle"));
        exitPanel.setOpaque(false);

        JLabel ticketLabel = new JLabel("Ticket ID:");
        ticketLabel.setForeground(Color.WHITE);
        ticketIdField = new JTextField(15);
        ticketIdField.setMaximumSize(new Dimension(220, 30));

        JButton exitButton = new JButton("Exit Vehicle");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(220, 40));
        exitButton.addActionListener(e -> exitVehicle());

        exitPanel.add(ticketLabel);
        exitPanel.add(ticketIdField);
        exitPanel.add(Box.createVerticalStrut(10));
        exitPanel.add(exitButton);
        exitPanel.add(Box.createVerticalStrut(20));

        JButton statusButton = new JButton("Refresh Status");
        statusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusButton.setMaximumSize(new Dimension(220, 40));
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
            showMessage("✓ Vehicle " + vehicleNumber + " parked successfully!\nTicket ID: " + t.getTicketId(), new Color(0, 180, 255));
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
            handleExit(ticketId);
            ticketIdField.setText("");
            refreshDisplay();
        } catch (NumberFormatException e) {
            showMessage("Invalid ticket ID. Please enter a number.", Color.RED);
        }
    }

    private void handleExit(int ticketId) {
        Map<Integer, ticket> activeTickets = parkingLot.activeTickets;
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
            JLabel status = slotStatusLabels.get(i);
            JLabel timer = slotTimerLabels.get(i);

            if (slot.isAvailable()) {
                status.setText("EMPTY");
                status.setForeground(new Color(0, 255, 170));
                status.setBackground(new Color(10, 50, 30));
                timer.setText("Idle");
                slotCarLabels.get(i).setText("No car");
            } else {
                status.setText("OCCUPIED");
                status.setForeground(new Color(255, 120, 120));
                status.setBackground(new Color(70, 20, 30));

                final int slotId = i;
                ticket ticketForSlot = parkingLot.activeTickets.values().stream()
                        .filter(t -> t.getSlot().getSlotId() == slotId)
                        .findFirst().orElse(null);

                if (ticketForSlot != null) {
                    long minutes = java.time.Duration.between(ticketForSlot.getEntryTime(), java.time.LocalDateTime.now()).toMinutes();
                    long hours = minutes / 60;
                    long remMinutes = minutes % 60;
                    int currentFee = (int) ((hours + 1) * 20);
                    timer.setText(String.format("%02dh %02dm / Rs. %d", hours, remMinutes, currentFee));
                    slotCarLabels.get(i).setText(ticketForSlot.getVehicle().getVehicleNumber());
                } else {
                    timer.setText("- -");
                    slotCarLabels.get(i).setText("Where is car?");
                }
            }
        }
    }

    private void updateStatus() {
        long availableSlots = parkingLot.slots.stream().filter(parkingslot::isAvailable).count();
        long occupiedSlots = 12 - availableSlots;

        statusLabel.setText("Slots Available: " + availableSlots + " / 12");
        totalCountLabel.setText("Total: 12");
        occupiedCountLabel.setText("Occupied: " + occupiedSlots);
        availableCountLabel.setText("Available: " + availableSlots);

        if (availableSlots == 0) {
            statusLabel.setForeground(new Color(255, 120, 120));
        } else {
            statusLabel.setForeground(new Color(150, 255, 150));
        }

        projectionLabel.setText("Current Fee Projection: Rs. " + calculateCurrentFee());
    }

    private void setupTimer() {
        uiTimer = new Timer(1000, e -> {
            currentTimeLabel.setText("Time: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
            updateSlotsDisplay();
            updateStatus();
        });
        uiTimer.start();
    }

    private int calculateCurrentFee() {
        int total = 0;
        for (ticket t : parkingLot.activeTickets.values()) {
            long hours = java.time.Duration.between(t.getEntryTime(), java.time.LocalDateTime.now()).toHours() + 1;
            total += (int) hours * 20;
        }
        return total;
    }

    private void showMessage(String message, Color color) {
        outputArea.setForeground(color);
        outputArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpgradedUI::new);
    }
}
