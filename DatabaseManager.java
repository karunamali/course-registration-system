package task_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private List<Course> courses;
    private List<Student> students;
    private Map<String, List<Student>> courseRegistrations; // Map of courseCode to list of students

    public DatabaseManager() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        courseRegistrations = new HashMap<>();
        // Sample data
        courses.add(new Course("BCA101", "Introduction to BCA", "Basics of BCA", 60, "MWF 10-11AM", "BCA"));
        courses.add(new Course("MCA201", "Advanced Java", "Deep dive into Java", 30, "TTh 2-4PM", "MCA"));
        courses.add(new Course("AI101", "Artificial Intelligence", "Introduction to AI", 50, "MWF 1-2PM", "AI"));
        courses.add(new Course("JAVA301", "Advanced Java Programming", "Advanced concepts in Java", 40, "TTh 10-11AM", "Java"));
        courses.add(new Course("PYTH201", "Python for Data Science", "Python programming for data analysis", 35, "MWF 11AM-12PM", "Python"));
        courses.add(new Course("CLOUD101", "Cloud Computing Fundamentals", "Introduction to Cloud Computing", 60, "MWF 2-3PM", "Cloud"));
        courses.add(new Course("SECURITY201", "Cybersecurity Essentials", "Basic principles of cybersecurity", 25, "TTh 3-4PM", "Cybersecurity"));
        courses.add(new Course("WEBDEV202", "Web Development with JavaScript", "Frontend web development", 45, "MWF 3-4PM", "Web Dev"));
        courses.add(new Course("DBMS303", "Database Management Systems", "Advanced DBMS concepts", 30, "TTh 1-2PM", "DBMS"));
        courses.add(new Course("NET202", "Networking Essentials", "Fundamentals of computer networking", 55, "MWF 12-1PM", "Networking"));
        courses.add(new Course("ML101", "Machine Learning Basics", "Introduction to Machine Learning", 40, "TTh 11AM-12PM", "ML"));
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public boolean registerStudentForCourse(String studentID, String studentName, String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode) && course.getCapacity() > course.getEnrolled()) {
                course.setEnrolled(course.getEnrolled() + 1);

                Student student = new Student(studentID, studentName);
                if (!students.contains(student)) {
                    students.add(student);
                }

                if (!courseRegistrations.containsKey(courseCode)) {
                    courseRegistrations.put(courseCode, new ArrayList<>());
                }

                courseRegistrations.get(courseCode).add(student);
                return true;
            }
        }
        return false;
    }

    public Map<String, List<Student>> getCourseRegistrations() {
        return courseRegistrations;
    }
}


