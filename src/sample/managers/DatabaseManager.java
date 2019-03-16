package sample.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    // -----------------------------------------------------------------

    private Connection conn = null;

    /**
     * Default constructor
     */
    private DatabaseManager() {
    }

    // -----------------------------------------------------------------
    // Methods

    /**
     * @return Current instance.
     */
    public DatabaseManager openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/db", "jeremy", "boyer");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return this;
    }

    /**
     * @return Current instance.
     */
    public void closeConnection() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Closed database successfully");
    }

    // -----------------------------------------------------------------
    // Select Queries

    // -----------------------------------------------------------------
    // Update Queries

    // -----------------------------------------------------------------
    // Insert Queries

    // -----------------------------------------------------------------
    // Delete Queries
}
