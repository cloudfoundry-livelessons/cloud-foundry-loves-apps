package com.example.cfdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@SpringBootApplication
public class CfDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfDemoApplication.class, args);
    }
}

@Component
class SampleDataCLR implements CommandLineRunner {

    private final CarRepository carRepository;

    SampleDataCLR(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Civic", "330i", "300", "Tesla")
                .forEach(name -> carRepository.save(new Car(name)));
        carRepository.findAll().forEach(System.out::println);
    }
}

interface CarRepository extends JpaRepository<Car, Long> {
}

@Entity
class Car {


    public Car() {// why JPA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Car(String make) {

        this.make = make;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String make;
}

@RestController
class GreetingsRestController {

    @GetMapping("/hi")
    String hi() {
        return "Hello, world";
    }
}