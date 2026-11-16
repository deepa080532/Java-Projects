
package org.example.controller;

import org.example.Trainer;
import org.example.service.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    // Display all trainers
    @GetMapping("/trainers")
    public String trainersPage(Model model) {

        model.addAttribute("trainers", trainerService.getAllTrainers());

        // Empty object for adding a new trainer
        model.addAttribute("trainer", new Trainer());

        return "trainers";
    }

    // Load trainer details for editing
    @GetMapping("/trainers/edit/{id}")
    public String editTrainer(@PathVariable int id, Model model) {

        Trainer trainer = trainerService.getTrainerById(id);

        if (trainer == null) {
            return "redirect:/trainers";
        }

        model.addAttribute("trainer", trainer);
        model.addAttribute("trainers", trainerService.getAllTrainers());

        return "trainers";
    }

    // Save new trainer or update existing trainer
    @PostMapping("/trainers/save")
    public String saveTrainer(@ModelAttribute Trainer trainer) {

        trainerService.saveTrainer(trainer);

        return "redirect:/trainers";
    }

    // Delete trainer
    @GetMapping("/trainers/delete/{id}")
    public String deleteTrainer(@PathVariable int id) {

        trainerService.deleteTrainer(id);

        return "redirect:/trainers";
    }
}
