package com.yuchen.assignment3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuchen.assignment3.entity.Student;
import com.yuchen.assignment3.repository.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(
            @RequestParam("studentId") String studentId,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            Model model) {

        if (studentRepository.existsById(studentId)) {
            model.addAttribute("errorMessage", "Student ID already exists!");
            return "register";
        }

  
        if (studentRepository.existsByUserName(userName)) {
            model.addAttribute("errorMessage", "Username already taken! Please choose another.");
            return "register";
        }


        Student student = new Student();
        student.setStudentId(studentId);
        student.setUserName(userName);
        student.setPassword(password);
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setAddress(address);
        student.setCity(city);
        student.setPostalCode(postalCode);

        studentRepository.save(student);

        model.addAttribute("successMessage", "Registration successful! Please login.");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            Model model) {

        Student student = studentRepository.findByUserName(userName);

        if (student != null && student.getPassword().equals(password)) {
            return "redirect:/enrollments?studentId=" + student.getStudentId();
        } else {
            model.addAttribute("loginErrorMessage", "Invalid username or password!");
            return "login";
        }
    }

    @GetMapping("/profile")
    public String showProfile(@RequestParam("studentId") String studentId, Model model) {
        Student student = studentRepository.findByStudentId(studentId);
        if (student == null) {
            return "redirect:/login";
        }
        model.addAttribute("student", student);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(@RequestParam("studentId") String studentId, Model model) {
        Student student = studentRepository.findByStudentId(studentId);
        if (student == null) {
            return "redirect:/login";
        }
        model.addAttribute("student", student);
        return "editProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam("studentId") String studentId,  // studentId 不能是可选的
            @RequestParam("password") String password,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode) {

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return "redirect:/login";
        }

        student.setPassword(password);
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setAddress(address);
        student.setCity(city);
        student.setPostalCode(postalCode);

        studentRepository.save(student);

        return "redirect:/profile?studentId=" + studentId;
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
