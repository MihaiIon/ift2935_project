package sample.managers;

import sample.models.*;

import java.sql.*;
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
        Statement stmt = null;
        ArrayList<ClientModel> clients = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM client");
            while(rs.next()){
                clients.add(new ClientModel(Integer.valueOf(rs.getString("id")),rs.getString("name")));
            }
            rs.close();
            stmt.close();
        }
        catch ( Exception e ) {
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
    }
        return clients;
    }

    // TODO
    public ArrayList<ProductModel> selectProducts() {
        checkConnection();

        Statement stmt = null;
        ArrayList<ProductModel> products = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product order by seller_id;");
            while(rs.next()){
                products.add(new ProductModel(rs.getInt("id"),
                        rs.getInt("seller_id"),
                        rs.getString("name"),
                        rs.getString("state"),
                        rs.getString("description"),
                        rs.getFloat("seller_price"),
                        rs.getFloat("expert_price"),
                        rs.getTimestamp("creation_date"),
                        rs.getTimestamp("publich_date")));
            }
            rs.close();
            stmt.close();
        }
        catch ( Exception e ) {
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return products;
    }

    // TODO
    public ArrayList<SellerModel> selectSellers( ArrayList<ProductModel> products) {
        checkConnection();
        Statement stmt = null;
        ArrayList<SellerModel> sellers = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from seller order by id;");
            int id = 0;
            int i = 0;
            while(rs.next()){

                //New Seller
                if(rs.getInt("id") != id) {

                    ArrayList<ProductModel> arr = new ArrayList<>();
                    sellers.add(new SellerModel(rs.getInt("id"),
                            rs.getString("name"),
                            arr));

                }
                //Add all products for current seller
                while(i<products.size() && rs.getInt("id") == products.get(i).getSellerId()){
                    sellers.get(sellers.size()-1).addProduct(products.get(i));
                    i++;
                }

                id = rs.getInt("id");
            }
            rs.close();
            stmt.close();

        }
        catch ( Exception e ) {
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return sellers;
    }


    // TODO
    public ArrayList<OfferModel> selectOffers(ArrayList<ProductModel> products) {
        checkConnection();
        Statement stmt = null;
        ArrayList<OfferModel> offers = new ArrayList<>();
        ArrayList<ProductModel> p = new ArrayList<>();
        int prevId =0;

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select offer.id, offer.client_id, offer.amount, offer.offer_date, product.id as product_id, product.seller_id from offer join product on offer.seller_id = product.seller_id order by product_id;");
            while(rs.next()){
                if(rs.getInt("seller_id") != prevId){
                    p = DataManager.getProductsWithSellerId(rs.getInt("seller_id"), products);
                    prevId = rs.getInt("seller_id");

                }

                    offers.add(new OfferModel(rs.getInt("id"),
                            rs.getInt("client_id"),
                            DataManager.getProductWithId(rs.getInt("product_id"),p),
                            rs.getFloat("amount"),
                            rs.getTimestamp("offer_date")));


            }
            rs.close();
            stmt.close();
        }
        catch ( Exception e ) {
            for(ProductModel pr : p){
                System.out.println(pr.getName());
            }
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println(offers.size());
        return offers;
    }

    // TODO
    public ArrayList<TransactionModel> selectTransactions(ArrayList<ClientModel> clients, ArrayList<OfferModel> offers) {
        checkConnection();
        Statement stmt = null;
        ArrayList<TransactionModel> transactions = new ArrayList<>();
        int i = 0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from transaction order by seller_id");
            while(rs.next()){
                transactions.add(new TransactionModel(
                        rs.getInt("id"),
                        rs.getInt("seller_id"),
                        DataManager.findClientById(clients, rs.getInt("client_id")),
                        DataManager.getOfferWithOfferId(rs.getInt("offer_id"),offers),
                        rs.getBoolean("is_automatic"),
                        rs.getTimestamp("transaction_date")
                        ));

            }
            rs.close();
            stmt.close();
        }
        catch ( Exception e ) {
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return transactions;
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
        PreparedStatement pstm = null;
        try{
            String sql = "insert into product values (?,?,?,?,?,?,?,?,?);";
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, product.getId());
            pstm.setInt(2, product.getSellerId());
            pstm.setString(3, product.getName());
            pstm.setString(4, product.getState().toString());
            pstm.setString(5, product.getDescription());
            pstm.setFloat(6, product.getSellerPrice());
            pstm.setObject(7, product.getExpertPrice(), Types.FLOAT);
            pstm.setTimestamp(8, product.getCreationDate());
            pstm.setTimestamp(9, product.getPublishDate());

            pstm.executeUpdate();

            pstm.close();


        }catch (Exception e){
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
        return singleton;
    }

    // TODO
    public DatabaseManager insertOffer(OfferModel offer) {
        checkConnection();
        PreparedStatement pstm;
        try {
            String sql = "insert into offer values(?,?,?,?,?,?)";
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, offer.getId());
            pstm.setInt(2,offer.getClientId());
            pstm.setInt(3,offer.getProductId());
            pstm.setInt(4,offer.getSellerId());
            pstm.setFloat(5, offer.getAmount());
            pstm.setTimestamp(6, offer.getOfferDate());

            pstm.executeUpdate();
            pstm.close();
        }catch (Exception e){
            System.out.println(offer.getId());
            System.out.println(offer.getClientId());
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
        return singleton;
    }

    // TODO
    public DatabaseManager insertTransaction(TransactionModel transaction) {
        checkConnection();
        PreparedStatement pstm;
        try{
            String sql = "insert into transaction values (?,?,?,?,?,?,?);";
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, transaction.getId());
            pstm.setInt(2, transaction.getClient().getId());
            pstm.setInt(3, transaction.getSellerId());
            pstm.setInt(4, transaction.getOffer().getProductId());
            pstm.setInt(5, transaction.getOffer().getId());
            pstm.setBoolean(6, transaction.isWasAutomatic());
            pstm.setTimestamp(7, transaction.getTransactionDate());

            pstm.executeUpdate();
            pstm.close();

        }catch (Exception e){
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
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
        PreparedStatement pstm = null;
        try{
            String sql = String.format("update product set state = '%s' where id=%d",product.getState().toString(), product.getId());
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
            pstm.close();

        }catch (Exception e){
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
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
