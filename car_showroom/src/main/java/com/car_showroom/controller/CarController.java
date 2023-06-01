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
//реализация контроллера
    public CarController(CarService carService) {
        this.carService = carService;
    }

    //контроллер вывода автомобилей
    @GetMapping("/")
    public ResponseEntity<List<CarDTO>> cars() {
        return carService.cars();
    }

    //контроллер вывода автомобилей по их id
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> car(@PathVariable Long id) {
        return carService.car(id);
    }

    //контроллер добавление автомобилей
    @PostMapping("/{id}")
    public ResponseEntity<CarDTO> addCar(@RequestBody CarEntity car, @PathVariable Long id) {
        return carService.addCar(car, id);
    }

    //контроллер обновление информации о автомобиле
    @PatchMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@RequestBody CarEntity car, @PathVariable Long id) {
        return carService.updateCar(car, id);
    }

    //удаление автомобиля
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        return carService.deleteCar(id);
    }
}
