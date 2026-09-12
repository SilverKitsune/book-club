package com.bookclub.service;

import com.bookclub.entity.Meeting;
import com.bookclub.repository.MeetingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    public Meeting findById(Long id) {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Встреча с id " + id + " не найдена"));
    }

    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public void deleteById(Long id) {
        meetingRepository.deleteById(id);
    }
}