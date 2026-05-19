package core;

import model.academic.Course;
import model.communication.Request;
import model.users.StudentOrganization;
import model.research.News;
import model.research.ResearchDecorator;
import model.research.ResearchPaper;
import model.users.User;

import java.io.*;
import java.util.Calendar;
import java.util.*;

/**
 * Singleton class responsible for storing and managing
 * all university system data.
 *
 * Handles:
 * - users
 * - courses
 * - news
 * - requests
 * - researchers
 * - student organizations
 *
 * Also provides serialization functionality
 * for saving and loading data from files.
 *
 * @author Sabina
 * @version 1.0
 */
public class DataStorage {

    /**
     * Singleton instance of DataStorage.
     */
    private static final DataStorage INSTANCE =
            new DataStorage();

    /**
     * Directory where serialized data is stored.
     */
    private static final String DATA_DIR = "data";

    /**
     * List of all system users.
     */
    private static List<User> users;

    /**
     * List of all university courses.
     */
    private static List<Course> courses;

    /**
     * List of published news.
     */
    private static List<News> news;

    /**
     * List of system requests.
     */
    private static List<Request> requests;

    /**
     * List of registered researchers.
     */
    private static List<ResearchDecorator> researchers;

    /**
     * List of student organizations.
     */
    private static List<StudentOrganization> organizations;

    /**
     * Static initializer for creating data directory.
     */
    static {

        File dir = new File(DATA_DIR);

        if (!dir.exists()) {

            dir.mkdirs();
        }
    }

    /**
     * Private constructor for singleton pattern.
     * Initializes collections and loads saved data.
     */
    private DataStorage() {

        users = new ArrayList<>();

        courses = new ArrayList<>();

        news = new ArrayList<>();

        requests = new ArrayList<>();

        researchers = new ArrayList<>();

        organizations = new ArrayList<>();

        load();
    }

    /**
     * Returns singleton instance of DataStorage.
     *
     * @return DataStorage instance
     */
    public static DataStorage getInstance() {

        return INSTANCE;
    }

    /**
     * Adds a user to the system.
     *
     * @param user user to add
     */
    public static void addUser(User user) {

        users.add(user);
    }

    /**
     * Removes a user from the system.
     *
     * @param user user to remove
     */
    public static void removeUser(User user) {
        users.removeIf(u -> u.getLogin() != null
                && u.getLogin().equals(user.getLogin()));
    }

    /**
     * Adds a course to the system.
     *
     * @param course course to add
     */
    public static void addCourse(Course course) {

        courses.add(course);
    }

    /**
     * Adds news to the system.
     *
     * @param n news object
     */
    public static void addNews(News n) {

        news.add(n);
    }

    /**
     * Adds a request to the system.
     *
     * @param r request object
     */
    public static void addRequest(Request r) {

        requests.add(r);
    }

    /**
     * Registers a researcher in the system.
     *
     * @param r research decorator object
     */
    public static void registerResearcher(
            ResearchDecorator r) {

        if (!researchers.contains(r)) {

            researchers.add(r);
        }
    }

    /**
     * Adds a student organization.
     *
     * @param org organization to add
     */
    public static void addOrganization(
            StudentOrganization org) {

        organizations.add(org);
    }

    /**
     * Returns all organizations.
     *
     * @return unmodifiable list of organizations
     */
    public static List<StudentOrganization>
    getOrganizations() {

        return Collections.unmodifiableList(
                organizations
        );
    }

    /**
     * Returns all users.
     *
     * @return unmodifiable list of users
     */
    public static List<User> getUsers() {

        return Collections.unmodifiableList(users);
    }

    /**
     * Returns all courses.
     *
     * @return unmodifiable list of courses
     */
    public static List<Course> getCourses() {

        return Collections.unmodifiableList(courses);
    }

    /**
     * Returns all requests.
     *
     * @return unmodifiable list of requests
     */
    public static List<Request> getRequests() {

        return Collections.unmodifiableList(requests);
    }

    /**
     * Returns all researchers.
     *
     * @return unmodifiable list of researchers
     */
    public static List<ResearchDecorator>
    getResearchers() {

        return Collections.unmodifiableList(
                researchers
        );
    }

    /**
     * Returns sorted news list.
     * Pinned news appear first.
     *
     * @return sorted list of news
     */
    public static List<News> getNews() {

        List<News> sorted =
                new ArrayList<>(news);

        sorted.sort(
                (a, b) -> Boolean.compare(
                        b.isPinned(),
                        a.isPinned()
                )
        );

        return sorted;
    }

