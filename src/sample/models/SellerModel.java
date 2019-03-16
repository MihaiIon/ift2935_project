package sample.models;

import sample.managers.DataManager;

import java.util.ArrayList;

public class SellerModel {

    private int id;
    private String name;
    private ArrayList<ProductModel> products;

    /**
     * Constructor | Use when creating a new SellerModel.
     *
     * @param name Seller's name.
     */
    public SellerModel(String name) {
        id = DataManager.getInstance().getNextSellerId();
        this.name = name;
        products = new ArrayList<>();
    }

    /**
     * Constructor | Use when loading an existing SellerModel from the database.
     *
     * @param name Seller's name.
     */
    public SellerModel(int id, String name, ArrayList<ProductModel> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    // -----------------------------------------------------------------
    // Methods

    /**
     * Appends new ProductModel to local list.
     *
     * @param product
     */
    public void addProduct(ProductModel product) {
        products.add(product);
    }

    // -----------------------------------------------------------------
    // Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ProductModel> getProducts() {
        return products;
    }
}
