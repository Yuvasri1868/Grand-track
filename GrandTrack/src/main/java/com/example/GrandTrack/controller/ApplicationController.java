package com.example.GrandTrack.controller;

import com.example.GrandTrack.entity.Application;
import com.example.GrandTrack.entity.Faculty;
import com.example.GrandTrack.service.ApplicationService;
import com.example.GrandTrack.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final FacultyService facultyService;

    public ApplicationController(
            ApplicationService applicationService,
            FacultyService facultyService) {

        this.applicationService = applicationService;
        this.facultyService = facultyService;
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(
            @PathVariable Long id) {

        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(
            @RequestBody Application application,
            @RequestParam Long facultyId) {

        return facultyService.getFacultyById(facultyId)
                .map(faculty -> {

                    application.setFaculty(faculty);

                    Application savedApplication =
                            applicationService.saveApplication(application);

                    return ResponseEntity.ok(savedApplication);

                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id) {

        applicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }
}