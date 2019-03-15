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
        // If the database is empty...
        if(true) { 
            // Fill it
        }
    }

    /**
     * Check if the connection was correctly opened.
     */
    private void checkConnection() {
        if(singleton === null) {
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
    public ArrayList<ProductModel> selectProduct() {
        checkConnection();
        return null;
    }

    // TODO
    public ArrayList<OfferModel> selectOffer() {
        checkConnection();
        return null;
    }

    // TODO
    public ArrayList<TransactionModel> selectTransaction() {
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
}
