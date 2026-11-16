
package org.example.controller;

import org.example.TrainingSession;
import org.example.Trainer;
import org.example.Lab;

import org.example.service.TrainingSessionService;
import org.example.service.TrainerService;
import org.example.service.LabService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TrainingSessionController {

    private final TrainingSessionService sessionService;
    private final TrainerService trainerService;
    private final LabService labService;

    public TrainingSessionController(
            TrainingSessionService sessionService,
            TrainerService trainerService,
            LabService labService) {

        this.sessionService = sessionService;
        this.trainerService = trainerService;
        this.labService = labService;
    }

    // Display all sessions
    @GetMapping("/sessions")
    public String sessionsPage(Model model) {

        model.addAttribute("sessions", sessionService.getAllSessions());
        model.addAttribute("session", new TrainingSession());
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("labs", labService.getAllLabs());

        return "sessions";
    }

    // Save or update session
    @PostMapping("/sessions/save")
    public String saveSession(
            @ModelAttribute("session") TrainingSession session,
            @RequestParam("trainerId") int trainerId,
            @RequestParam("labId") int labId,
            Model model) {

        Trainer trainer = trainerService.getTrainerById(trainerId);
        Lab lab = labService.getLabById(labId);

        if (trainer == null || lab == null) {
            model.addAttribute("error", "Invalid trainer or computer lab.");

            loadSessionPage(model, session);
            return "sessions";
        }

        session.setTrainer(trainer);
        session.setLab(lab);

        try {
            sessionService.saveSession(session);
            return "redirect:/sessions";

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage());

            loadSessionPage(model, session);
            return "sessions";
        }
    }

    // Load sessions page data
    private void loadSessionPage(Model model, TrainingSession session) {

        model.addAttribute("session", session);
        model.addAttribute("sessions", sessionService.getAllSessions());
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("labs", labService.getAllLabs());
    }

    // Edit session
    @GetMapping("/sessions/edit/{id}")
    public String editSession(@PathVariable int id, Model model) {

        TrainingSession session = sessionService.getSessionById(id);

        if (session == null) {
            return "redirect:/sessions";
        }

        loadSessionPage(model, session);

        return "sessions";
    }

    // Delete session
    @GetMapping("/sessions/delete/{id}")
    public String deleteSession(@PathVariable int id) {

        sessionService.deleteSession(id);

        return "redirect:/sessions";
    }
}
