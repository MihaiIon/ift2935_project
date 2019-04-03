package sample.models;

import sample.managers.DataManager;

import java.sql.Timestamp;

public class ProductModel {

    public enum ProductState {
        DRAFT, WAITING_ESTIMATION, AVAILABLE, UNAVAILABLE
    }

    private int id;
    private int sellerId;
    private String name;
    private String description;
    private float sellerPrice;
    private Float expertPrice;
    private Timestamp creationDate;
    private Timestamp publishDate;
    private ProductState state;

    /**
     * Constructor | Use when creating a new ProductModel.
     *
     * @param sellerId
     * @param name
     * @param description
     * @param price
     */
    public ProductModel(int sellerId, String name, String description, float price) {
        id = DataManager.getInstance().getNextProductId();
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        sellerPrice = price;
        expertPrice = null;
        creationDate = new Timestamp(System.currentTimeMillis());
        publishDate = null;
        state = ProductState.DRAFT;
    }

    /**
     * Constructor | Use when loading an existing ProductModel from the database.
     *
     * @param id
     * @param sellerId
     * @param name
     * @param description
     * @param sellerPrice
     * @param expertPrice
     * @param creationDate
     * @param publishDate
     */
    public ProductModel(int id, int sellerId, String name, String description, float sellerPrice, Float expertPrice, Timestamp creationDate, Timestamp publishDate) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.sellerPrice = sellerPrice;
        this.expertPrice = expertPrice;
        this.creationDate = creationDate;
        this.publishDate = publishDate;
        if(expertPrice != null && expertPrice != 0.0f){
            publish(expertPrice);
        }else{
            submit();
        }
    }

    // -----------------------------------------------------------------
    // Methods

    /**
     * Product's state = WAITING_ESTIMATION.
     *
     * @return This.
     */
    public ProductModel submit() {
        state = ProductState.WAITING_ESTIMATION;
        return this;
    }

    /**
     * @return TRUE if state == WAITING_ESTIMATION.
     */
    public boolean isSubmitted() {
        return state == ProductState.WAITING_ESTIMATION;
    }

    /**
     * Product's state = AVAILABLE.
     *
     * @param expertPrice
     * @return This.
     */
    public ProductModel publish(Float expertPrice) {
        this.expertPrice = expertPrice;
        state = ProductState.AVAILABLE;
        return this;
    }

    /**
     * @return TRUE if state == AVAILABLE.
     */
    public boolean isAvailable() {
        return state == ProductState.AVAILABLE;
    }

    /**
     * Product's state = UNAVAILABLE.
     *
     * @return This.
     */
    public ProductModel sell() {
        state = ProductState.UNAVAILABLE;
        return this;
    }

    /**
     * @return TRUE if state == UNAVAILABLE.
     */
    public boolean isUnavailable() {
        return state == ProductState.UNAVAILABLE;
    }

    // -----------------------------------------------------------------
    // Getters & Setters

    public int getId() {
        return id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getSellerPrice() {
        return sellerPrice;
    }

    public Float getExpertPrice() {
        return expertPrice;
    }

    public void setExpertPrice(Float expertPrice) {
        this.expertPrice = expertPrice;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }
}


