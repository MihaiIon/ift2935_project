package sample.managers;

import sample.models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseManager {
    // Singleton
    private static boolean CREATE_DATABASE = false;
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
        // If the database is empty...
        if (CREATE_DATABASE) {
            openConnection();
            // Fill it
            closeConnection();
        }
    }

    // -----------------------------------------------------------------
    // Methods

    /**
     * @return Current instance.
     */
    public DatabaseManager openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://isilo.db.elephantsql.com:5432/lafmoddm", "lafmoddm", "HYPjmhFPNHvy85afZLsfl4SSLBwwqLx3");
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

    /**
     * Check if the connection was correctly opened.
     */
    private void checkConnection() {
        if (singleton == null) {
            getInstance();
            System.out.println("Warning | The connection must be opened via the method 'openConnection()' and closed via the method 'closeConnection'.");
        }
    }

    // -----------------------------------------------------------------
    // Select Queries

    // TODO
    public ArrayList<ClientModel> selectClients() {
        checkConnection();
        return null;
    }

    // TODO
    public ArrayList<SellerModel> selectSellers() {
        checkConnection();
        return null;
    }

    // TODO
    public ArrayList<ProductModel> selectProducts() {
        checkConnection();
        return null;
    }

    // TODO
    public ArrayList<OfferModel> selectOffers() {
        checkConnection();
        return null;
    }

    // TODO
    public ArrayList<TransactionModel> selectTransactions() {
        checkConnection();
        return null;
    }

    // -----------------------------------------------------------------
    // Insert Queries

    // TODO
    public DatabaseManager insertClient(ClientModel client) {
        checkConnection();
        return singleton;
    }

    // TODO
    public DatabaseManager insertSeller(SellerModel seller) {
        checkConnection();
        return singleton;
    }

    // TODO
    public DatabaseManager insertProduct(ProductModel product) {
        checkConnection();
        return singleton;
    }

    // TODO
    public DatabaseManager insertOffer(OfferModel offer) {
        checkConnection();
        return singleton;
    }

    // TODO
    public DatabaseManager insertTransaction(TransactionModel transaction) {
        checkConnection();
        return singleton;
    }

    // -----------------------------------------------------------------
    // Update Queries

    // TODO
    public DatabaseManager updateClient(ClientModel client) {
        checkConnection();
        return singleton;
    }

    // TODO
    public DatabaseManager updateSeller(SellerModel seller) {
        checkConnection();
        return singleton;
    }

    // TODO
    public DatabaseManager updateProduct(ProductModel product) {
        checkConnection();
        return singleton;
    }

    // TODO
    public DatabaseManager updateOffer(OfferModel offer) {
        checkConnection();
        return singleton;
    }

    // TODO
    public DatabaseManager updateTransaction(TransactionModel transaction) {
        checkConnection();
        return singleton;
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
