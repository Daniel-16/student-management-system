public class Student {
    private String matric_no;
    private String name;
    private int age;
    public Student(String matric_no, String name, int age) {
        this.matric_no = matric_no;
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public String getMatricNo() {
        return matric_no;
    }
    public void setMatricNo (String matric_no) {
        this.matric_no = matric_no;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }

    @Override
    public String toString(){
        return matric_no + "," + name + "," + age;
    }
}
