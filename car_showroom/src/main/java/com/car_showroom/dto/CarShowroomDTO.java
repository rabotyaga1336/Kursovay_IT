package com.car_showroom.dto;

import com.car_showroom.entity.CarEntity;
import com.car_showroom.entity.CarShowroomEntity;
import com.car_showroom.entity.ClientEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarShowroomDTO {

    private Long id;
    private String name;
    private Long cntEmpl;
    private List<Long> carsId;
    private List<Long> clientsId;

    public CarShowroomDTO(Long id, String name, Long cntEmpl, List<Long> carsId, List<Long> clientsId) {
        this.id = id;
        this.name = name;
        this.cntEmpl = cntEmpl;
        this.carsId = carsId;
        this.clientsId = clientsId;
    }

    public static CarShowroomDTO toCarShowroomDto(CarShowroomEntity carShowroom) {
        return new CarShowroomDTO(carShowroom.getId(), carShowroom.getName(), carShowroom.getCntEmpl(), carShowroom.getCars().isEmpty() ? new ArrayList<>() : carShowroom.getCars().stream().map(CarEntity::getId).toList(), carShowroom.getClients().isEmpty() ? new ArrayList<>() : carShowroom.getClients().stream().map(ClientEntity::getId).toList());
    }
}
