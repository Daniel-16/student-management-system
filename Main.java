import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main extends JFrame {
    private StudentManager studentManager;
    private JTextField matricNoField, nameField, ageField, searchField;
    private JTextArea displayArea;

    public Main() {
        studentManager = new StudentManager();
        initializeUI();
        loadStudentsFromFile();
        displayStudents();
    }

    private void initializeUI() {
        setTitle("Student Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Matriculation Number:"));
        matricNoField = new JTextField();
        inputPanel.add(matricNoField);
        inputPanel.add(new JLabel("Fullname:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudent());
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update Student");
        updateButton.addActionListener(e -> updateStudent());
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(e -> deleteStudent());
        buttonPanel.add(deleteButton);

        JButton displayButton = new JButton("Display Students");
        displayButton.addActionListener(e -> displayStudents());
        buttonPanel.add(displayButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchStudents());
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.CENTER);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        // Save Button
        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(e -> saveStudentsToFile());
        buttonPanel.add(saveButton);
    }

    private void addStudent() {
        try {
            String matricNo = matricNoField.getText().trim();
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            
            if (matricNo.isEmpty() || name.isEmpty()) {
                throw new IllegalArgumentException("matricNo and fullname cannot be empty");
            }
            
            Student student = new Student(matricNo, name, age);
            studentManager.addStudent(student);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            displayStudents();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        try {
            String matricNo = matricNoField.getText().trim();
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            
            if (matricNo.isEmpty() || name.isEmpty()) {
                throw new IllegalArgumentException("matricNo and fullname cannot be empty");
            }
            
            studentManager.updateStudent(matricNo, name, age);
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            displayStudents();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String matricNo = matricNoField.getText().trim();
        if (matricNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a student matricNo to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        studentManager.removeStudent(matricNo);
        clearInputFields();
        JOptionPane.showMessageDialog(this, "Student deleted successfully!");
        displayStudents();
    }

    private void searchStudents() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            displayStudents(); // If search is empty, display all students
            return;
        }
        List<Student> searchResults = studentManager.searchStudents(keyword);
        displayArea.setText("");
        if (searchResults.isEmpty()) {
            displayArea.append("No students found matching the search criteria.\n");
        } else {
            for (Student student : searchResults) {
                displayArea.append(student.toString() + "\n");
            }
        }
    }


    private void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String matricNo = parts[0].trim();
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    Student student = new Student(matricNo, name, age);
                    studentManager.addStudent(student);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading students from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error parsing age in file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayStudents() {
        List<Student> students = studentManager.getAllStudents();
        displayArea.setText("");
        if (students.isEmpty()) {
            displayArea.append("No students in the system.\n");
        } else {
            for (Student student : students) {
                displayArea.append(student.toString() + "\n");
            }
        }
    }

    private void clearInputFields() {
        matricNoField.setText("");
        nameField.setText("");
        ageField.setText("");
    }

    private void saveStudentsToFile() {
        FileHandler.saveStudents(studentManager.getAllStudents());
        JOptionPane.showMessageDialog(this, "Students saved to file successfully!");
    }

    // private void loadStudentsFromFile() {
    //     List<Student> loadedStudents = FileHandler.loadStudents();
    //     for (Student student : loadedStudents) {
    //         studentManager.addStudent(student);
    //     }
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}