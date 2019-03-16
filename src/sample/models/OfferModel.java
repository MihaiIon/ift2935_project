package sample.models;

import sample.managers.DataManager;

import java.sql.Timestamp;

public class OfferModel {

    private int id;
    private int clientId;
    private ProductModel product;
    private float amount;
    private Timestamp offerDate;

    /**
     * Constructor | Use when creating a new OfferModel.
     *
     * @param clientId
     * @param product
     * @param amount
     */
    public OfferModel(int clientId, ProductModel product, float amount) {
        id = DataManager.getInstance().getNextOfferId();
        this.product = product;
        this.amount = amount;
        offerDate = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Constructor | Use when loading an existing OfferModel from the database.
     *
     * @param id
     * @param clientId
     * @param product
     * @param amount
     */
    public OfferModel(int id, int clientId, ProductModel product, float amount, Timestamp offerDate) {
        this.id = id;
        this.clientId = clientId;
        this.product = product;
        this.amount = amount;
        this.offerDate = offerDate;
    }

    // -----------------------------------------------------------------
    // Getters

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getProductId() {
        return product.getId();
    }

    public int getSellerId() {
        return product.getSellerId();
    }

    public float getAmount() {
        return amount;
    }

    public Timestamp getOfferDate() {
        return offerDate;
    }
}
