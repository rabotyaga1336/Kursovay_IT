package com.car_showroom.service;

import com.car_showroom.dto.ClientDTO;
import com.car_showroom.entity.CarEntity;
import com.car_showroom.entity.CarShowroomEntity;
import com.car_showroom.entity.ClientEntity;
import com.car_showroom.repository.CarRepository;
import com.car_showroom.repository.CarShowroomRepository;
import com.car_showroom.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final CarShowroomRepository carShowroomRepository;

    public ClientService(ClientRepository clientRepository, CarRepository carRepository, CarShowroomRepository carShowroomRepository) {
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
        this.carShowroomRepository = carShowroomRepository;
    }

    //метод вывода всех клиентов
    public ResponseEntity<List<ClientDTO>> clients() {
        return ResponseEntity.ok().body(clientRepository.findAll().stream().map(ClientDTO::toClientDto).toList());
    }

    //метод вывода клиента по id
    public ResponseEntity<ClientDTO> client(Long id) {
        try {
            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.findById(id).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //метод добавления клиента
    public ResponseEntity<ClientDTO> addClient(ClientEntity client, Long id) {
        try {
            CarShowroomEntity carShowroom = carShowroomRepository.findById(id).orElseThrow();

            carShowroom.getClients().add(client);
            client.getCarShops().add(carShowroom);
            client.setCar(null);

            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.save(client)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //метода "привязки" авто к клиенту/покупки авто клиентом
    public ResponseEntity<ClientDTO> addCarToClient(Long carId, Long clientId) {
        try {
            CarEntity car = carRepository.findById(carId).orElseThrow();
            ClientEntity client = clientRepository.findById(clientId).orElseThrow();

            if (!car.getIsBooked()) {
                client.setCar(car);
                car.setClient(client);
                car.setBooked(!car.getIsBooked());
            } else {
                throw new AlreadyBoundException();
            }

            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.save(client)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (AlreadyBoundException e) {
            return ResponseEntity.status(HttpStatus.IM_USED).build();
        }
    }

    //метода "отвязки" авто от клиенту/продажи авто клиентом
    public ResponseEntity<ClientDTO> deleteCarFromUser(Long clientId) {
        try {
            ClientEntity client = clientRepository.findById(clientId).orElseThrow();

            client.getCar().setClient(null);
            client.getCar().setBooked(!client.getCar().getIsBooked());
            client.setCar(null);

            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.save(client)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //метод обновления информации о клиенте
    public ResponseEntity<ClientDTO> updateClient(ClientEntity changedClient, Long id) {
        try {
            ClientEntity client = clientRepository.findById(id).orElseThrow();
            if (changedClient.getFio() != null) {
                client.setFio(changedClient.getFio());
            }
            if (changedClient.getPhNum() != null) {
                client.setPhNum(changedClient.getPhNum());
            }
            return ResponseEntity.ok().body(ClientDTO.toClientDto(clientRepository.save(client)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //метод удаления клиента
    public ResponseEntity<String> deleteClient(Long id) {
        try {
            ClientEntity client = clientRepository.findById(id).orElseThrow();

            if(client.getCar() != null) {
                client.getCar().setClient(null);
            }
            client.getCarShops().forEach(carShowroom -> carShowroom.getClients().remove(client));

            clientRepository.delete(client);

            return ResponseEntity.ok().body("Удаление клиента c id = " + id + " прошло успешно!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
