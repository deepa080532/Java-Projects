
package org.example.controller;

import org.example.Lab;
import org.example.service.LabService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LabController {

    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    // Display all labs
    @GetMapping("/labs")
    public String labsPage(Model model) {
        model.addAttribute("labs", labService.getAllLabs());
        model.addAttribute("lab", new Lab());
        return "labs";
    }

    // Add or update lab
    @PostMapping("/labs/save")
    public String saveLab(@ModelAttribute("lab") Lab lab) {
        System.out.println("ADD LAB: " + lab.getLabName());
        labService.saveLab(lab);
        return "redirect:/labs";
    }

    // Load lab details for editing
    @GetMapping("/labs/edit/{id}")
    public String editLab(@PathVariable("id") int id, Model model) {
        Lab lab = labService.getLabById(id);

        if (lab == null) {
            return "redirect:/labs";
        }

        model.addAttribute("lab", lab);
        model.addAttribute("labs", labService.getAllLabs());
        return "labs";
    }

    // Delete lab
    @GetMapping("/labs/delete/{id}")
    public String deleteLab(@PathVariable("id") int id) {
        labService.deleteLab(id);
        return "redirect:/labs";
    }
}
