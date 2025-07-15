package studentReports;

import java.util.*;

public class StudentService {

    Integer studentsAverage = 0;
    double studentPercentage = 0;
    String studentsResultStatus = null;
    Integer obtainedMarks = 0;
    Integer totalMarks = 400;

    List<Student> students = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);
    public String addStudent(String name){
        if (name == null || name.trim().isEmpty()) System.out.println("Name cannot be null or empty.");
        Student student = new Student();
        student.setStudentName(name);
        students.add(student);
        return student.getStudentName();
    }
    public Student getStudentByName(String name) {
        for (Student student : students) {
            if (student.getStudentName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    public Map<String, Integer> registerMarks(Student student, Map<String, Integer> marks){
        for(Map.Entry<String, Integer> mark: marks.entrySet()){
            if(mark.getValue()>=0 && mark.getValue()<=100){
                student.getSubjectMarks().put(mark.getKey(), mark.getValue());
            }
            else{
                System.out.println("Invalid marks Entered"+mark.getKey()+" "+mark.getValue());
            }
        }
        return student.getSubjectMarks();
    }

    public void calculateResult(Student student){
//        Student student = getStudentByName(name);
        Map<String, Integer> marks = student.getSubjectMarks();
        for(Map.Entry<String, Integer> mark : marks.entrySet()){
            obtainedMarks+=mark.getValue();
        }
        System.out.println("----------------------------------------");
        System.out.println("Total obtained Marks is: "+obtainedMarks);
        System.out.println("----------------------------------------");

        studentPercentage = ((double) obtainedMarks / totalMarks) * 100;
        System.out.println("Percentage of "+student.getStudentName()+" is "+studentPercentage+"%");
        System.out.println("----------------------------------------");

        if(studentPercentage >= 35){
            System.out.println(student.getStudentName()+" has passed with "+studentPercentage+"%");
            System.out.println("----------------------------------------");
        }
        else {
            System.out.println(student.getStudentName()+" has failed with "+studentPercentage+"%");
            System.out.println("----------------------------------------");
        }

        studentsAverage = obtainedMarks/marks.size();

        System.out.println(student.getStudentName()+"'s average marks is "+studentsAverage);
        System.out.println("----------------------------------------");

//        marks.clear();

    }









}
