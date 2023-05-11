package com.example.securityhibernate.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "voucher")
    private float voucher;

    @OneToMany(mappedBy = "coupon")
    private Set<Restaurant> listRestaurant;

    @OneToMany(mappedBy = "coupon")
    private Set<Orders> listOrders;

    public Set<Orders> getListOrders() {
        return listOrders;
    }

    public void setListOrders(Set<Orders> listOrders) {
        this.listOrders = listOrders;
    }

    public Set<Restaurant> getListRestaurant() {
        return listRestaurant;
    }

    public void setListRestaurant(Set<Restaurant> listRestaurant) {
        this.listRestaurant = listRestaurant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getVoucher() {
        return voucher;
    }

    public void setVoucher(float voucher) {
        this.voucher = voucher;
    }
}
