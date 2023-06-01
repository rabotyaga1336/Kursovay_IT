package com.car_showroom.dto;

import com.car_showroom.entity.CarEntity;
import lombok.Data;

@Data
public class CarDTO {

    private Long id;
    private String name;
    private Long price;
    private String desc;
    private boolean isBooked;
    private Long carShopId;
    private Long clientId;

    public CarDTO(Long id, String name, Long price, String desc, boolean isBooked, Long carShopId, Long clientId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.isBooked = isBooked;
        this.carShopId = carShopId;
        this.clientId = clientId;
    }

    //
    public static CarDTO toCarDto(CarEntity car) {
        return new CarDTO(car.getId(), car.getName(), car.getPrice(), car.getDesc(), car.getIsBooked(), car.getCarShop().getId(), car.getClient() != null ? car.getClient().getId() : 0L);
    }
}
