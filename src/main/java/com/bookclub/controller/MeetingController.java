package com.bookclub.controller;

import com.bookclub.entity.Meeting;
import com.bookclub.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping
    public List<Meeting> getAll() {
        return meetingService.findAll();
    }

    @GetMapping("/{id}")
    public Meeting getById(@PathVariable Long id) {
        return meetingService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Meeting create(@RequestBody Meeting meeting) {
        return meetingService.save(meeting);
    }

    @PutMapping("/{id}")
    public Meeting update(@PathVariable Long id, @RequestBody Meeting meeting) {
        Meeting existing = meetingService.findById(id);
        existing.setDateTime(meeting.getDateTime());
        existing.setPlace(meeting.getPlace());
        existing.setStatus(meeting.getStatus());
        return meetingService.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        meetingService.deleteById(id);
    }
}