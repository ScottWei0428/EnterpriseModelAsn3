package com.yuchen.assignment3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import com.yuchen.assignment3.entity.Student;
import com.yuchen.assignment3.repository.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    private Student currentStudent;

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
    

	 @GetMapping("/logout")
	 public String logout(HttpSession session) {
	     session.invalidate(); // 清除session
	     return "redirect:/login";
	 }

 
    @PostMapping("/register")
    public String registerStudent(
            @RequestParam("studentId") int studentId,
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
        } else {
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
            model.addAttribute("successMessage", "Registration successful. Please login.");
            return "login";
        }
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        Student student = studentRepository.findByUserName(userName);

        if (student != null && student.getPassword().equals(password)) {
            session.setAttribute("loggedInStudent", student); // 存入 session
            return "redirect:/enrollments";
        } else {
            model.addAttribute("loginErrorMessage", "Invalid username or password!");
            return "login";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", loggedInStudent);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", loggedInStudent);
        return "editProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam("password") String password,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            HttpSession session) {

        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        loggedInStudent.setPassword(password);
        loggedInStudent.setFirstname(firstname);
        loggedInStudent.setLastname(lastname);
        loggedInStudent.setAddress(address);
        loggedInStudent.setCity(city);
        loggedInStudent.setPostalCode(postalCode);

        studentRepository.save(loggedInStudent);
        session.setAttribute("loggedInStudent", loggedInStudent);

        return "redirect:/profile";
    }

}
