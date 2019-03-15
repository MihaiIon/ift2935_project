package sample.models;

import java.sql.Timestamp;

public class TransactionModel {

    private int id;
    private int sellerId;
    private ClientModel client;
    private OfferModel offer;
    private boolean wasAutomatic;
    private Timestamp transactionDate;

    /**
     * Constructor | Use when creating a new TransactionModel.
     *
     * @param sellerId
     * @param client
     * @param offer
     * @param wasAutomatic
     */
    public TransactionModel(int sellerId, ClientModel client, OfferModel offer, boolean wasAutomatic) {
        id = -1; // TODO: Get id from database
        this.sellerId = sellerId;
        this.client = client;
        this.offer = offer;
        this.wasAutomatic = wasAutomatic;
        transactionDate = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Constructor | Use when loading an existing TransactionModel from the database.
     *
     * @param id
     * @param sellerId
     * @param client
     * @param offer
     * @param wasAutomatic
     * @param transactionDate
     */
    public TransactionModel(int id, int sellerId, ClientModel client, OfferModel offer, boolean wasAutomatic, Timestamp transactionDate) {
        this.id = id;
        this.sellerId = sellerId;
        this.client = client;
        this.offer = offer;
        this.wasAutomatic = wasAutomatic;
        this.transactionDate = transactionDate;
    }

    // -----------------------------------------------------------------
    // Getters


    public int getId() {
        return id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public ClientModel getClient() {
        return client;
    }

    public OfferModel getOffer() {
        return offer;
    }

    public boolean isWasAutomatic() {
        return wasAutomatic;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }
}
