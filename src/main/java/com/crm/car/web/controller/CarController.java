package com.crm.car.web.controller;

import com.crm.car.dao.entity.Car;
import com.crm.car.dao.repository.CarRepository;
import com.crm.car.service.InstagrammService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/cars")
public class CarController {
    private final CarRepository carRepository;
    private final InstagrammService instagrammService;

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


    @GetMapping("/posts/acc-name/{accName}/post-num/{postNum}")
    public String save(Model theModel, @PathVariable("accName") String accName, @PathVariable("postNum") String postNumber) throws IOException {
        List<String> service = instagrammService.servie(accName, Integer.parseInt(postNumber));
        theModel.addAttribute("posts", service);
        return "posts/list";
    }


    @GetMapping("/posts/acc-name/{accName}/code/{code}")
    public String save2(Model theModel, @PathVariable("accName") String accName, @PathVariable("code") String code) throws IOException {
        List<String> service = instagrammService.getTextByCode(accName, code);
        theModel.addAttribute("posts", service);
        return "posts/list";
    }
}
