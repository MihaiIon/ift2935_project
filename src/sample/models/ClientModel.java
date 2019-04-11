package sample.models;

import sample.managers.DataManager;

public class ClientModel {

    private int id;
    private String name;

    /**
     * Constructor | Use when creating a new Client.
     *
     * @param name Client's name.
     */
    public ClientModel(String name) {
        id = DataManager.getInstance().getNextClientId();
        this.name = name;
    }

    /**
     * Constructor | Use when loading an existing Client from the database.
     *
     * @param name Client's name.
     */
    public ClientModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // -----------------------------------------------------------------
    // Methods

    /**
     * Creates an offer regarding an existing (and available) product.
     *
     * @param product
     * @param amount
     * @return
     */
    public OfferModel createOffer(ProductModel product, float amount) {
        return new OfferModel(id, product, amount);
    }

    // -----------------------------------------------------------------
    // Getters

    public int getId() { return id; }
    public String getName() { return name; }
}
