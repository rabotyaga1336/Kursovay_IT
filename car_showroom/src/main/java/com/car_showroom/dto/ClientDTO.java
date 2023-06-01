package com.car_showroom.dto;

import com.car_showroom.entity.CarShowroomEntity;
import com.car_showroom.entity.ClientEntity;
import lombok.Data;

import java.util.List;

@Data
public class ClientDTO {

    private Long id;
    private String fio;
    private String phNum;
    private Long carId;
    private List<Long> carShopsId;

    public ClientDTO(Long id, String fio, String phNum, Long carId, List<Long> carShopsId) {
        this.id = id;
        this.fio = fio;
        this.phNum = phNum;
        this.carId = carId;
        this.carShopsId = carShopsId;
    }

    public static ClientDTO toClientDto(ClientEntity client) {
        return new ClientDTO(client.getId(), client.getFio(), client.getPhNum(), client.getCar() != null ? client.getCar().getId() : 0L, client.getCarShops().stream().map(CarShowroomEntity::getId).toList());
    }
}
