import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentManager {
    private List<Student> students;
    public StudentManager() {
        students = new ArrayList<>();
    }
    public void addStudent(Student student) {
        students.add(student);
    }
    public void removeStudent(String matric_no) {
        students.removeIf(student -> student.getMatricNo().equals(matric_no));
    }
    public Student getStudent(String matric_no){
        return students.stream().filter(student -> student.getMatricNo().equals(matric_no)).findFirst().orElse(null);
    }
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    public void updateStudent(String matric_no, String name, int age) {
        Student student = getStudent(matric_no);
        if (student != null) {
            student.setName(name);
            student.setAge(age);
        }
    }
    public List<Student> searchStudents(String keyword) {
        return students.stream().filter(student -> student.getMatricNo().contains(keyword) || student.getName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    }
}