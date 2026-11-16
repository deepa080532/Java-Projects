
package org.example.repository;

import org.example.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TrainingSessionRepository
        extends JpaRepository<TrainingSession, Integer> {

    boolean existsByLab_LabIdAndSessionDateAndStartTimeLessThanAndEndTimeGreaterThanAndSessionIdNot(
            int labId,
            LocalDate sessionDate,
            LocalTime newEndTime,
            LocalTime newStartTime,
            int sessionId
    );
}
