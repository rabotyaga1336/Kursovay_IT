package com.car_showroom.controller;

import com.car_showroom.dto.ClientDTO;
import com.car_showroom.entity.ClientEntity;
import com.car_showroom.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //контроллер вывода всех клиентов
    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> clients() {
        return clientService.clients();
    }

    //котроллер вывода клиента по id
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> client(@PathVariable Long id) {
        return clientService.client(id);
    }

    //контроллер добавление клиента
    @PostMapping("/{id}")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientEntity client, @PathVariable Long id) {
        return clientService.addClient(client, id);
    }

    //контроллер "покупки" пользователем автомобиля
    @PostMapping("/add/car/{clientId}/{carId}")
    public ResponseEntity<ClientDTO> addCarToClient(@PathVariable Long clientId, @PathVariable Long carId) {
        return clientService.addCarToClient(carId, clientId);
    }

    //контроллер обновления информации о клиенте
    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientEntity client, @PathVariable Long id) {
        return clientService.updateClient(client, id);
    }

    //контроллер удаления клиента
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }

    //контроллер "продажи" клиентом автомобиля
    @DeleteMapping("/delete/car/{clientId}")
    public ResponseEntity<ClientDTO> deleteCarFromUser(@PathVariable Long clientId) {
        return clientService.deleteCarFromUser(clientId);
    }
}
