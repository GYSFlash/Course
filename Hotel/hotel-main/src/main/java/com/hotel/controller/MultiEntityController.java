package com.hotel.controller;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.service.MultiEntityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/multi")
@PreAuthorize("hasRole('ADMIN')")
public class MultiEntityController extends BaseController{
    private MultiEntityService service;
    public MultiEntityController(MultiEntityService service) {
        this.service = service;
    }
    @GetMapping("/sort/{sortBy}")
    public List<Object> sortRoomAndService(@PathVariable("sortBy") String sortBy) {
        return service.sort(sortBy);
    }
}
