package com.car_showroom.service;

import com.car_showroom.dto.CarShowroomDTO;
import com.car_showroom.entity.CarEntity;
import com.car_showroom.entity.CarShowroomEntity;
import com.car_showroom.repository.CarShowroomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarShowroomService {

    private final CarShowroomRepository carShowroomRepository;

    public CarShowroomService(CarShowroomRepository carShowroomRepository) {
        this.carShowroomRepository = carShowroomRepository;
    }

    public ResponseEntity<List<CarShowroomDTO>> carShowrooms() {
        return ResponseEntity.ok().body(carShowroomRepository.findAll().stream().map(CarShowroomDTO::toCarShowroomDto).toList());
    }

    public ResponseEntity<CarShowroomDTO> carShowroom(Long id) {
        try {
            return ResponseEntity.ok().body(CarShowroomDTO.toCarShowroomDto(carShowroomRepository.findById(id).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //вычисляемое поле находит стоимость всех машин автосалона
    public ResponseEntity<String> getPriceOfAllCars(Long id) {
        try {
            CarShowroomEntity carShowroom = carShowroomRepository.findById(id).orElseThrow();
            return ResponseEntity.ok().body("Стоимость всех машин: " + carShowroom.getCars().stream().map(CarEntity::getPrice).reduce(Long::sum).orElseThrow());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<CarShowroomDTO> createCarShowroom(CarShowroomEntity carShowroom) {
        return ResponseEntity.ok().body(CarShowroomDTO.toCarShowroomDto(carShowroomRepository.save(carShowroom)));
    }

    public ResponseEntity<CarShowroomDTO> updateCarShowroom(CarShowroomEntity changedCarShowroom, Long id) {
        try {
            CarShowroomEntity carShowroom = carShowroomRepository.findById(id).orElseThrow();
            if(changedCarShowroom.getName() != null) {
                carShowroom.setName(changedCarShowroom.getName());
            }
            if(changedCarShowroom.getCntEmpl() != null) {
                carShowroom.setCntEmpl(changedCarShowroom.getCntEmpl());
            }
            return ResponseEntity.ok().body(CarShowroomDTO.toCarShowroomDto(carShowroomRepository.save(carShowroom)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<String> deleteCarShowroom(Long id) {
        try {
            CarShowroomEntity carShowroom = carShowroomRepository.findById(id).orElseThrow();

            carShowroom.getClients().forEach(client -> client.getCarShops().remove(carShowroom));
            carShowroomRepository.delete(carShowroom);

            return ResponseEntity.ok().body("Удаление автосалона id = " + id + " прошло успешно!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
