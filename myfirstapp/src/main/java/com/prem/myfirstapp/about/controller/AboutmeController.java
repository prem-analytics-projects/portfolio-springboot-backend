package com.prem.myfirstapp.about.controller;

import com.prem.myfirstapp.about.dto.AboutmeRequest;
import com.prem.myfirstapp.about.dto.AboutmeResponse;
import com.prem.myfirstapp.about.service.AboutmeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/about-sections")
public class AboutmeController {

    private final AboutmeService service;

    public AboutmeController(AboutmeService service) {
        this.service = service;
    }

    @GetMapping
    public List<AboutmeResponse> getAbout(@RequestParam(required = false) Long profileId) {
        return service.getActive(profileId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AboutmeResponse create(@Valid @RequestBody AboutmeRequest request) {
        return service.create(request);
    }
}