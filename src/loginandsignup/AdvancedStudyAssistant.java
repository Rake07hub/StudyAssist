package loginandsignup;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;


import javax.swing.tree.DefaultTreeModel;
import java.net.HttpURLConnection; 
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;

public class AdvancedStudyAssistant extends JFrame {
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Note> notes = new ArrayList<>();
    private JTabbedPane tabbedPane;
    private JList<Task> taskList;
    private JList<Note> noteList;
    private DefaultListModel<Task> taskListModel;
    private DefaultListModel<Note> noteListModel;
    private javax.swing.Timer studyTimer;
    private JLabel timerLabel, statusLabel;
    private long totalStudyTime = 0;
    private int completedTasks = 0;
    private Map<String, java.util.List<Task>> taskDependencies = new HashMap<>();

    // Modern color scheme
    private static final Color BACKGROUND_COLOR = new Color(18, 18, 18);    // Dark background
    private static final Color ACCENT_COLOR = new Color(94, 114, 228);      // Blue-purple accent
    private static final Color SECONDARY_BG = new Color(32, 32, 32);        // Slightly lighter dark
    private static final Color TEXT_COLOR = Color.WHITE;                    // White text
    private static final Color TEXT_SECONDARY = new Color(170, 170, 170);   // Gray text
    private static final Color SUCCESS_COLOR = new Color(45, 206, 137);     // Green
    private static final Color DANGER_COLOR = new Color(251, 99, 64);       // Red
    private static final Color BUTTON_HOVER = new Color(104, 124, 238);     // Lighter accent
    private static final Color CALENDAR_TODAY_BG = new Color(40, 45, 60);   // Today highlight
    private static final Color TASK_INDICATOR = new Color(50, 55, 70);      // Task date background
    private static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final int PADDING = 15;
    private static final int COMPONENT_SPACING = 10;

    static class Task implements Serializable {
        String description, category, priority, recurrence, dependsOn;
        LocalDateTime deadline;
        boolean isCompleted;
        int progress;

        Task(String description, LocalDateTime deadline, String priority, String category, String recurrence, String dependsOn, int progress) {
            this.description = description;
            this.deadline = deadline;
            this.priority = priority;
            this.category = category;
            this.recurrence = recurrence;
            this.dependsOn = dependsOn != null && dependsOn.isEmpty() ? null : dependsOn;
            this.isCompleted = false;
            this.progress = progress;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return String.format("[%s] %s | Due: %s | Priority: %s | Recur: %s | Dep: %s | Progress: %d%% %s",
                    category, description, deadline.format(formatter), priority, recurrence,
                    dependsOn == null ? "None" : dependsOn, progress, isCompleted ? "[Completed]" : "");
        }
    }

    static class Note implements Serializable {
        String content;
        LocalDateTime timestamp;

        Note(String content) {
            this.content = content;
            this.timestamp = LocalDateTime.now();
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return String.format("[%s] %s", timestamp.format(formatter), content);
        }
    }

