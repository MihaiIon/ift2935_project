package sample.managers;

import sample.models.*;

import java.util.ArrayList;

public class DataManager {
    // Singleton
    private static DataManager singleton = null;

    /**
     * @return Singleton.
     */
    public static DataManager getInstance() {
        if (singleton == null) {
            singleton = new DataManager();
        }
        return singleton;
    }

    // -----------------------------------------------------------------

    private ArrayList<ClientModel> clients;
    private ArrayList<SellerModel> sellers;
    private ArrayList<ProductModel> products;
    private ArrayList<OfferModel> offers;
    private ArrayList<TransactionModel> transactions;

    private DataManager() {
        DatabaseManager dbm = DatabaseManager.getInstance();
        dbm.openConnection();
        clients = dbm.selectClients();
        products = dbm.selectProducts();
        sellers = dbm.selectSellers(products);
        offers = dbm.selectOffers(products);
        transactions = dbm.selectTransactions(clients, offers);
        dbm.closeConnection();
    }

    // -----------------------------------------------------------------
    // Methods

    /**
     * @param client
     */
    public void addClient(ClientModel client) {
        clients.add(client);
        DatabaseManager.getInstance().openConnection().insertClient(client).closeConnection();
    }

    /**
     * @param seller
     */
    public void addSeller(SellerModel seller) {
        sellers.add(seller);
        DatabaseManager.getInstance().openConnection().insertSeller(seller).closeConnection();
    }

    /**
     * @param product
     */
    public void addProduct(ProductModel product) {
        products.add(product);
        DatabaseManager.getInstance().openConnection().insertProduct(product).closeConnection();
    }

    /**
     * @param offer
     */
    public void addOffer(OfferModel offer) {
        offers.add(offer);
        DatabaseManager.getInstance().openConnection().insertOffer(offer).closeConnection();
    }

    /**
     * @param transaction
     */
    public void addTransaction(TransactionModel transaction) {
        transactions.add(transaction);
        DatabaseManager.getInstance().openConnection().insertTransaction(transaction).closeConnection();
    }

    // -----------------------------------------------------------------
    // Getters


    public ArrayList<ClientModel> getClients() {
        return clients;
    }

    public ClientModel getClientFromId(int id){
        for(ClientModel c: clients){
            if(c.getId() == id){
                return c;
            }
        }

        return null;
    }

    /**
     * @return
     */
    public int getNextClientId() {
        return clients.size();
    }

    public ArrayList<SellerModel> getSellers() {
        return sellers;
    }

    /**
     * @return
     */
    public int getNextSellerId() {
        return sellers.size();
    }

    /**
     * @param id Seller's id.
     * @return
     */
    public ArrayList<ProductModel> getProductsWithSellerId(int id) {
        ArrayList<ProductModel> filteredResults = new ArrayList<>();
        for (ProductModel product : products) {
            if (product.getSellerId() == id) {
                filteredResults.add(product);
            }
        }
        return filteredResults;
    }

    public static ArrayList<ProductModel> getProductsWithSellerId(int id, ArrayList<ProductModel> products) {
        ArrayList<ProductModel> filteredResults = new ArrayList<>();
        for (ProductModel product : products) {
            if (product.getSellerId() == id) {
                filteredResults.add(product);
            }
        }
        return filteredResults;
    }

    public static ProductModel getProductWithId(int id, ArrayList<ProductModel> products){
        for(ProductModel product : products){
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }



    /**
     * @return
     */
    public ArrayList<ProductModel> getAvailableProducts() {
        ArrayList<ProductModel> filteredResults = new ArrayList<>();
        for (ProductModel product : products) {
            if (product.isAvailable()) {
                filteredResults.add(product);
            }
        }
        return filteredResults;
    }

    /**
     * @return
     */
    public int getNextProductId() {
        return transactions.size();
    }

    /**
     * @param id Seller's id.
     * @return
     */
    public ArrayList<OfferModel> getOffersWithSellerId(int id) {
        ArrayList<OfferModel> filteredResults = new ArrayList<>();
        for (OfferModel offer : offers) {
            if (offer.getSellerId() == id) {
                filteredResults.add(offer);
            }
        }
        return filteredResults;
    }

    /**
     * @param id Client's id.
     * @return
     */
    public ArrayList<OfferModel> getOffersWithClientId(int id) {
        ArrayList<OfferModel> filteredResults = new ArrayList<>();
        for (OfferModel offer : offers) {
            if (offer.getClientId() == id) {
                filteredResults.add(offer);
            }
        }
        return filteredResults;
    }

    /**
     * @return
     */
    public int getNextOfferId() {
        return offers.size();
    }

    /**
     * @param id Seller's id.
     * @return
     */
    public ArrayList<TransactionModel> getTransactionsWithSellerId(int id) {
        ArrayList<TransactionModel> filteredResults = new ArrayList<>();
        for (TransactionModel transaction : transactions) {
            if (transaction.getSellerId() == id) {
                filteredResults.add(transaction);
            }
        }
        return filteredResults;
    }

    /**
     * @return
     */
    public int getNextTransactionId() {
        return transactions.size();
    }

    public static ClientModel findClientById(ArrayList<ClientModel> clients, int  id){
        return clients.stream().filter(client -> id == client.getId()).findFirst().orElse(null);
    }
}
