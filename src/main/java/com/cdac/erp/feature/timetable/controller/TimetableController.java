package com.cdac.erp.feature.timetable.controller;

import com.cdac.erp.feature.timetable.dto.TimetableEntryRequest;
import com.cdac.erp.feature.timetable.dto.TimetableEntryResponse;
import com.cdac.erp.feature.timetable.service.ITimetableService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/timetable")
public class TimetableController {

    @Autowired
    private ITimetableService timetableService;

    @PostMapping
    public ResponseEntity<TimetableEntryResponse> createTimetableEntry(@RequestBody TimetableEntryRequest request) { // Changed return type
        TimetableEntryResponse newEntryDto = timetableService.createTimetableEntry(request);
        return new ResponseEntity<>(newEntryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<TimetableEntryResponse> getAllTimetableEntries() {
        return timetableService.getAllTimetableEntries();
    }
}