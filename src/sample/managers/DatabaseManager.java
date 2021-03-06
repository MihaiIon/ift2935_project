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
            conn = DriverManager.getConnection("jdbc:postgresql://postgres.iro.umontreal.ca:5432/davidbam", "davidbam_app", "unelicornequicourt");
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM davidbam.client");
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM davidbam.product order by seller_id;");
            while(rs.next()){
                products.add(new ProductModel(rs.getInt("id"),
                        rs.getInt("seller_id"),
                        rs.getString("name"),
                        rs.getString("state"),
                        rs.getString("description"),
                        rs.getFloat("seller_price"),
                        rs.getFloat("expert_price"),
                        rs.getTimestamp("creation_date"),
                        rs.getTimestamp("publich_date"),
                        rs.getInt("expert_id")));
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
            ResultSet rs = stmt.executeQuery("select * from davidbam.seller order by id;");
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
            ResultSet rs = stmt.executeQuery("select * from davidbam.offer order by seller_id;");
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
            ResultSet rs = stmt.executeQuery("select * from davidbam.transaction order by seller_id;");
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

    public ArrayList<ExpertModel> selectExperts(){
        checkConnection();
        Statement stm = null;
        ArrayList<ExpertModel> experts = new ArrayList<>();
        try{
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from davidbam.expert;");
            while(rs.next()){
                experts.add(new ExpertModel(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("reputation")));
            }
            rs.close();
            stm.close();
        }catch (Exception e){
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return experts;
    }

    // -----------------------------------------------------------------
    // Insert Queries

    // TODO
    public DatabaseManager insertClient(ClientModel client) {
        checkConnection();
        PreparedStatement pstm = null;
        try{
            String sql = "insert into davidbam.client values (?,?);";
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, client.getId());
            pstm.setString(2, client.getName());

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
    public DatabaseManager insertSeller(SellerModel seller) {
        checkConnection();
        PreparedStatement pstm = null;
        try{
            String sql = "insert into davidbam.seller values (?,?);";
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, seller.getId());
            pstm.setString(2, seller.getName());

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
    public DatabaseManager insertProduct(ProductModel product) {
        checkConnection();
        PreparedStatement pstm = null;
        try{
            String sql = "insert into davidbam.product values (?,?,?,?,?,?,?,?,?,?);";
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
            pstm.setInt(10, product.getExpertId());

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
            String sql = "insert into davidbam.offer values(?,?,?,?,?,?)";
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
            String sql = "insert into davidbam.transaction values (?,?,?,?,?,?,?);";
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
            String sql = String.format("update davidbam.product set state = '%s' where id=%d",product.getState().toString(), product.getId());
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

    public ArrayList<String> request(String req){
        checkConnection();
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<Integer> sizes = new ArrayList<>();
        Statement stm = null;
        String columns = "";
        try {
            String sql = req;
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnNumber = rsmd.getColumnCount();
            for(int i =1; i<=columnNumber;i++){
                columns+=rsmd.getColumnName(i)+"\t";
            }
            columns = columns.substring(0,columns.length()-1);
            ret.add(columns);
            while(rs.next()){

                String r = "";
                for(int i =1; i<=columnNumber;i++){

                    r += rs.getString(i) + "\t";
                }
                r = r.substring(0,r.length()-1);
                ret.add(r);
            }
        }catch (Exception e){
            System.err.println(e.getStackTrace());
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
        return ret;
    }


    // -----------------------------------------------------------------
    // Select Queries

    // -----------------------------------------------------------------
    // Update Queries

    // -----------------------------------------------------------------
    // Insert Queries

    // -----------------------------------------------------------------
    // Delete Queries

    final private static void printResultSet(ResultSet rs) throws SQLException {

        // Prepare metadata object and get the number of columns.
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        // Print column names (a header).
        for (int i = 1; i <= columnsNumber; i++) {
            if (i > 1) System.out.print(" | ");
            System.out.print(rsmd.getColumnName(i));
        }
        System.out.println("");

        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }
}
