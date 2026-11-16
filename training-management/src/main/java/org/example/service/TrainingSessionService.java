
package org.example.service;

import org.example.TrainingSession;
import org.example.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingSessionService {

    private final TrainingSessionRepository sessionRepository;

    public TrainingSessionService(TrainingSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Get all sessions
    public List<TrainingSession> getAllSessions() {
        return sessionRepository.findAll();
    }

    // Add or update session with lab availability validation
    public TrainingSession saveSession(TrainingSession session) {

        boolean labAlreadyBooked =
                sessionRepository
                        .existsByLab_LabIdAndSessionDateAndStartTimeLessThanAndEndTimeGreaterThanAndSessionIdNot(
                                session.getLab().getLabId(),
                                session.getSessionDate(),
                                session.getEndTime(),
                                session.getStartTime(),
                                session.getSessionId()
                        );

        if (labAlreadyBooked) {
            throw new IllegalArgumentException(
                    "This computer lab is already booked during this time."
            );
        }

        return sessionRepository.save(session);
    }

    // Get session by ID
    public TrainingSession getSessionById(int id) {
        return sessionRepository.findById(id).orElse(null);
    }

    // Delete session
    public void deleteSession(int id) {
        sessionRepository.deleteById(id);
    }
}
