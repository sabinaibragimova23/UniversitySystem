package core;

import model.academic.Course;
import model.users.StudentOrganization;
import model.comunication.Request;
import model.research.News;
import model.research.ResearchDecorator;
import model.research.ResearchPaper;
import model.users.User;

import java.io.*;
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
        File dir = new File("src/data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String rootPath = dir.getAbsolutePath();
    }

    private DataStorage() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        news = new ArrayList<>();
        requests = new ArrayList<>();
        researchers = new ArrayList<>();

        load();
    }

    public static DataStorage getInstance() {
        return INSTANCE;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
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
                        & saveObject(researchers, "researchers");

        if (ok) {
            System.out.println("[DataStorage] Data saved successfully.");
        }

        return ok;
    }

    @SuppressWarnings("unchecked")
    public static void load() {

        Object u = loadObject("users");
        Object c = loadObject("courses");
        Object n = loadObject("news");
        Object r = loadObject("requests");
        Object res = loadObject("researchers");

        if (u != null) users = (List<User>) u;
        if (c != null) courses = (List<Course>) c;
        if (n != null) news = (List<News>) n;
        if (r != null) requests = (List<Request>) r;
        if (res != null) researchers = (List<ResearchDecorator>) res;
    }

    @Override
    public String toString() {
        return "DataStorage[" +
                "users=" + users.size() +
                ", courses=" + courses.size() +
                ", news=" + news.size() +
                ", requests=" + requests.size() +
                "]";
    }

	public static void printAllResearcherPapers(Comparator<ResearchPaper> comparator) {

		
	}

	public static ResearchDecorator getTopCitedResearcher() {
		return null;
	}
}