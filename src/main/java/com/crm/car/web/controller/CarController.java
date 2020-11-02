package com.crm.car.web.controller;

import com.crm.car.dao.entity.Car;
import com.crm.car.dao.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
}
