package com.fruitybeastsknowledge.mustache.helpers;

/**
 * Created by akumanotatsujin on 24/11/17.
 */

public class Order {

        private String id ;
        private String to ;
        private String from ;
        private String state ;

    public Order() {
// default ...
    }

    public Order(String id,String from, String to, String state) {
        this.id = id ;
        this.from = from;
        this.to = to;
        this.state = state;
    }


    public Order(String from, String to, String state) {
        this.from = from;
        this.to = to;
        this.state = state;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
