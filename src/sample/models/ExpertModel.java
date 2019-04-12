package sample.models;

import sample.managers.DataManager;

public class ExpertModel {

    private int id;
    private String name;
    private int rating;

    public ExpertModel(String name, int rating){
        id = DataManager.getInstance().getNextExpertId();
        this.name = name;
        this.rating = rating;

    }

    public ExpertModel(int id, String name, int rating){
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public int getId(){ return id; }
}
