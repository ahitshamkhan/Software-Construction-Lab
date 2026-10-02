import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MailGUI extends JFrame {
    private final MailServer server;
    private final List<MailClient> clients = new ArrayList<>();

    private JComboBox<String> clientSelector;
    private DefaultListModel<String> inboxModel;
    private JList<String> inboxList;
    private JTextArea messageArea;
    private JTextField toField, subjectField;
    private JButton sendBtn, refreshBtn, composeBtn;
    private JLabel statusLabel, unreadCountLabel;
    private JPanel inboxPanel, sidebarPanel;

    private List<MailItem> currentInbox = new ArrayList<>();

    // Modern Dark Theme Color Palette
    private static final Color PRIMARY = new Color(107, 115, 255);        // Vibrant Purple-Blue
    private static final Color PRIMARY_LIGHT = new Color(139, 146, 255);  // Light Purple
    private static final Color PRIMARY_DARK = new Color(88, 96, 235);     // Dark Purple
    private static final Color BG_DARK = new Color(15, 18, 40);           // Deep Navy
    private static final Color BG_CARD = new Color(25, 29, 55);           // Card Background
    private static final Color BG_SIDEBAR = new Color(20, 24, 50);        // Sidebar
    private static final Color TEXT_PRIMARY = new Color(255, 255, 255);   // White
    private static final Color TEXT_SECONDARY = new Color(160, 165, 190); // Light Gray
    private static final Color BORDER_COLOR = new Color(40, 45, 75);      // Subtle Border
    private static final Color HOVER_BG = new Color(35, 40, 70);          // Hover State
    private static final Color SELECTED_BG = new Color(45, 50, 90);       // Selected Item
    private static final Color SUCCESS = new Color(0, 200, 150);          // Teal Green
    private static final Color SUCCESS_DARK = new Color(0, 180, 135);
    private static final Color ACCENT_GRADIENT_START = new Color(107, 115, 255);
    private static final Color ACCENT_GRADIENT_END = new Color(139, 95, 255);

    public MailGUI() {
        server = new MailServer();
        initUI();

        addClient("Alice");
        addClient("Bob");
        addClient("Charlie");

        selectClient(0);
    }

    private void initUI() {
        setTitle("ProMail - Software Construction Lab 5");
        setSize(1300, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set dark theme for the whole window
        getContentPane().setBackground(BG_DARK);

        setLayout(new BorderLayout());

        JPanel topBar = createTopBar();
        add(topBar, BorderLayout.NORTH);

        JPanel leftPanel = createInboxPanel();
        add(leftPanel, BorderLayout.WEST);

        JSplitPane centerSplit = createCenterPanel();
        add(centerSplit, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createTopBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_CARD);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));
        panel.setPreferredSize(new Dimension(0, 70));

        // Left side - Logo and Client Selector
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftPanel.setBackground(BG_CARD);

        JLabel logoLabel = new JLabel("✉");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(PRIMARY);
        leftPanel.add(logoLabel);

        JLabel appTitle = new JLabel("ProMail");
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        appTitle.setForeground(TEXT_PRIMARY);
        leftPanel.add(appTitle);

        // Client Selector with modern styling
        clientSelector = new JComboBox<>();
        clientSelector.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clientSelector.setPreferredSize(new Dimension(200, 40));
        clientSelector.setBackground(BG_SIDEBAR);
        clientSelector.setForeground(TEXT_PRIMARY);
        clientSelector.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        clientSelector.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                if (isSelected) {
                    c.setBackground(PRIMARY);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(BG_SIDEBAR);
                    c.setForeground(TEXT_PRIMARY);
                }
                return c;
            }
        });
        clientSelector.addActionListener(e -> {
            int idx = clientSelector.getSelectedIndex();
            if (idx >= 0) selectClient(idx);
        });
        leftPanel.add(clientSelector);

        // Right side - Status with gradient effect
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setBackground(BG_CARD);

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(TEXT_SECONDARY);
        rightPanel.add(statusLabel);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createInboxPanel() {
        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(BG_SIDEBAR);
        sidebarPanel.setPreferredSize(new Dimension(350, 0));
        sidebarPanel.setBorder(new MatteBorder(0, 0, 0, 1, BORDER_COLOR));

        // Header with gradient background
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_SIDEBAR);
        headerPanel.setBorder(new EmptyBorder(25, 25, 20, 25));

        JLabel inboxTitle = new JLabel("INBOX");
        inboxTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inboxTitle.setForeground(TEXT_SECONDARY);
        headerPanel.add(inboxTitle, BorderLayout.WEST);

        unreadCountLabel = new JLabel("0");
        unreadCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        unreadCountLabel.setForeground(PRIMARY);
        headerPanel.add(unreadCountLabel, BorderLayout.EAST);

        sidebarPanel.add(headerPanel, BorderLayout.NORTH);

        // Compose Button with gradient effect
        composeBtn = new JButton("  ✎  COMPOSE");
        composeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        composeBtn.setBackground(PRIMARY);
        composeBtn.setForeground(Color.WHITE);
        composeBtn.setFocusPainted(false);
        composeBtn.setBorderPainted(false);
        composeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        composeBtn.setPreferredSize(new Dimension(0, 50));
        composeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                composeBtn.setBackground(PRIMARY_DARK);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                composeBtn.setBackground(PRIMARY);
            }
        });
        composeBtn.addActionListener(e -> {
            toField.requestFocus();
            toField.selectAll();
        });

        JPanel composeWrapper = new JPanel(new BorderLayout());
        composeWrapper.setBackground(BG_SIDEBAR);
        composeWrapper.setBorder(new EmptyBorder(0, 25, 20, 25));
        composeWrapper.add(composeBtn, BorderLayout.CENTER);
        sidebarPanel.add(composeWrapper, BorderLayout.NORTH);

        // Inbox List with custom renderer
        inboxModel = new DefaultListModel<>();
        inboxList = new JList<>(inboxModel);
        inboxList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        inboxList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inboxList.setBackground(BG_SIDEBAR);
        inboxList.setSelectionBackground(SELECTED_BG);
        inboxList.setSelectionForeground(TEXT_PRIMARY);
        inboxList.setFixedCellHeight(75);
        inboxList.setBorder(BorderFactory.createEmptyBorder());
        inboxList.setCellRenderer(new EmailListCellRenderer());
        inboxList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) displaySelectedMessage();
        });

        JScrollPane scroll = new JScrollPane(inboxList);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setBackground(BG_SIDEBAR);
        sidebarPanel.add(scroll, BorderLayout.CENTER);

        // Refresh Button
        refreshBtn = new JButton("↻  Refresh Inbox");
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshBtn.setBackground(BG_CARD);
        refreshBtn.setForeground(PRIMARY);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                refreshBtn.setBackground(HOVER_BG);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                refreshBtn.setBackground(BG_CARD);
            }
        });
        refreshBtn.addActionListener(e -> loadInbox());

        JPanel refreshWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        refreshWrapper.setBackground(BG_SIDEBAR);
        refreshWrapper.setBorder(new EmptyBorder(20, 25, 25, 25));
        refreshWrapper.add(refreshBtn);
        sidebarPanel.add(refreshWrapper, BorderLayout.SOUTH);

        return sidebarPanel;
    }

    private JSplitPane createCenterPanel() {
        // Message Display Area with card styling
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBackground(BG_CARD);
        messageArea.setForeground(TEXT_PRIMARY);
        messageArea.setMargin(new Insets(25, 25, 25, 25));
        messageArea.setCaretColor(PRIMARY);

        JScrollPane msgScroll = new JScrollPane(messageArea);
        msgScroll.setBorder(BorderFactory.createEmptyBorder());
        msgScroll.getVerticalScrollBar().setUnitIncrement(16);
        msgScroll.getVerticalScrollBar().setBackground(BG_DARK);

        // Add a header to message area
        JPanel messageHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        messageHeader.setBackground(BG_CARD);
        messageHeader.setBorder(new EmptyBorder(15, 25, 10, 25));
        JLabel messageTitle = new JLabel("Message Content");
        messageTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        messageTitle.setForeground(TEXT_SECONDARY);
        messageHeader.add(messageTitle);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(BG_CARD);
        messagePanel.add(messageHeader, BorderLayout.NORTH);
        messagePanel.add(msgScroll, BorderLayout.CENTER);

        // Compose Area with modern styling
        JPanel composePanel = new JPanel(new GridBagLayout());
        composePanel.setBackground(BG_CARD);
        composePanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // To Field
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        toLabel.setForeground(TEXT_SECONDARY);
        toField = new JTextField();
        toField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        toField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        toField.setBackground(BG_SIDEBAR);
        toField.setForeground(TEXT_PRIMARY);
        toField.setCaretColor(PRIMARY);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        composePanel.add(toLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        composePanel.add(toField, gbc);

        // Subject Field
        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        subjectLabel.setForeground(TEXT_SECONDARY);
        subjectField = new JTextField();
        subjectField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subjectField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        subjectField.setBackground(BG_SIDEBAR);
        subjectField.setForeground(TEXT_PRIMARY);
        subjectField.setCaretColor(PRIMARY);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        composePanel.add(subjectLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        composePanel.add(subjectField, gbc);

        // Body Area
        JTextArea bodyArea = new JTextArea(6, 30);
        bodyArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        bodyArea.setBackground(BG_SIDEBAR);
        bodyArea.setForeground(TEXT_PRIMARY);
        bodyArea.setCaretColor(PRIMARY);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        composePanel.add(new JScrollPane(bodyArea), gbc);

        // Send Button with gradient effect
        sendBtn = new JButton("Send Email");
        sendBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendBtn.setBackground(SUCCESS);
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFocusPainted(false);
        sendBtn.setBorderPainted(false);
        sendBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendBtn.setPreferredSize(new Dimension(160, 45));
        sendBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sendBtn.setBackground(SUCCESS_DARK);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                sendBtn.setBackground(SUCCESS);
            }
        });
        sendBtn.addActionListener(e -> sendEmail(bodyArea.getText()));

        gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridwidth = 2;
        composePanel.add(sendBtn, gbc);

        // Wrap compose panel with border
        JPanel composeWrapper = new JPanel(new BorderLayout());
        composeWrapper.setBackground(BG_CARD);
        composeWrapper.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_COLOR));
        composeWrapper.add(composePanel, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messagePanel, composeWrapper);
        split.setResizeWeight(0.65);
        split.setDividerSize(3);
        split.setBorder(BorderFactory.createEmptyBorder());
        split.setBackground(BG_DARK);

        return split;
    }

    // Custom Cell Renderer for Email List
    private class EmailListCellRenderer extends JPanel implements ListCellRenderer<String> {
        private JLabel fromLabel, messageLabel;
        private JPanel mainPanel;

        public EmailListCellRenderer() {
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(12, 25, 12, 25));
            setBackground(BG_SIDEBAR);

            mainPanel = new JPanel(new BorderLayout());
            mainPanel.setOpaque(false);
            mainPanel.setBorder(new EmptyBorder(8, 12, 8, 12));

            fromLabel = new JLabel();
            fromLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            fromLabel.setForeground(TEXT_PRIMARY);

            messageLabel = new JLabel();
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            messageLabel.setForeground(TEXT_SECONDARY);

            mainPanel.add(fromLabel, BorderLayout.NORTH);
            mainPanel.add(messageLabel, BorderLayout.CENTER);
            add(mainPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list,
                                                      String value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(SELECTED_BG);
                mainPanel.setBackground(SELECTED_BG);
                fromLabel.setForeground(PRIMARY_LIGHT);
            } else {
                setBackground(BG_SIDEBAR);
                mainPanel.setBackground(BG_SIDEBAR);
                fromLabel.setForeground(TEXT_PRIMARY);
            }

            // Parse the display string
            if (value != null && value.startsWith("From: ")) {
                String[] parts = value.split(" \\| ", 2);
                if (parts.length == 2) {
                    fromLabel.setText(parts[0].replace("From: ", ""));
                    String msg = parts[1];
                    messageLabel.setText(msg.length() > 55 ? msg.substring(0, 55) + "..." : msg);
                }
            }

            return this;
        }
    }

    public void addClient(String username) {
        MailClient client = new MailClient(username, server);
        clients.add(client);
        clientSelector.addItem(username);
    }

    private void selectClient(int index) {
        if (index < 0 || index >= clients.size()) return;
        loadInbox();
        messageArea.setText("");
        toField.setText("");
        subjectField.setText("");
        updateStatus("Logged in as: " + clients.get(index).getUsername());
    }

    private void loadInbox() {
        inboxModel.clear();
        currentInbox.clear();
        MailClient current = getCurrentClient();
        while (current.howManyPending() > 0) {
            MailItem item = current.getNextMailItem();
            if (item != null) {
                currentInbox.add(item);
                inboxModel.addElement("From: " + item.getFrom() + " | " + item.getMessage());
            }
        }
        unreadCountLabel.setText(String.valueOf(inboxModel.size()));
        updateStatus("Inbox refreshed. " + inboxModel.size() + " messages.");
    }

    private void displaySelectedMessage() {
        int idx = inboxList.getSelectedIndex();
        if (idx == -1 || idx >= currentInbox.size()) return;
        MailItem item = currentInbox.get(idx);
        messageArea.setText("From: " + item.getFrom() + "\nTo: " + item.getTo() + "\n\n" + item.getMessage());
        messageArea.setCaretPosition(0);
    }

    private void sendEmail(String body) {
        String to = toField.getText().trim();
        if (to.isEmpty() || body.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in the 'To' and message fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE,
                    UIManager.getIcon("OptionPane.warningIcon"));
            return;
        }
        getCurrentClient().sendEmail(to, body);
        updateStatus("Email sent to " + to + " successfully!");
        toField.setText("");
        subjectField.setText("");

        // Show success message
        JOptionPane.showMessageDialog(this,
                "Email sent to " + to + " successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private MailClient getCurrentClient() {
        return clients.get(clientSelector.getSelectedIndex());
    }

    private void updateStatus(String msg) {
        statusLabel.setText(msg);
    }

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MailGUI());
    }
}