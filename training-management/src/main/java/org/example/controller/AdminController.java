
package org.example.controller;

import org.example.service.AdminService;
import org.example.service.TrainerService;
import org.example.service.LabService;
import org.example.service.TrainingSessionService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final TrainerService trainerService;
    private final LabService labService;
    private final TrainingSessionService sessionService;

    public AdminController(AdminService adminService,
                           TrainerService trainerService,
                           LabService labService,
                           TrainingSessionService sessionService) {
        this.adminService = adminService;
        this.trainerService = trainerService;
        this.labService = labService;
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public String loginPage() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        if (adminService.login(username, password)) {
            session.setAttribute("loggedInAdmin", username);
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Invalid username or password");
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/";
        }

        model.addAttribute("totalTrainers",
                trainerService.getAllTrainers().size());

        model.addAttribute("totalLabs",
                labService.getAllLabs().size());

        model.addAttribute("totalSessions",
                sessionService.getAllSessions().size());

        model.addAttribute("recentSessions",
                sessionService.getAllSessions());

        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
