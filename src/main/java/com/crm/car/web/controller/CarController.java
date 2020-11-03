package com.crm.car.web.controller;

import com.crm.car.dao.entity.Car;
import com.crm.car.dao.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/cars")
public class CarController {
    private final CarRepository carRepository;

    @GetMapping(path = {"/", "/list"})
    public String getCars(Model theModel) {
        List<Car> cars = carRepository.findAll();
        theModel.addAttribute("cars", cars);
        return "car/list";
    }

    @GetMapping("/showFormForAdd")
    public String addCar(Model theModel) {
        Car car = Car.builder().id(UUID.randomUUID()).build();
        theModel.addAttribute("car", car);
        return "car/form-add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("car") Car car) {
        carRepository.save(car);
        return "redirect:/cars/list";
    }
}
