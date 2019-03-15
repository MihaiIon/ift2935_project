package sample.managers;

public class DatabaseManager {
    // Singleton
    private static DatabaseManager singleton = null;

    /**
     * @return Singleton.
     */
    public static DatabaseManager getInstance() {
        if (singleton == null) {
            singleton = new DatabaseManager();
        }
        return singleton;
    }

    private DatabaseManager() {

    }
}
