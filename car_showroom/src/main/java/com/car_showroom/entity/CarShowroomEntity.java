package com.car_showroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//идентификация сущности Car_Showroom
@Entity
@Table(name = "carshowrooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarShowroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long cntEmpl;

    //обьявление связей для сущности Car_Showroom
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<ClientEntity> clients = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CarEntity> cars = new ArrayList<>();
}
