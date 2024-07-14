package task_3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class CourseRegistrationSystem {
    private DatabaseManager dbManager;
    private JFrame frame;
    private JTable courseTable;
    private JTextField studentIdField;
    private JTextField studentNameField;
    private JTextField courseCodeField;
    private JComboBox<String> courseTypeComboBox;
    private DefaultTableModel courseTableModel;
    private JTextArea registeredCoursesArea;

    public CourseRegistrationSystem() {
        dbManager = new DatabaseManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Course Registration System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800); // Set a fixed window size
        frame.setLayout(new BorderLayout());

        // Centering the window on the screen
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(60, 63, 65));
        JLabel titleLabel = new JLabel("Course Registration System", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        courseTableModel = new DefaultTableModel(new Object[]{"Course Code", "Title", "Description", "Capacity", "Schedule", "Slots Available", "Course Type"}, 0);
        courseTable = new JTable(courseTableModel);
        courseTable.setRowHeight(30);
        courseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane courseScrollPane = new JScrollPane(courseTable);

        JPanel registrationPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        registrationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        registrationPanel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        registrationPanel.add(studentIdField);

        registrationPanel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        registrationPanel.add(studentNameField);

        registrationPanel.add(new JLabel("Course Code:"));
        courseCodeField = new JTextField();
        registrationPanel.add(courseCodeField);

        registrationPanel.add(new JLabel("Course Type:"));
        courseTypeComboBox = new JComboBox<>(new String[]{"BCA", "BBA", "BSc", "BBA-CA", "BCS", "BCom", "MSc", "MCA", "MS-CCA"});
        registrationPanel.add(courseTypeComboBox);

        JButton registerButton = new JButton("Register");
        registrationPanel.add(registerButton);

        JButton viewRegisteredCoursesButton = new JButton("View Registered Courses");
        registrationPanel.add(viewRegisteredCoursesButton);

        registeredCoursesArea = new JTextArea();
        registeredCoursesArea.setEditable(false);
        registeredCoursesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        registeredCoursesArea.setBorder(BorderFactory.createTitledBorder("Registered Courses"));
        JScrollPane registeredCoursesScrollPane = new JScrollPane(registeredCoursesArea);
        registeredCoursesScrollPane.setPreferredSize(new Dimension(1200, 300)); // Adjust the height here

        frame.add(courseScrollPane, BorderLayout.CENTER);
        frame.add(registrationPanel, BorderLayout.EAST);
        frame.add(registeredCoursesScrollPane, BorderLayout.SOUTH);

        loadCourses();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerForCourse();
            }
        });

        viewRegisteredCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRegisteredCourses();
            }
        });

        frame.setVisible(true);
    }

    private void loadCourses() {
        List<Course> courses = dbManager.getAllCourses();
        courseTableModel.setRowCount(0);
        for (Course course : courses) {
            courseTableModel.addRow(new Object[]{
                    course.getCourseCode(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getCapacity(),
                    course.getSchedule(),
                    course.getCapacity() - course.getEnrolled(),
                    course.getCourseType()
            });
        }
    }

    private void registerForCourse() {
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String courseCode = courseCodeField.getText();

        boolean success = dbManager.registerStudentForCourse(studentId, studentName, courseCode);
        if (success) {
            JOptionPane.showMessageDialog(frame, "Registration successful");
            loadCourses();
        } else {
            JOptionPane.showMessageDialog(frame, "Registration failed");
        }
    }

    private void loadRegisteredCourses() {
        Map<String, List<Student>> courseRegistrations = dbManager.getCourseRegistrations();
        registeredCoursesArea.setText("");
        for (Map.Entry<String, List<Student>> entry : courseRegistrations.entrySet()) {
            registeredCoursesArea.append("Course Code: " + entry.getKey() + "\n");
            registeredCoursesArea.append("Registered Students:\n");
            for (Student student : entry.getValue()) {
                registeredCoursesArea.append(" - " + student.getName() + " (" + student.getStudentID() + ")\n");
            }
            registeredCoursesArea.append("\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CourseRegistrationSystem::new);
    }
}