    public AdvancedStudyAssistant() {
        setTitle("StudyMate Pro");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        
        // Initialize FlatLaf theme
        initializeFlatLaf();
        
        loadData();
        showSplashScreen();

        JPanel mainPanel = new JPanel(new BorderLayout());
        updateTheme(mainPanel);

        // Create a modern header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Initialize tabbed pane with modern styling
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(MAIN_FONT);
        tabbedPane.putClientProperty("JTabbedPane.tabHeight", 40);
        tabbedPane.putClientProperty("JTabbedPane.tabInsets", new Insets(8, 12, 8, 12));
        tabbedPane.putClientProperty("JTabbedPane.selectedBackground", ACCENT_COLOR);
        tabbedPane.putClientProperty("JTabbedPane.showTabSeparators", true);
        updateTheme(tabbedPane);

        // Create minimalist icons using text-based symbols
        String[] tabIcons = {
            "✓",  // Tasks
            "⏰",  // Timer
            "✎",  // Notes
            "📅",  // Calendar
            "📊",  // Stats
            "⋮",   // Task Tree
            "❓"   // AI
        };

        String[] tabTitles = {
            "Tasks",
            "Timer",
            "Notes",
            "Calendar",
            "Stats & Settings",
            "Task Tree",
            "Ask Doubts (AI)"
        };

        JPanel[] panels = {
            createTaskPanel(),
            createTimerPanel(),
            createNotePanel(),
            createCalendarPanel(),
            createStatsPanel(),
            createTaskTreePanel(),
            createDoubtPanel()
        };

        // Add tabs with text-based icons
        for (int i = 0; i < tabTitles.length; i++) {
            JLabel iconLabel = new JLabel(tabIcons[i]);
            iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setForeground(ACCENT_COLOR);
            
            JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            tabPanel.setOpaque(false);
            tabPanel.add(iconLabel);
            tabPanel.add(new JLabel(tabTitles[i]));
            
            tabbedPane.addTab(null, panels[i]);
            tabbedPane.setTabComponentAt(i, tabPanel);
        }

        // Create a modern status bar
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(MAIN_FONT);
        statusLabel.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, UIManager.getColor("Separator.foreground")),
            new EmptyBorder(8, 12, 8, 12)
        ));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });

        checkDeadlines();
    }

    private void initializeFlatLaf() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            
            // Set dark theme colors
            UIManager.put("Panel.background", BACKGROUND_COLOR);
            UIManager.put("Panel.foreground", TEXT_COLOR);
            UIManager.put("Label.foreground", TEXT_COLOR);
            UIManager.put("TextField.background", SECONDARY_BG);
            UIManager.put("TextField.foreground", TEXT_COLOR);
            UIManager.put("TextArea.background", SECONDARY_BG);
            UIManager.put("TextArea.foreground", TEXT_COLOR);
            UIManager.put("List.background", SECONDARY_BG);
            UIManager.put("List.foreground", TEXT_COLOR);
            UIManager.put("ComboBox.background", SECONDARY_BG);
            UIManager.put("ComboBox.foreground", TEXT_COLOR);
            UIManager.put("Button.arc", 999);
            UIManager.put("Component.arc", 999);
            UIManager.put("ProgressBar.arc", 999);
            UIManager.put("TextComponent.arc", 999);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.trackArc", 999);
            UIManager.put("TabbedPane.selectedBackground", ACCENT_COLOR);
            UIManager.put("TabbedPane.selectedForeground", TEXT_COLOR);
            UIManager.put("TabbedPane.foreground", TEXT_SECONDARY);
            
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAllComponentsTheme(Container container) {
        for (Component comp : container.getComponents()) {
            updateComponentTheme(comp);
            if (comp instanceof Container) {
                updateAllComponentsTheme((Container) comp);
            }
        }
    }

    private void updateComponentTheme(Component comp) {
        if (comp instanceof JPanel) {
            comp.setBackground(BACKGROUND_COLOR);
            comp.setForeground(TEXT_COLOR);
            for (Component c : ((JPanel) comp).getComponents()) {
                updateTheme(c);
            }
        } else if (comp instanceof JTextField || comp instanceof JTextArea) {
            comp.setBackground(SECONDARY_BG);
            comp.setForeground(TEXT_COLOR);
        } else if (comp instanceof JList) {
            comp.setBackground(SECONDARY_BG);
            comp.setForeground(TEXT_COLOR);
        } else if (comp instanceof JLabel) {
            comp.setForeground(TEXT_COLOR);
        } else if (comp instanceof JButton) {
            if (!((JButton) comp).getText().equals("▶") && !((JButton) comp).getText().equals("◀")) {
                comp.setBackground(ACCENT_COLOR);
                comp.setForeground(TEXT_COLOR);
            }
        }
    }

    private Color getPrimaryColor() {
        return ACCENT_COLOR;
    }

    private Color getSecondaryColor() {
        
        return SECONDARY_BG;
    }

    private String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    private void updateTheme(Component component) {
        updateComponentTheme(component);
        if (component instanceof Container) {
            for (Component c : ((Container) component).getComponents()) {
                updateTheme(c);
            }
        }
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        header.setBackground(ACCENT_COLOR);

        JLabel titleLabel = new JLabel("StudyMate Pro");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);

        header.add(titleLabel, BorderLayout.WEST);
        return header;
    }

    private JPanel createTaskPanel() {
        JPanel panel = new JPanel(new BorderLayout(COMPONENT_SPACING, COMPONENT_SPACING));
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Initialize list models if not already done
        if (taskListModel == null) {
            taskListModel = new DefaultListModel<>();
            noteListModel = new DefaultListModel<>();
            taskList = new JList<>(taskListModel);
            noteList = new JList<>(noteListModel);
            updateTaskList();
            updateNoteList();
            buildTaskDependencies();
        }

        // Task list with modern styling
        taskList.setCellRenderer(new TaskCellRenderer());
        taskList.setFont(MAIN_FONT);
        taskList.setFixedCellHeight(50);
        taskList.setSelectionBackground(ACCENT_COLOR);
        taskList.setSelectionForeground(TEXT_COLOR);

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1));
        scrollPane.setBackground(SECONDARY_BG);

        // Search panel with modern styling
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, PADDING, 0));

        JTextField searchField = new JTextField(20);
        searchField.setFont(MAIN_FONT);
        searchField.putClientProperty("JTextField.placeholderText", "Search tasks...");
        searchField.putClientProperty("JTextField.showClearButton", true);

        JComboBox<String> filterCombo = new JComboBox<>(new String[]{"All", "Active", "Completed"});
        filterCombo.setFont(MAIN_FONT);
        filterCombo.setPreferredSize(new Dimension(120, 30));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(MAIN_FONT);
        searchButton.putClientProperty("JButton.buttonType", "roundRect");

        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(filterCombo);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchButton);

        // Button panel with modern styling
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, 0, 0, 0));

        String[][] buttons = {
            {"Add Task", "primary"},
            {"Update Progress", "default"},
            {"Mark Complete", "success"},
            {"Delete Task", "danger"},
            {"Sort by Deadline", "default"},
            {"Backup Tasks", "default"},
            {"Restore Tasks", "default"},
            {"Show Dependencies", "default"}
        };

        for (String[] buttonInfo : buttons) {
            JButton button = new JButton(buttonInfo[0]);
            button.setFont(MAIN_FONT);
            button.putClientProperty("JButton.buttonType", "roundRect");
            
            switch (buttonInfo[1]) {
                case "primary":
                    button.putClientProperty("JButton.buttonType", "roundRect");
                    button.putClientProperty("JButton.selectedBackground", ACCENT_COLOR);
                    break;
                case "success":
                    button.putClientProperty("JButton.buttonType", "roundRect");
                    button.putClientProperty("JButton.selectedBackground", SUCCESS_COLOR);
                    break;
                case "danger":
                    button.putClientProperty("JButton.buttonType", "roundRect");
                    button.putClientProperty("JButton.selectedBackground", DANGER_COLOR);
                    break;
            }
            
            buttonPanel.add(button);
            buttonPanel.add(Box.createHorizontalStrut(10));

            // Add action listeners
            switch (buttonInfo[0]) {
                case "Add Task": button.addActionListener(e -> addTask()); break;
                case "Update Progress": button.addActionListener(e -> updateProgress()); break;
                case "Mark Complete": button.addActionListener(e -> markTaskComplete()); break;
                case "Delete Task": button.addActionListener(e -> deleteTask()); break;
                case "Sort by Deadline": button.addActionListener(e -> sortTasks()); break;
                case "Backup Tasks": button.addActionListener(e -> backupTasks()); break;
                case "Restore Tasks": button.addActionListener(e -> restoreTasks()); break;
                case "Show Dependencies": button.addActionListener(e -> showDependencies()); break;
            }
        }

        // Add components to main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private class TaskCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout(10, 0));
            panel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            panel.setBackground(isSelected ? ACCENT_COLOR : SECONDARY_BG);
            
            if (value instanceof Task) {
                Task task = (Task) value;
                
                JLabel mainLabel = new JLabel("<html><b>" + task.description + "</b><br>" +
                    "<font color='#aaaaaa'>Due: " + task.deadline.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")) + "</font></html>");
                mainLabel.setFont(MAIN_FONT);
                mainLabel.setForeground(TEXT_COLOR);
                
                JProgressBar progress = new JProgressBar(0, 100);
                progress.setValue(task.progress);
                progress.setStringPainted(true);
                progress.setString(task.progress + "%");
                progress.setPreferredSize(new Dimension(100, 20));
                progress.setBackground(SECONDARY_BG);
                progress.setForeground(SUCCESS_COLOR);
                
                JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                rightPanel.setOpaque(false);
                rightPanel.add(progress);
                
                panel.add(mainLabel, BorderLayout.CENTER);
                panel.add(rightPanel, BorderLayout.EAST);
            }
            
            return panel;
        }
    }

    private void showSplashScreen() {
        JWindow splash = new JWindow();
        JPanel splashPanel = new JPanel(new BorderLayout());
        splashPanel.setBackground(Color.WHITE);
        
        JLabel splashLabel = new JLabel("StudyMate Pro", SwingConstants.CENTER);
        splashLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        splashLabel.setForeground(ACCENT_COLOR);
        
        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loadingLabel.setForeground(UIManager.getColor("Label.disabledText"));
        
        splashPanel.add(splashLabel, BorderLayout.CENTER);
        splashPanel.add(loadingLabel, BorderLayout.SOUTH);
        splashPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        splash.getContentPane().add(splashPanel);
        splash.setBounds(500, 300, 400, 150);
        splash.setVisible(true);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        splash.dispose();
    }

    private JPanel createTimerPanel() {
        JPanel panel = new JPanel(new BorderLayout(COMPONENT_SPACING, COMPONENT_SPACING));
        panel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Timer display panel
        JPanel timerDisplayPanel = new JPanel(new GridBagLayout());
        timerDisplayPanel.setBorder(new CompoundBorder(
            new MatteBorder(1, 1, 1, 1, UIManager.getColor("Component.borderColor")),
            new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(COMPONENT_SPACING, COMPONENT_SPACING, COMPONENT_SPACING, COMPONENT_SPACING);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Timer label with modern styling
        timerLabel = new JLabel("00:00");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setForeground(ACCENT_COLOR);

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, COMPONENT_SPACING, 0));
        
        JSpinner minutesSpinner = new JSpinner(new SpinnerNumberModel(25, 1, 120, 1));
        minutesSpinner.setFont(MAIN_FONT);
        minutesSpinner.setPreferredSize(new Dimension(80, 30));
        ((JSpinner.DefaultEditor) minutesSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);

        JLabel minutesLabel = new JLabel("minutes");
        minutesLabel.setFont(MAIN_FONT);
        minutesLabel.setBorder(new EmptyBorder(0, 5, 0, 15));

        // Control buttons with modern styling
        JButton startButton = new JButton("Start");
        startButton.putClientProperty("JButton.buttonType", "roundRect");
        startButton.putClientProperty("JButton.selectedBackground", ACCENT_COLOR);
        
        JButton stopButton = new JButton("Stop");
        stopButton.putClientProperty("JButton.buttonType", "roundRect");
        stopButton.putClientProperty("JButton.selectedBackground", DANGER_COLOR);
        
        JButton pomodoroButton = new JButton("Pomodoro (25/5)");
        pomodoroButton.putClientProperty("JButton.buttonType", "roundRect");
        pomodoroButton.putClientProperty("JButton.selectedBackground", ACCENT_COLOR);

        for (JButton button : new JButton[]{startButton, stopButton, pomodoroButton}) {
            button.setFont(MAIN_FONT);
            button.setFocusPainted(false);
        }

        inputPanel.add(minutesSpinner);
        inputPanel.add(minutesLabel);
        inputPanel.add(startButton);
        inputPanel.add(stopButton);
        inputPanel.add(pomodoroButton);

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 10));
        statsPanel.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, UIManager.getColor("Component.borderColor")),
            new EmptyBorder(20, 0, 0, 0)
        ));

        // Create stat cards
        String[][] stats = {
            {"Total Study Time", formatTime(totalStudyTime)},
            {"Completed Tasks", String.valueOf(completedTasks)},
            {"Average Session", completedTasks > 0 ? formatTime(totalStudyTime / completedTasks) : "N/A"},
            {"Current Streak", "0 days"}  // You can implement streak tracking
        };

        for (String[] stat : stats) {
            JPanel statCard = new JPanel(new BorderLayout(5, 5));
            statCard.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")),
                new EmptyBorder(10, 15, 10, 15)
            ));
            
            JLabel titleLabel = new JLabel(stat[0]);
            titleLabel.setFont(MAIN_FONT);
            titleLabel.setForeground(TEXT_SECONDARY);
            
            JLabel valueLabel = new JLabel(stat[1]);
            valueLabel.setFont(HEADER_FONT);
            valueLabel.setForeground(ACCENT_COLOR);
            
            statCard.add(titleLabel, BorderLayout.NORTH);
            statCard.add(valueLabel, BorderLayout.CENTER);
            statsPanel.add(statCard);
        }

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        timerDisplayPanel.add(timerLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, COMPONENT_SPACING, COMPONENT_SPACING, COMPONENT_SPACING);
        timerDisplayPanel.add(inputPanel, gbc);

        panel.add(timerDisplayPanel, BorderLayout.CENTER);
        panel.add(statsPanel, BorderLayout.SOUTH);

        // Add action listeners
        startButton.addActionListener(e -> startTimer(String.valueOf(minutesSpinner.getValue()), false));
        stopButton.addActionListener(e -> stopTimer());
        pomodoroButton.addActionListener(e -> startPomodoro());

        return panel;
    }

    private JPanel createNotePanel() {
        JPanel panel = new JPanel(new BorderLayout(COMPONENT_SPACING, COMPONENT_SPACING));
        updateTheme(panel);
        panel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        JScrollPane scrollPane = new JScrollPane(noteList);
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        scrollPane.setBackground(SECONDARY_BG);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, COMPONENT_SPACING, COMPONENT_SPACING));
        updateTheme(topPanel);
        
        JTextField searchField = new JTextField(20);
        searchField.setFont(MAIN_FONT);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JButton searchButton = createStyledButton("Search", ACCENT_COLOR);
        searchButton.addActionListener(e -> searchNotes(searchField.getText()));
        
        JLabel searchLabel = new JLabel("Search Notes:");
        searchLabel.setFont(MAIN_FONT);
        searchLabel.setForeground(TEXT_SECONDARY);
        
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, COMPONENT_SPACING, COMPONENT_SPACING));
        updateTheme(buttonPanel);

        JButton addButton = createStyledButton("Add Note", ACCENT_COLOR);
        JButton deleteButton = createStyledButton("Delete Note", DANGER_COLOR);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> addNote());
        deleteButton.addActionListener(e -> deleteNote());

        // Custom cell renderer for notes
        noteList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Note) {
                    Note note = (Note) value;
                    label.setText("<html><div style='padding: 5px;'>" + note.toString() + "</div></html>");
                    label.setFont(MAIN_FONT);
                    label.setForeground(TEXT_SECONDARY);
                    if (!isSelected) {
                        label.setBackground(getSecondaryColor());
                    }
                }
                return label;
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel(new BorderLayout(COMPONENT_SPACING, COMPONENT_SPACING));
        updateTheme(panel);
        panel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Calendar Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        updateTheme(headerPanel);
        headerPanel.setBorder(new EmptyBorder(0, 0, COMPONENT_SPACING, 0));

        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(TITLE_FONT);
        monthLabel.setForeground(ACCENT_COLOR);
        monthLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Navigation panel
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, COMPONENT_SPACING, 0));
        updateTheme(navigationPanel);
        
        JButton prevButton = new JButton("◀");
        prevButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        prevButton.setForeground(ACCENT_COLOR);
        prevButton.setBackground(null);
        prevButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        prevButton.setFocusPainted(false);
        prevButton.setContentAreaFilled(false);
        
        JButton nextButton = new JButton("▶");
        nextButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        nextButton.setForeground(ACCENT_COLOR);
        nextButton.setBackground(null);
        nextButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        nextButton.setFocusPainted(false);
        nextButton.setContentAreaFilled(false);

        JButton todayButton = createStyledButton("Today", ACCENT_COLOR);
        
        navigationPanel.add(prevButton);
        navigationPanel.add(todayButton);
        navigationPanel.add(nextButton);

        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(navigationPanel, BorderLayout.SOUTH);

        // Calendar Grid
        JPanel calendarGrid = new JPanel(new GridLayout(7, 7, 4, 4));
        calendarGrid.setBackground(BACKGROUND_COLOR);
        calendarGrid.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Day headers
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(HEADER_FONT);
            dayLabel.setForeground(TEXT_SECONDARY);
            calendarGrid.add(dayLabel);
        }

        // Date buttons
        JButton[] dateButtons = new JButton[42];
        for (int i = 0; i < 42; i++) {
            dateButtons[i] = new JButton();
            dateButtons[i].setFont(MAIN_FONT);
            dateButtons[i].setFocusPainted(false);
            dateButtons[i].setBorderPainted(true);
            dateButtons[i].setContentAreaFilled(false);
            dateButtons[i].setOpaque(true);
            dateButtons[i].setBackground(SECONDARY_BG);
            dateButtons[i].setForeground(TEXT_COLOR);
            calendarGrid.add(dateButtons[i]);
        }

        // Task Panel
        JPanel taskPanel = new JPanel(new BorderLayout(COMPONENT_SPACING, COMPONENT_SPACING));
        updateTheme(taskPanel);
        taskPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, ACCENT_COLOR),
            new EmptyBorder(PADDING, 0, 0, 0)
        ));

        JLabel taskLabel = new JLabel("Tasks for Selected Date");
        taskLabel.setFont(HEADER_FONT);
        taskLabel.setForeground(ACCENT_COLOR);

        JTextArea taskArea = new JTextArea(5, 30);
        taskArea.setFont(MAIN_FONT);
        taskArea.setLineWrap(true);
        taskArea.setWrapStyleWord(true);
        taskArea.setBackground(SECONDARY_BG);
        taskArea.setForeground(TEXT_COLOR);
        taskArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        JScrollPane taskScroll = new JScrollPane(taskArea);
        taskScroll.setBorder(BorderFactory.createEmptyBorder());
        taskScroll.getViewport().setBackground(SECONDARY_BG);
        
        JButton addTaskButton = createStyledButton("Add Task", ACCENT_COLOR);
        
        taskPanel.add(taskLabel, BorderLayout.NORTH);
        taskPanel.add(taskScroll, BorderLayout.CENTER);
        taskPanel.add(addTaskButton, BorderLayout.SOUTH);

        // Main layout
        JPanel centerPanel = new JPanel(new BorderLayout(COMPONENT_SPACING, COMPONENT_SPACING));
        updateTheme(centerPanel);
        centerPanel.add(calendarGrid, BorderLayout.CENTER);
        centerPanel.add(taskPanel, BorderLayout.SOUTH);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Calendar Logic
        Calendar calendar = Calendar.getInstance();
        updateCalendar(calendar, dateButtons, monthLabel);

        // Event Handlers
        prevButton.addActionListener(e -> {
            calendar.add(Calendar.MONTH, -1);
            updateCalendar(calendar, dateButtons, monthLabel);
        });

        nextButton.addActionListener(e -> {
            calendar.add(Calendar.MONTH, 1);
            updateCalendar(calendar, dateButtons, monthLabel);
        });

        todayButton.addActionListener(e -> {
            calendar.setTime(new Date());
            updateCalendar(calendar, dateButtons, monthLabel);
        });

        // Date button click handler
        for (JButton dateButton : dateButtons) {
            dateButton.addActionListener(e -> {
                if (!dateButton.getText().isEmpty()) {
                    // Reset all button backgrounds
                    for (JButton btn : dateButtons) {
                        if (!btn.getText().isEmpty()) {
                            if (btn.getBackground().equals(CALENDAR_TODAY_BG)) {
                                continue; // Don't reset today's background
                            }
                            btn.setBackground(SECONDARY_BG);
                            btn.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1));
                        }
                    }
                    
                    // Highlight selected date
                    if (!dateButton.getBackground().equals(CALENDAR_TODAY_BG)) {
                        dateButton.setBackground(ACCENT_COLOR);
                        dateButton.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 2));
                    }
                    
                    String selectedDate = monthLabel.getText() + " " + dateButton.getText();
                    taskLabel.setText("Tasks for " + dateButton.getText() + " " + monthLabel.getText());
                    showTasksForDate(selectedDate, taskArea);
                }
            });
        }

        // Add Task button handler
        addTaskButton.addActionListener(e -> {
            String taskText = taskArea.getText().trim();
            if (!taskText.isEmpty()) {
                String selectedDate = monthLabel.getText() + " " + taskLabel.getText().split(" ")[2];
                addTaskForDate(selectedDate, taskText);
                taskArea.setText("");
                updateCalendar(calendar, dateButtons, monthLabel);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a task description", 
                    "Empty Task", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        return panel;
    }

    private void updateCalendar(Calendar calendar, JButton[] dateButtons, JLabel monthLabel) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        // Update month label with a more attractive format
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(monthFormat.format(calendar.getTime()));

        // Clear all buttons and set default styling
        for (JButton dateButton : dateButtons) {
            dateButton.setText("");
            dateButton.setBackground(SECONDARY_BG);
            dateButton.setForeground(TEXT_COLOR);
            dateButton.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1));
        }

        // Fill in dates with improved styling
        Calendar today = Calendar.getInstance();
        for (int i = 0; i < daysInMonth; i++) {
            JButton dateButton = dateButtons[firstDayOfMonth + i];
            dateButton.setText(String.valueOf(i + 1));
            
            // Style weekends differently
            int dayOfWeek = (firstDayOfMonth + i) % 7;
            if (dayOfWeek == 0 || dayOfWeek == 6) {
                dateButton.setForeground(TEXT_SECONDARY);
            }
            
            // Highlight today
            if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                (i + 1) == today.get(Calendar.DAY_OF_MONTH)) {
                dateButton.setBackground(CALENDAR_TODAY_BG);
                dateButton.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 2));
                dateButton.setForeground(ACCENT_COLOR);
            }
            
            // Add visual indicator for dates with tasks
            if (hasTasksForDate(monthLabel.getText() + " " + (i + 1))) {
                if (!dateButton.getBackground().equals(CALENDAR_TODAY_BG)) {
                    dateButton.setBackground(TASK_INDICATOR);
                    dateButton.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
                }
            }

            // Add hover effect
            dateButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!dateButton.getBackground().equals(CALENDAR_TODAY_BG)) {
                        Color currentBg = dateButton.getBackground();
                        dateButton.setBackground(new Color(
                            Math.min(255, currentBg.getRed() + 20),
                            Math.min(255, currentBg.getGreen() + 20),
                            Math.min(255, currentBg.getBlue() + 20)
                        ));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!dateButton.getBackground().equals(CALENDAR_TODAY_BG)) {
                        if (hasTasksForDate(monthLabel.getText() + " " + dateButton.getText())) {
                            dateButton.setBackground(TASK_INDICATOR);
                        } else {
                            dateButton.setBackground(SECONDARY_BG);
                        }
                    }
                }
            });
        }
    }

    private void showTasksForDate(String date, JTextArea taskArea) {
        StringBuilder tasksText = new StringBuilder();
        for (Task task : tasks) {
            if (task.deadline != null) {
                String taskDate = task.deadline.format(DateTimeFormatter.ofPattern("MMMM yyyy d"));
                if (taskDate.equals(date)) {
                    tasksText.append("• ").append(task.description).append("\n");
                }
            }
        }
        taskArea.setText(tasksText.toString());
    }

    private boolean hasTasksForDate(String date) {
        for (Task task : tasks) {
            if (task.deadline != null) {
                String taskDate = task.deadline.format(DateTimeFormatter.ofPattern("MMMM yyyy d"));
                if (taskDate.equals(date)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addTaskForDate(String selectedDate, String taskText) {
        try {
            // Parse the selected date in the format "MMMM yyyy d"
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy d");
            LocalDateTime deadline = LocalDateTime.parse(selectedDate + " 00:00", 
                DateTimeFormatter.ofPattern("MMMM yyyy d HH:mm"));
            Task newTask = new Task(taskText, deadline, "Medium", "Calendar", "None", null, 0);
            tasks.add(newTask);
            taskListModel.addElement(newTask);
            updateTaskList();
            JOptionPane.showMessageDialog(this, "Task added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid task. Format: Task description\nDate is automatically set to: " + selectedDate, 
                "Task Format Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(COMPONENT_SPACING, COMPONENT_SPACING));
        updateTheme(panel);
        panel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Stats Section
        JPanel statsSection = new JPanel(new GridLayout(3, 1, 10, 10));
        updateTheme(statsSection);
        statsSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            "Statistics",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            HEADER_FONT,
            ACCENT_COLOR
        ));

        // Create stat cards with modern styling
        JPanel[] statCards = new JPanel[3];
        String[][] stats = {
            {"Total Study Time", formatTime(totalStudyTime)},
            {"Completed Tasks", String.valueOf(completedTasks)},
            {"Average Session", completedTasks > 0 ? formatTime(totalStudyTime / completedTasks) : "N/A"}
        };

        for (int i = 0; i < stats.length; i++) {
            statCards[i] = new JPanel(new BorderLayout(5, 5));
            statCards[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)
            ));
            updateTheme(statCards[i]);

            JLabel titleLabel = new JLabel(stats[i][0]);
            titleLabel.setFont(MAIN_FONT);
            titleLabel.setForeground(TEXT_SECONDARY);

            JLabel valueLabel = new JLabel(stats[i][1]);
            valueLabel.setFont(HEADER_FONT);
            valueLabel.setForeground(ACCENT_COLOR);

            statCards[i].add(titleLabel, BorderLayout.NORTH);
            statCards[i].add(valueLabel, BorderLayout.CENTER);
            statsSection.add(statCards[i]);
        }

        panel.add(statsSection, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTaskTreePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        updateTheme(panel);
        panel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Tasks");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        JTree taskTree = new JTree(treeModel);
        taskTree.setFont(MAIN_FONT);
        taskTree.setBackground(SECONDARY_BG);
        taskTree.setForeground(TEXT_COLOR);

        buildTaskTree(root);

        JScrollPane scrollPane = new JScrollPane(taskTree);
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        scrollPane.setBackground(SECONDARY_BG);
        scrollPane.getViewport().setBackground(SECONDARY_BG);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        updateTheme(buttonPanel);
        JButton refreshButton = createStyledButton("Refresh Tree", ACCENT_COLOR);
        refreshButton.addActionListener(e -> {
            root.removeAllChildren();
            buildTaskTree(root);
            treeModel.reload();
        });
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void buildTaskTree(DefaultMutableTreeNode root) {
        taskDependencies.clear();
        buildTaskDependencies();
        for (Task task : tasks) {
            DefaultMutableTreeNode taskNode = new DefaultMutableTreeNode(task.description);
            root.add(taskNode);
            if (taskDependencies.containsKey(task.description)) {
                for (Task dependentTask : taskDependencies.get(task.description)) {
                    DefaultMutableTreeNode dependentNode = new DefaultMutableTreeNode(dependentTask.description + " (Depends On)");
                    taskNode.add(dependentNode);
                }
            }
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(MAIN_FONT);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setMargin(new Insets(5, 10, 5, 10));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private void addTask() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField descField = new JTextField(20);
        JTextField dateField = new JTextField(16);
        String[] priorities = {"Low", "Medium", "High"};
        JComboBox<String> priorityCombo = new JComboBox<>(priorities);
        String[] categories = {"Work", "School", "Personal"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        String[] recurrences = {"None", "Daily", "Weekly", "Monthly"};
        JComboBox<String> recurrenceCombo = new JComboBox<>(recurrences);
        JComboBox<String> dependsCombo = new JComboBox<>(getTaskDescriptions());
        dependsCombo.insertItemAt("None", 0); // Add "None" as the first option
        dependsCombo.setSelectedIndex(0);
        JSlider progressSlider = new JSlider(0, 100, 0);

        descField.setFont(MAIN_FONT);
        dateField.setFont(MAIN_FONT);
        priorityCombo.setFont(MAIN_FONT);
        categoryCombo.setFont(MAIN_FONT);
        recurrenceCombo.setFont(MAIN_FONT);
        dependsCombo.setFont(MAIN_FONT);

        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Deadline (yyyy-MM-dd HH:mm):"));
        panel.add(dateField);
        panel.add(new JLabel("Priority:"));
        panel.add(priorityCombo);
        panel.add(new JLabel("Category:"));
        panel.add(categoryCombo);
        panel.add(new JLabel("Recurrence:"));
        panel.add(recurrenceCombo);
        panel.add(new JLabel("Depends On:"));
        panel.add(dependsCombo);
        panel.add(new JLabel("Progress (0-100%):"));
        panel.add(progressSlider);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Task", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime deadline = LocalDateTime.parse(dateField.getText(), formatter);
                String dependsOn = (String) dependsCombo.getSelectedItem();
                if (dependsOn.equals("None")) {
                    dependsOn = null; // Store null if "None" is selected
                }
                tasks.add(new Task(descField.getText(), deadline, (String) priorityCombo.getSelectedItem(),
                        (String) categoryCombo.getSelectedItem(), (String) recurrenceCombo.getSelectedItem(),
                        dependsOn, progressSlider.getValue()));
                updateTaskList();
                buildTaskDependencies(); //update dependencies
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format!");
            }
        }
    }

    private void updateProgress() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            Task task = tasks.get(index);
            JSlider progressSlider = new JSlider(0, 100, task.progress);
            int result = JOptionPane.showConfirmDialog(this, progressSlider, "Update Progress",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                task.progress = progressSlider.getValue();
                if (task.progress == 100) {
                    task.isCompleted = true;
                }
                updateTaskList();
                if (task.isCompleted) {
                    completedTasks++;
                }
                updateStats();
            }
        }
    }

    private void markTaskComplete() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            Task task = tasks.get(index);
            if (task.dependsOn != null && !isDependencyComplete(task.dependsOn)) {
                JOptionPane.showMessageDialog(this, "Complete dependency '" + task.dependsOn + "' first!");
                return;
            }
            task.isCompleted = true;
            task.progress = 100;
            completedTasks++;
            handleRecurrence(task);
            updateTaskList();
            updateStats();
            notifyUser("Task '" + task.description + "' completed!");
        }
    }

    private void deleteTask() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            tasks.remove(index);
            updateTaskList();
            buildTaskDependencies(); //update dependencies
        }
    }

    private void sortTasks() {
        Collections.sort(tasks, Comparator.comparing(t -> t.deadline));
        updateTaskList();
    }

    private void searchTasks(String query) {
        taskListModel.clear();
        for (Task task : tasks) {
            if (task.description.toLowerCase().contains(query.toLowerCase()) ||
                    task.category.toLowerCase().contains(query.toLowerCase())) {
                taskListModel.addElement(task);
            }
        }
    }

    private void searchNotes(String query) {
        noteListModel.clear();
        for (Note note : notes) {
            if (note.content.toLowerCase().contains(query.toLowerCase())) {
                noteListModel.addElement(note);
            }
        }
    }

    private void startTimer(String minutesStr, boolean isPomodoro) {
        try {
            int minutes = Integer.parseInt(minutesStr);
            if (studyTimer != null) {
                studyTimer.stop();
            }

            final int[] secondsLeft = {minutes * 60};
            final boolean[] isWorkPhase = {isPomodoro};
            studyTimer = new javax.swing.Timer(1000, e -> {
                secondsLeft[0]--;
                int min = secondsLeft[0] / 60;
                int sec = secondsLeft[0] % 60;
                timerLabel.setText(String.format("%02d:%02d", min, sec));
                if (secondsLeft[0] <= 0) {
                    studyTimer.stop();
                    totalStudyTime += minutes * 60;
                    if (isPomodoro && isWorkPhase[0]) {
                        notifyUser("Work phase complete! Starting 5-min break.");
                        startTimer("5", false);
                        isWorkPhase[0] = false;
                    } else {
                        JOptionPane.showMessageDialog(this, isPomodoro ? "Pomodoro cycle complete!" : "Study session complete!");
                        updateStats();
                        notifyUser("Timer finished!");
                    }
                }
            });
            studyTimer.start();
            statusLabel.setText(isPomodoro ? "Pomodoro Work Phase" : "Timer Running");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid minutes!");
        }
    }

    private void startPomodoro() {
        startTimer("25", true);
    }

    private void stopTimer() {
        if (studyTimer != null) {
            studyTimer.stop();
            timerLabel.setText("00:00");
            statusLabel.setText("Timer Stopped");
        }
    }

    private void addNote() {
        String note = JOptionPane.showInputDialog(this, "Enter note:");
        if (note != null && !note.trim().isEmpty()) {
            notes.add(new Note(note));
            updateNoteList();
        }
    }

    private void deleteNote() {
        int index = noteList.getSelectedIndex();
        if (index >= 0) {
            notes.remove(index);
            updateNoteList();
        }
    }

    private void backupTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("backup.dat"))) {
            oos.writeObject(tasks);
            JOptionPane.showMessageDialog(this, "Tasks backed up successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Backup failed: " + e.getMessage());
        }
    }

    private void restoreTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("backup.dat"))) {
            tasks = (ArrayList<Task>) ois.readObject();
            updateTaskList();
            buildTaskDependencies();  //rebuild
            JOptionPane.showMessageDialog(this, "Tasks restored successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Restore failed: " + e.getMessage());
        }
    }

    private void updateTaskList() {
        taskListModel.clear();
        for (Task task : tasks) {
            taskListModel.addElement(task);
        }
    }

    private void updateNoteList() {
        noteListModel.clear();
        for (Note note : notes) {
            noteListModel.addElement(note);
        }
    }

    private void updateStats() {
        JPanel statsPanel = (JPanel) tabbedPane.getComponentAt(4);
        if (statsPanel == null) return; //check if the stats panel exists
        statsPanel.removeAll();
        statsPanel.setLayout(new GridLayout(5, 1, 10, 10)); // Use GridLayout
        JLabel tasksLabel = new JLabel("Completed Tasks: " + completedTasks);
        JLabel timeLabel = new JLabel("Total Study Time: " + formatTime(totalStudyTime));
        JLabel avgLabel = new JLabel("Avg. Time per Task: " +
                (completedTasks > 0 ? formatTime(totalStudyTime / completedTasks) : "N/A"));
        JCheckBox themeToggle = new JCheckBox("Dark Mode", true);
        themeToggle.setFont(MAIN_FONT);

        tasksLabel.setFont(MAIN_FONT);
        timeLabel.setFont(MAIN_FONT);
        avgLabel.setFont(MAIN_FONT);

        statsPanel.add(tasksLabel);
        statsPanel.add(timeLabel);
        statsPanel.add(avgLabel);
        statsPanel.add(themeToggle);

        themeToggle.addActionListener(e -> {
            updateTheme(getContentPane());
            SwingUtilities.updateComponentTreeUI(this);
            repaint();
            saveSettings();
        });
        statsPanel.revalidate();
        statsPanel.repaint();
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("study_data.dat"))) {
            oos.writeObject(tasks);
            oos.writeObject(notes);
            oos.writeLong(totalStudyTime);
            oos.writeInt(completedTasks);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        File dataFile = new File("study_data.dat");
        if (!dataFile.exists()) {
            return; // No data file yet, start with defaults.
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            tasks = (ArrayList<Task>) ois.readObject();
            notes = (ArrayList<Note>) ois.readObject();
            totalStudyTime = ois.readLong();
            completedTasks = ois.readInt();
        } catch (FileNotFoundException e) {
            // No data file yet, start with defaults.
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private void saveSettings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("settings.dat"))) {
            oos.writeBoolean(true);
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    private void loadSettings() {
        File settingsFile = new File("settings.dat");
        if (!settingsFile.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("settings.dat"))) {
            // No need to read darkMode, as we're keeping the dark theme
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading settings: " + e.getMessage());
        }
    }

    private boolean isDependencyComplete(String dependsOn) {
        for (Task task : tasks) {
            if (task.description.equals(dependsOn) && task.isCompleted) {
                return true;
            }
        }
        return false;
    }

    private void handleRecurrence(Task task) {
        if (!task.recurrence.equals("None")) {
            LocalDateTime newDeadline = task.deadline;
            switch (task.recurrence) {
                case "Daily":
                    newDeadline = newDeadline.plusDays(1);
                    break;
                case "Weekly":
                    newDeadline = newDeadline.plusWeeks(1);
                    break;
                case "Monthly":
                    newDeadline = newDeadline.plusMonths(1);
                    break;
            }
            tasks.add(new Task(task.description, newDeadline, task.priority, task.category,
                    task.recurrence, task.dependsOn, 0));
        }
    }

    private void checkDeadlines() {
        javax.swing.Timer deadlineTimer = new javax.swing.Timer(60000, e -> {
            LocalDateTime now = LocalDateTime.now();
            tasks.stream()
                    .filter(t -> !t.isCompleted && t.deadline.isBefore(now.plusMinutes(10)))
                    .forEach(t -> notifyUser("Reminder: '" + t.description + "' due soon!"));
        });
        deadlineTimer.start();
    }

    private void notifyUser(String message) {
        statusLabel.setText(message);
        JOptionPane.showMessageDialog(this, message);
    }

    private String[] getTaskDescriptions() {
        String[] descriptions = new String[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            descriptions[i] = tasks.get(i).description;
        }
        return descriptions;
    }

    private void buildTaskDependencies() {
        taskDependencies.clear();
        for (Task task : tasks) {
            if (task.dependsOn != null) {
                if (!taskDependencies.containsKey(task.dependsOn)) {
                    taskDependencies.put(task.dependsOn, new ArrayList<>());
                }
                taskDependencies.get(task.dependsOn).add(task);
            }
        }
    }

    private void showDependencies() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            Task selectedTask = tasks.get(index);
            String taskName = selectedTask.description;
            if (taskDependencies.containsKey(taskName)) {
                StringBuilder message = new StringBuilder("Tasks depending on '" + taskName + "':\n");
                for (Task dependentTask : taskDependencies.get(taskName)) {
                    message.append("- ").append(dependentTask.description).append("\n");
                }
                JOptionPane.showMessageDialog(this, message.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No tasks depend on '" + taskName + "'");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to show its dependencies.");
        }
    }
    
    private String loadAPIKey() {
        File keyFile = new File("C:/Projects/study assistant/study assistant/src/loginandsignup/openai_key.txt");
        if (!keyFile.exists()) {
            System.out.println("Key file not found at: " + keyFile.getAbsolutePath());
            return "";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(keyFile))) {
            String key = br.readLine();
            if (key != null && !key.trim().isEmpty()) {
                return key.trim(); // Trim to remove newline
            } else {
                System.out.println("Key file is empty!");
                return "";
            }
        } catch (IOException e) {
            System.out.println("Failed to read key file: " + e.getMessage());
            return "";
        }
    }

    private JPanel createDoubtPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        updateTheme(panel);

        // Header with title and subtitle
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Study Assistant AI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(getPrimaryColor());
        
        JLabel subtitleLabel = new JLabel("Ask any study-related questions", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(UIManager.getColor("Label.disabledText"));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Chat area with scroll pane
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(UIManager.getColor("Panel.background"));
        
        // Create a wrapper panel to center the chat content
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(UIManager.getColor("Panel.background"));
        wrapperPanel.add(chatPanel, BorderLayout.CENTER);
        
        JScrollPane chatScroll = new JScrollPane(wrapperPanel);
        chatScroll.setBorder(BorderFactory.createEmptyBorder());
        chatScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatScroll.getVerticalScrollBar().setUnitIncrement(16);
        chatScroll.putClientProperty("JScrollPane.smoothScrolling", true);

        // Input area
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        inputPanel.setBackground(UIManager.getColor("Panel.background"));

        JTextArea inputArea = new JTextArea(3, 30);
        inputArea.setFont(MAIN_FONT);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        inputArea.putClientProperty("JTextArea.placeholderText", "Type your question here...");
        
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setBorder(BorderFactory.createEmptyBorder());
        inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton sendButton = new JButton("Send");
        sendButton.setFont(MAIN_FONT);
        sendButton.putClientProperty("JButton.buttonType", "roundRect");
        sendButton.setPreferredSize(new Dimension(100, 40));
        sendButton.setBackground(getPrimaryColor());
        sendButton.setForeground(Color.WHITE);

        inputPanel.add(inputScroll, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add components to main panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(chatScroll, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // Add welcome message
        addMessage(chatPanel, "assistant", "Hello! I'm your Study Assistant. How can I help you today?");

        // Action listener for send button
        sendButton.addActionListener(e -> {
            String question = inputArea.getText().trim();
            if (!question.isEmpty()) {
                // Add user message
                addMessage(chatPanel, "user", question);
                inputArea.setText("");
                
                // Show typing indicator
                JPanel typingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                typingPanel.setOpaque(false);
                JLabel typingLabel = new JLabel("Assistant is typing...");
                typingLabel.setFont(MAIN_FONT);
                typingLabel.setForeground(UIManager.getColor("Label.disabledText"));
                typingPanel.add(typingLabel);
                chatPanel.add(typingPanel);
                chatPanel.revalidate();
                chatPanel.repaint();
                
                // Scroll to bottom
                SwingUtilities.invokeLater(() -> {
                    JScrollBar vertical = chatScroll.getVerticalScrollBar();
                    vertical.setValue(vertical.getMaximum());
                });

                // Get AI response in background
                new Thread(() -> {
                    String response = askAI(question);
                    SwingUtilities.invokeLater(() -> {
                        // Remove typing indicator
                        chatPanel.remove(typingPanel);
                        // Add AI response
                        addMessage(chatPanel, "assistant", response);
                        // Scroll to bottom
                        JScrollBar vertical = chatScroll.getVerticalScrollBar();
                        vertical.setValue(vertical.getMaximum());
                    });
                }).start();
            }
        });

        return panel;
    }

    private void addMessage(JPanel chatPanel, String sender, String message) {
        JPanel messagePanel = new JPanel(new BorderLayout(10, 5));
        messagePanel.setOpaque(false);
        messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Message bubble
        JPanel bubblePanel = new JPanel(new BorderLayout(10, 5));
        bubblePanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        
        if (sender.equals("user")) {
            bubblePanel.setBackground(getPrimaryColor());
            messagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        } else {
            bubblePanel.setBackground(UIManager.getColor("Panel.background"));
            bubblePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
            ));
            messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        }

        // Create a JEditorPane for better text formatting
        JEditorPane messageText = new JEditorPane("text/html", "");
        messageText.setEditable(false);
        messageText.setOpaque(false);
        messageText.setFont(MAIN_FONT);
        messageText.setForeground(sender.equals("user") ? Color.WHITE : UIManager.getColor("Label.foreground"));
        messageText.setContentType("text/html");
        
        // Format the message with proper HTML and width constraints
        String formattedMessage = "<html><body style='margin: 0; padding: 0; font-family: Segoe UI; font-size: 14px; line-height: 1.5; max-width: 600px;'>" +
            message.replace("\n", "<br>") +
            "</body></html>";
        messageText.setText(formattedMessage);
        
        // Set preferred size for the message text
        messageText.setPreferredSize(new Dimension(600, messageText.getPreferredSize().height));

        // Add the message text to the bubble
        bubblePanel.add(messageText, BorderLayout.CENTER);
        
        // Add the bubble to the message panel
        messagePanel.add(bubblePanel);
        
        // Add the message panel to the chat
        chatPanel.add(messagePanel);
        
        // Add some spacing between messages
        chatPanel.add(Box.createVerticalStrut(16));
        
        chatPanel.revalidate();
        chatPanel.repaint();
    }

    private String askAI(String question) {
        try {
            String apiKey = loadAPIKey(); // Load your OpenRouter key
            URL url = new URL("https://openrouter.ai/api/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("HTTP-Referer", "https://your-app-name.com"); // required
            conn.setRequestProperty("X-Title", "StudyMate");

            conn.setDoOutput(true);
            String jsonInput = String.format("""
            {
              "model": "mistralai/mixtral-8x7b-instruct",
              "messages": [{"role": "user", "content": "%s"}],
              "max_tokens": 800
            }
            """, question);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes());
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();

            // Parse response content (simple parsing)
            String responseStr = response.toString();
            int contentStart = responseStr.indexOf("\"content\":\"") + 11;
            int contentEnd = responseStr.indexOf("\"", contentStart);
            return responseStr.substring(contentStart, contentEnd).replace("\\n", "\n");

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private Icon createMinimalistIcon(String emoji, int size) {
        JLabel label = new JLabel(emoji);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, size));
        label.setOpaque(false);
        label.setForeground(ACCENT_COLOR);
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                label.paint(g);
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdvancedStudyAssistant app = new AdvancedStudyAssistant();
            app.setVisible(true);
        });
    }
}

