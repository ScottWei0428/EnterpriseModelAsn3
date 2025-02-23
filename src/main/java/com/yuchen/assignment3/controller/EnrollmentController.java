package com.yuchen.assignment3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuchen.assignment3.entity.Enrollment;
import com.yuchen.assignment3.entity.Program;
import com.yuchen.assignment3.entity.Student;
import com.yuchen.assignment3.repository.EnrollmentRepository;
import com.yuchen.assignment3.repository.ProgramRepository;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;

@Controller
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ProgramRepository programRepository;

    @GetMapping("/enrollments")
    public String showEnrollments(Model model, HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        List<Enrollment> studentEnrollments = enrollmentRepository.findByStudentId(loggedInStudent.getStudentId());
        model.addAttribute("enrollments", studentEnrollments);
        return "enrollments";
    }

    @GetMapping("/checkout")
    public String checkout(
            @RequestParam("programCode") String programCode,
            Model model,
            HttpSession session) {

        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        Program program = programRepository.findById(programCode).orElse(null);
        if (program == null) {
            return "redirect:/studentPrograms";
        }

        String[] startDates = program.getStartDates().split(",");

        model.addAttribute("studentId", loggedInStudent.getStudentId());
        model.addAttribute("programCode", program.getProgramCode());
        model.addAttribute("programName", program.getProgramName());
        model.addAttribute("duration", program.getDuration());
        model.addAttribute("fee", program.getFee());
        model.addAttribute("startDates", startDates);

        return "checkout";
    }

    @PostMapping("/processPayment")
    public String processPayment(
            @RequestParam("programCode") String programCode,
            @RequestParam("startDate") String startDate,
            @RequestParam("fee") double fee,
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("cvv") String cvv,
            HttpSession session,
            Model model) {

        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setApplicationNo(UUID.randomUUID().toString().substring(0, 8));
        enrollment.setStudentId(loggedInStudent.getStudentId());
        enrollment.setProgramCode(programCode);
        enrollment.setStartDate(startDate);
        enrollment.setAmountPaid(fee);
        enrollment.setStatus("Confirmed");

        enrollmentRepository.save(enrollment);

        model.addAttribute("enrollment", enrollment);

        return "enrollmentConfirmation";
    }
}
