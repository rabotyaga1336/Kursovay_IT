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

    //контроллер вывода всех автосалонов
    @GetMapping("/")
    public ResponseEntity<List<CarShowroomDTO>> carShowrooms() {
        return carShowroomService.carShowrooms();
    }

    //контроллер вывода автосалона по id
    @GetMapping("/{id}")
    public ResponseEntity<CarShowroomDTO> carShowroom(@PathVariable Long id) {
        return carShowroomService.carShowroom(id);
    }

    //контроллер вывода стоимости всех автомобилей в салоне
    @GetMapping("/price/cars/{id}")
    public ResponseEntity<String> getPriceOfAllCars(@PathVariable Long id) {
        return carShowroomService.getPriceOfAllCars(id);
    }

    //контроллер добавления автосалона
    @PostMapping("/")
    public ResponseEntity<CarShowroomDTO> createCarShowroom(@RequestBody CarShowroomEntity carShowroom) {
        return carShowroomService.createCarShowroom(carShowroom);
    }

    //контроллер обновления информации о автосалоне
    @PatchMapping("/{id}")
    public ResponseEntity<CarShowroomDTO> updateCarShowroom(@RequestBody CarShowroomEntity carShowroom, @PathVariable Long id) {
        return carShowroomService.updateCarShowroom(carShowroom, id);
    }

    //контроллер удаления автосалона
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarShowroom(@PathVariable Long id) {
        return carShowroomService.deleteCarShowroom(id);
    }
}
