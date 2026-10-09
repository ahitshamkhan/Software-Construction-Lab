
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class TechSupport extends JFrame {

    private Responder responder;
    private JTextPane chatHistory;
    private JTextField userInput;
    private JButton sendButton;

    // Premium dark UI color palette
    private static final Color BACKGROUND = new Color(15, 23, 42);
    private static final Color PANEL = new Color(23, 32, 51);
    private static final Color CHAT_BACKGROUND = new Color(17, 25, 43);
    private static final Color ACCENT = new Color(99, 102, 241);
    private static final Color TEXT = new Color(241, 245, 249);
    private static final Color MUTED = new Color(148, 163, 184);
    private static final Color BORDER = new Color(51, 65, 85);
    private static final Color USER_BUBBLE = new Color(49, 46, 129);
    private static final Color SUPPORT_BUBBLE = new Color(30, 41, 59);

    public TechSupport() {
        responder = new Responder();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Nexus | IT Tech Support");
        setSize(900, 680);
        setMinimumSize(new Dimension(650, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 24, 20, 24));

        // ================= HEADER =================

        JPanel header = new JPanel(new BorderLayout(14, 0));
        header.setBackground(PANEL);
        header.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, BORDER),
                new EmptyBorder(18, 20, 18, 20)
        ));

        JPanel brandIcon = new JPanel(new GridBagLayout());
        brandIcon.setPreferredSize(new Dimension(48, 48));
        brandIcon.setBackground(ACCENT);
        brandIcon.setBorder(new LineBorder(ACCENT, 1, true));

        JLabel iconLabel = new JLabel("N");
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        iconLabel.setForeground(Color.WHITE);
        brandIcon.add(iconLabel);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("IT Tech Support");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Your personal technical assistant");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);
        subtitle.setBorder(new EmptyBorder(5, 0, 0, 0));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanel.add(title);
        titlePanel.add(subtitle);

        JPanel statusPanel = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 7, 0
        ));
        statusPanel.setOpaque(false);

        JPanel statusDot = new JPanel();
        statusDot.setPreferredSize(new Dimension(9, 9));
        statusDot.setBackground(new Color(52, 211, 153));
        statusDot.setBorder(new LineBorder(
                new Color(52, 211, 153), 1, true
        ));

        JLabel statusLabel = new JLabel("Support ready");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(52, 211, 153));

        statusPanel.add(statusDot);
        statusPanel.add(statusLabel);

        header.add(brandIcon, BorderLayout.WEST);
        header.add(titlePanel, BorderLayout.CENTER);
        header.add(statusPanel, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // ================= CHAT AREA =================

        JPanel conversationPanel = new JPanel(new BorderLayout());
        conversationPanel.setBackground(CHAT_BACKGROUND);
        conversationPanel.setBorder(new CompoundBorder(
                new MatteBorder(0, 1, 1, 1, BORDER),
                new EmptyBorder(18, 20, 18, 20)
        ));

        chatHistory = new JTextPane();
        chatHistory.setEditable(false);
        chatHistory.setFocusable(false);
        chatHistory.setBackground(CHAT_BACKGROUND);
        chatHistory.setForeground(TEXT);
        chatHistory.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chatHistory.setMargin(new Insets(8, 8, 8, 8));
        chatHistory.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(chatHistory);
        scrollPane.setBorder(null);
        scrollPane.setBackground(CHAT_BACKGROUND);
        scrollPane.getViewport().setBackground(CHAT_BACKGROUND);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(7, 0));
        verticalBar.setBackground(CHAT_BACKGROUND);
        verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(71, 85, 105);
                trackColor = CHAT_BACKGROUND;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        conversationPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel chatHeading = new JPanel(new BorderLayout());
        chatHeading.setBackground(CHAT_BACKGROUND);
        chatHeading.setBorder(new EmptyBorder(0, 8, 12, 8));

        JLabel conversationTitle = new JLabel("CONVERSATION");
        conversationTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        conversationTitle.setForeground(MUTED);

        JLabel encryptionLabel = new JLabel("●  Session active");
        encryptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        encryptionLabel.setForeground(new Color(52, 211, 153));

        chatHeading.add(conversationTitle, BorderLayout.WEST);
        chatHeading.add(encryptionLabel, BorderLayout.EAST);

        conversationPanel.add(chatHeading, BorderLayout.NORTH);

        JPanel chatWrapper = new JPanel(new BorderLayout());
        chatWrapper.setBackground(CHAT_BACKGROUND);
        chatWrapper.setBorder(new EmptyBorder(16, 0, 0, 0));
        chatWrapper.add(conversationPanel, BorderLayout.CENTER);

        mainPanel.add(chatWrapper, BorderLayout.CENTER);

        // ================= INPUT AREA =================

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setBackground(BACKGROUND);
        bottomPanel.setBorder(new EmptyBorder(16, 0, 0, 0));

        JPanel inputPanel = new JPanel(new BorderLayout(12, 0));
        inputPanel.setBackground(PANEL);
        inputPanel.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(8, 14, 8, 8)
        ));

        userInput = new JTextField();
        userInput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userInput.setForeground(TEXT);
        userInput.setCaretColor(TEXT);
        userInput.setBackground(PANEL);
        userInput.setBorder(null);
        userInput.setOpaque(true);
        userInput.setToolTipText("Describe your technical issue...");

        userInput.addActionListener(e -> sendMessage());

        sendButton = new JButton("Send  ➤");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(ACCENT);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(new EmptyBorder(12, 23, 12, 23));
        sendButton.setCursor(Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR
        ));
        sendButton.setOpaque(true);
        sendButton.setBorderPainted(false);

        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sendButton.setBackground(
                        new Color(79, 70, 229)
                );
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendButton.setBackground(ACCENT);
            }
        });

        sendButton.addActionListener(e -> sendMessage());

        inputPanel.add(userInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        JLabel hint = new JLabel(
                "  Press Enter to send  ·  IT assistance at your fingertips"
        );
        hint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        hint.setForeground(MUTED);

        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(hint, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Welcome message
        appendMessage(
                "System",
                "Welcome to IT Tech Support!\n\n" +
                        "I'm here to help you troubleshoot technical issues. " +
                        "Describe your problem below, and let's work through it together."
        );

        // Focus input after the window opens
        SwingUtilities.invokeLater(() -> userInput.requestFocusInWindow());
    }

    private void sendMessage() {
        String text = userInput.getText().trim();

        if (text.isEmpty()) {
            return;
        }

        appendMessage("You", text);

        String response = responder.generateResponse(text);
        appendMessage("Support", response);

        userInput.setText("");
        userInput.requestFocusInWindow();
    }

    private void appendMessage(String sender, String message) {
        StyledDocument doc = chatHistory.getStyledDocument();

        SimpleAttributeSet spacing = new SimpleAttributeSet();
        StyleConstants.setSpaceAbove(spacing, 10);
        StyleConstants.setSpaceBelow(spacing, 16);

        SimpleAttributeSet nameStyle = new SimpleAttributeSet();
        StyleConstants.setFontFamily(nameStyle, "SansSerif");
        StyleConstants.setFontSize(nameStyle, 12);
        StyleConstants.setBold(nameStyle, true);

        SimpleAttributeSet messageStyle = new SimpleAttributeSet();
        StyleConstants.setFontFamily(messageStyle, "SansSerif");
        StyleConstants.setFontSize(messageStyle, 14);
        StyleConstants.setForeground(messageStyle, TEXT);
        StyleConstants.setLineSpacing(messageStyle, 0.25f);

        if (sender.equals("You")) {
            StyleConstants.setForeground(
                    nameStyle, new Color(165, 180, 252)
            );
            StyleConstants.setBackground(messageStyle, USER_BUBBLE);
            StyleConstants.setAlignment(
                    spacing, StyleConstants.ALIGN_RIGHT
            );
        } else {
            StyleConstants.setForeground(
                    nameStyle, new Color(52, 211, 153)
            );
            StyleConstants.setBackground(messageStyle, SUPPORT_BUBBLE);
            StyleConstants.setAlignment(
                    spacing, StyleConstants.ALIGN_LEFT
            );
        }

        try {
            int start = doc.getLength();

            doc.insertString(doc.getLength(), sender + "\n", nameStyle);

            doc.insertString(
                    doc.getLength(),
                    "  " + message + "  \n\n",
                    messageStyle
            );

            doc.setParagraphAttributes(
                    start,
                    doc.getLength() - start,
                    spacing,
                    false
            );

            chatHistory.setCaretPosition(doc.getLength());

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            new TechSupport().start();
        });
    }
}