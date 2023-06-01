package com.car_showroom.controller;

import com.car_showroom.dto.CarShowroomDTO;
import com.car_showroom.entity.CarShowroomEntity;
import com.car_showroom.service.CarShowroomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carshop")
public class CarShowroomController {

    private final CarShowroomService carShowroomService;

    public CarShowroomController(CarShowroomService carShowroomService) {
        this.carShowroomService = carShowroomService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CarShowroomDTO>> carShowrooms() {
        return carShowroomService.carShowrooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarShowroomDTO> carShowroom(@PathVariable Long id) {
        return carShowroomService.carShowroom(id);
    }

    @GetMapping("/price/cars/{id}")
    public ResponseEntity<String> getPriceOfAllCars(@PathVariable Long id) {
        return carShowroomService.getPriceOfAllCars(id);
    }

    @PostMapping("/")
    public ResponseEntity<CarShowroomDTO> createCarShowroom(@RequestBody CarShowroomEntity carShowroom) {
        return carShowroomService.createCarShowroom(carShowroom);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarShowroomDTO> updateCarShowroom(@RequestBody CarShowroomEntity carShowroom, @PathVariable Long id) {
        return carShowroomService.updateCarShowroom(carShowroom, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarShowroom(@PathVariable Long id) {
        return carShowroomService.deleteCarShowroom(id);
    }
}
