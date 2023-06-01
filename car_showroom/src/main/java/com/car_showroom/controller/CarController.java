package com.car_showroom.controller;

import com.car_showroom.dto.CarDTO;
import com.car_showroom.entity.CarEntity;
import com.car_showroom.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CarDTO>> cars() {
        return carService.cars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> car(@PathVariable Long id) {
        return carService.car(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CarDTO> addCar(@RequestBody CarEntity car, @PathVariable Long id) {
        return carService.addCar(car, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@RequestBody CarEntity car, @PathVariable Long id) {
        return carService.updateCar(car, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        return carService.deleteCar(id);
    }
}
