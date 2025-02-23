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
import com.yuchen.assignment3.repository.StudentRepository;


import java.util.List;
import java.util.UUID;

@Controller
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ProgramRepository programRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/enrollments")
    public String showEnrollments(@RequestParam("studentId") String studentId, Model model) {
    	Student student = studentRepository.findByStudentId(studentId);
        if (student == null) {
            return "redirect:/login";
        }

        List<Enrollment> studentEnrollments = enrollmentRepository.findByStudentId(student.getStudentId());
        model.addAttribute("enrollments", studentEnrollments);
        model.addAttribute("studentId", student.getStudentId()); 
        return "enrollments";
    }


    @GetMapping("/checkout")
    public String checkout(
            @RequestParam("studentId") String studentId,
            @RequestParam("programCode") String programCode,
            Model model) {

    	Student student = studentRepository.findByStudentId(studentId);
        if (student == null) {
            return "redirect:/login";
        }

        Program program = programRepository.findById(programCode).orElse(null);
        if (program == null) {
            return "redirect:/studentPrograms";
        }

        String[] startDates = program.getStartDates().split(",");

        model.addAttribute("studentId", student.getStudentId());
        model.addAttribute("programCode", program.getProgramCode());
        model.addAttribute("programName", program.getProgramName());
        model.addAttribute("duration", program.getDuration());
        model.addAttribute("fee", program.getFee());
        model.addAttribute("startDates", startDates);

        return "checkout";
    }


    @PostMapping("/processPayment")
    public String processPayment(
            @RequestParam("studentId") String studentId,
            @RequestParam("programCode") String programCode,
            @RequestParam("startDate") String startDate,
            @RequestParam("fee") double fee,
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("cvv") String cvv,
            Model model) {

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return "redirect:/login";
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setApplicationNo(UUID.randomUUID().toString().substring(0, 8));
        enrollment.setStudentId(student.getStudentId());
        enrollment.setProgramCode(programCode);
        enrollment.setStartDate(startDate);
        enrollment.setAmountPaid(fee);
        enrollment.setStatus("Confirmed");

        enrollmentRepository.save(enrollment);

        model.addAttribute("enrollment", enrollment);
        model.addAttribute("studentId", studentId); 

        return "enrollmentConfirmation";
    }


}
