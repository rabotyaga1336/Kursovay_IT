package com.car_showroom.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@RequiredArgsConstructor
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Long price;
    @Column(name = "description")
    private String desc;
    @Column(name = "isbooked")
    private boolean isBooked;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private CarShowroomEntity carShop;
    @OneToOne(cascade = CascadeType.PERSIST)
    private ClientEntity client;

    public boolean getIsBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
