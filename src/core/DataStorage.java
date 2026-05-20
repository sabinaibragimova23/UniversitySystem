package core;

import model.academic.Course;
import model.users.Student;
import model.users.StudentOrganization;
import model.communication.Request;
import model.research.News;
import model.research.ResearchDecorator;
import model.research.ResearchPaper;
import model.users.User;

import java.io.*;
import java.util.Calendar;
import java.util.*;

public class DataStorage {

    private static final DataStorage INSTANCE = new DataStorage();
    private static final String DATA_DIR = "data";

    private static List<User> users;
    private static List<Course> courses;
    private static List<News> news;
    private static List<Request> requests;
    private static List<ResearchDecorator> researchers;
    private static List<StudentOrganization> organizations;

    static {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private DataStorage() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        news = new ArrayList<>();
        requests = new ArrayList<>();
        researchers = new ArrayList<>();
        organizations = new ArrayList<>();
        load();
    }

    public static DataStorage getInstance() {
        return INSTANCE;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.removeIf(u -> u.getLogin() != null
                && u.getLogin().equals(user.getLogin()));
    }

    public static void addCourse(Course course) {
        courses.add(course);
    }

    public static void addNews(News n) {
        news.add(n);
    }

    public static void addRequest(Request r) {
        requests.add(r);
    }

    public static void registerResearcher(ResearchDecorator r) {
        if (!researchers.contains(r)) {
            researchers.add(r);
        }
    }

    public static void addOrganization(StudentOrganization org) {
        organizations.add(org);
    }

    public static List<StudentOrganization> getOrganizations() {
        return Collections.unmodifiableList(organizations);
    }

    public static List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public static List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public static List<Request> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    public static List<ResearchDecorator> getResearchers() {
        return Collections.unmodifiableList(researchers);
    }

    public static List<News> getNews() {
        List<News> sorted = new ArrayList<>(news);
        sorted.sort((a, b) -> Boolean.compare(b.isPinned(), a.isPinned()));
        return sorted;
    }

    private static boolean saveObject(Object data, String fileName) {
        String path = DATA_DIR + "/" + fileName + ".bin";
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(data);
            return true;
        } catch (Exception e) {
            System.err.println("[DataStorage] Save error: " + e.getMessage());
        }
        return false;
    }

    private static Object loadObject(String fileName) {
        String path = DATA_DIR + "/" + fileName + ".bin";
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(path))) {
            return ois.readObject();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static boolean save() {
        boolean ok =
                saveObject(users, "users")
                & saveObject(courses, "courses")
                & saveObject(news, "news")
                & saveObject(requests, "requests")
                & saveObject(researchers, "researchers")
                & saveObject(organizations, "organizations");

        if (ok) {
            System.out.println("[DataStorage] Data saved successfully.");
        }
        return ok;
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        Object u   = loadObject("users");
        Object c   = loadObject("courses");
        Object n   = loadObject("news");
        Object r   = loadObject("requests");
        Object res = loadObject("researchers");
        Object org = loadObject("organizations");

        if (u != null) {
            users = (List<User>) u;
            users.removeIf(usr -> usr == null || usr.getLogin() == null);
        }

        if (c != null) {
            courses = (List<Course>) c;
        }

        if (n != null) {
            news = (List<News>) n;
        }

        if (r != null) {
            requests = (List<Request>) r;
        }

        if (res != null) {
            researchers = (List<ResearchDecorator>) res;
        }

        if (org != null) {
            organizations = (List<StudentOrganization>) org;
        }

        // Rebuild course.students from Student data after deserialization
        for (User user : users) {
            if (user instanceof Student) {
                Student s = (Student) user;
                for (Course sc : s.getCourses()) {
                    for (Course stored : courses) {
                        if (stored.getCourseId().equals(sc.getCourseId())) {
                            if (!stored.getStudents().contains(s)) {
                                stored.enrollStudent(s);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "DataStorage["
                + "users=" + users.size()
                + ", courses=" + courses.size()
                + ", news=" + news.size()
                + ", requests=" + requests.size()
                + ", organizations=" + organizations.size()
                + "]";
    }

    public static void printAllResearcherPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> all = new ArrayList<>();
        for (ResearchDecorator rd : researchers) {
            all.addAll(rd.getPapers());
        }
        if (all.isEmpty()) {
            System.out.println("No research papers found.");
            return;
        }
        if (comparator != null) {
            all.sort(comparator);
        } else {
            Collections.sort(all);
        }
        System.out.println("=== All Research Papers (" + all.size() + ") ===");
        for (ResearchPaper p : all) {
            System.out.println("  " + p);
        }
    }

    public static ResearchDecorator getTopCitedResearcher() {
        if (researchers.isEmpty()) return null;
        ResearchDecorator top = null;
        int maxCitations = -1;
        for (ResearchDecorator rd : researchers) {
            int total = 0;
            for (ResearchPaper p : rd.getPapers()) {
                total += p.getCitations();
            }
            if (total > maxCitations) {
                maxCitations = total;
                top = rd;
            }
        }
        return top;
    }

    public static ResearchDecorator getTopCitedResearcherByYear(int year) {
        if (researchers.isEmpty()) return null;
        ResearchDecorator top = null;
        int maxCitations = -1;
        for (ResearchDecorator rd : researchers) {
            int total = 0;
            for (ResearchPaper p : rd.getPapers()) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(p.getDate());
                if (cal.get(Calendar.YEAR) == year) {
                    total += p.getCitations();
                }
            }
            if (total > maxCitations) {
                maxCitations = total;
                top = rd;
            }
        }
        return top;
    }
}