
package org.example.service;

import org.example.Lab;
import org.example.repository.LabRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabService {

    private final LabRepository labRepository;

    public LabService(LabRepository labRepository) {
        this.labRepository = labRepository;
    }

    // Get all labs
    public List<Lab> getAllLabs() {
        return labRepository.findAll();
    }

    // Add or update lab
    public Lab saveLab(Lab lab) {
        return labRepository.save(lab);
    }

    // Get lab by ID
    public Lab getLabById(int id) {
        return labRepository.findById(id).orElse(null);
    }

    // Delete lab
    public void deleteLab(int id) {
        labRepository.deleteById(id);
    }
}