    /**
     * Saves an object into a binary file.
     *
     * @param data object to save
     * @param fileName target file name
     * @return true if save successful
     */
    private static boolean saveObject(
            Object data,
            String fileName) {

        String path =
                DATA_DIR + "/" + fileName + ".bin";

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(path)
                     )) {

            oos.writeObject(data);

            return true;

        } catch (Exception e) {

            System.err.println(
                    "[DataStorage] Save error: "
                            + e.getMessage()
            );
        }

        return false;
    }

    /**
     * Loads an object from a binary file.
     *
     * @param fileName source file name
     * @return loaded object or null
     */
    private static Object loadObject(
            String fileName) {

        String path =
                DATA_DIR + "/" + fileName + ".bin";

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(path)
                     )) {

            return ois.readObject();

        } catch (Exception ignored) {
        }

        return null;
    }

    /**
     * Saves all system data into files.
     *
     * @return true if all data saved successfully
     */
    public static boolean save() {

        boolean ok =
                saveObject(users, "users")
                        & saveObject(courses, "courses")
                        & saveObject(news, "news")
                        & saveObject(requests, "requests")
                        & saveObject(researchers,
                        "researchers")
                        & saveObject(organizations,
                        "organizations");

        if (ok) {

            System.out.println(
                    "[DataStorage] Data saved successfully."
            );
        }

        return ok;
    }

    /**
     * Loads all saved system data from files.
     */
    @SuppressWarnings("unchecked")
    public static void load() {

        Object u = loadObject("users");

        Object c = loadObject("courses");

        Object n = loadObject("news");

        Object r = loadObject("requests");

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

            researchers =
                    (List<ResearchDecorator>) res;
        }

        if (org != null) {

            organizations =
                    (List<StudentOrganization>) org;
        }
    }

    /**
     * Returns formatted storage information.
     *
     * @return string representation of storage
     */
    @Override
    public String toString() {

        return "DataStorage[" +
                "users=" + users.size() +
                ", courses=" + courses.size() +
                ", news=" + news.size() +
                ", requests=" + requests.size() +
                ", organizations=" +
                organizations.size() +
                "]";
    }

    /**
     * Prints all research papers from all researchers.
     * Papers may be sorted using a comparator.
     *
     * @param comparator sorting comparator
     */
    public static void printAllResearcherPapers(
            Comparator<ResearchPaper> comparator
    ) {

        List<ResearchPaper> all =
                new ArrayList<>();

        for (ResearchDecorator r : researchers) {

            all.addAll(r.getPapers());
        }

        if (all.isEmpty()) {

            System.out.println(
                    "No research papers found."
            );

            return;
        }

        if (comparator != null) {

            all.sort(comparator);

        } else {

            Collections.sort(all);
        }

        System.out.println(
                "=== All Research Papers ("
                        + all.size()
                        + ") ==="
        );

        for (ResearchPaper p : all) {

            System.out.println("  " + p);
        }
    }

    /**
     * Finds researcher with the highest
     * total number of citations.
     *
     * @return top cited researcher
     */
    public static ResearchDecorator
    getTopCitedResearcher() {

        if (researchers.isEmpty()) {

            return null;
        }

        ResearchDecorator top = null;

        int maxCitations = -1;

        for (ResearchDecorator r : researchers) {

            int total = 0;

            for (ResearchPaper p : r.getPapers()) {

                total += p.getCitations();
            }

            if (total > maxCitations) {

                maxCitations = total;

                top = r;
            }
        }

        return top;
    }

    /**
     * Finds top cited researcher
     * for a specific year.
     *
     * @param year target year
     * @return top cited researcher of the year
     */
    public static ResearchDecorator
    getTopCitedResearcherByYear(int year) {

        if (researchers.isEmpty()) {

            return null;
        }

        ResearchDecorator top = null;

        int maxCitations = -1;

        for (ResearchDecorator r : researchers) {

            int total = 0;

            for (ResearchPaper p : r.getPapers()) {

                Calendar cal =
                        Calendar.getInstance();

                cal.setTime(p.getDate());

                if (cal.get(Calendar.YEAR)
                        == year) {

                    total += p.getCitations();
                }
            }

            if (total > maxCitations) {

                maxCitations = total;

                top = r;
            }
        }

        return top;
    }
}