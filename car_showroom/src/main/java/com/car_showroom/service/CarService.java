package com.car_showroom.service;

import com.car_showroom.dto.CarDTO;
import com.car_showroom.entity.CarEntity;
import com.car_showroom.entity.CarShowroomEntity;
import com.car_showroom.repository.CarRepository;
import com.car_showroom.repository.CarShowroomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarShowroomRepository carShowroomRepository;

    public CarService(CarRepository carRepository, CarShowroomRepository carShowroomRepository) {
        this.carRepository = carRepository;
        this.carShowroomRepository = carShowroomRepository;
    }

    public ResponseEntity<List<CarDTO>> cars() {
        return ResponseEntity.ok().body(carRepository.findAll().stream().map(CarDTO::toCarDto).toList());
    }

    public ResponseEntity<CarDTO> car(Long id) {
        try {
            return ResponseEntity.ok().body(CarDTO.toCarDto(carRepository.findById(id).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<CarDTO> addCar(CarEntity car, Long carShop) {
        try {
            CarShowroomEntity carShowroom = carShowroomRepository.findById(carShop).orElseThrow();

            carShowroom.getCars().add(car);
            car.setCarShop(carShowroom);
            car.setBooked(false);

            return ResponseEntity.ok().body(CarDTO.toCarDto(carRepository.save(car)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<CarDTO> updateCar(CarEntity changedCar, Long id) {
        try {
            CarEntity car = carRepository.findById(id).orElseThrow();
            if(changedCar.getName() != null) {
                car.setName(changedCar.getName());
            }
            if(changedCar.getDesc() != null) {
                car.setDesc(changedCar.getDesc());
            }
            if(changedCar.getPrice() != null) {
                car.setPrice(changedCar.getPrice());
            }
            return ResponseEntity.ok().body(CarDTO.toCarDto(carRepository.save(car)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<String> deleteCar(Long id) {
        try {
            CarEntity car = carRepository.findById(id).orElseThrow();

            if(car.getClient() != null) {
                car.getClient().setCar(null);
            }
            car.getCarShop().getCars().remove(car);

            carRepository.delete(car);

            return ResponseEntity.ok().body("Удаление машины c id = " + id + " прошло успешно!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
