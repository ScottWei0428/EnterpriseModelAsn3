package com.yuchen.assignment3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuchen.assignment3.entity.Program;
import com.yuchen.assignment3.repository.ProgramRepository;

@Controller
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    @GetMapping("/programs")
    public String showPrograms(@RequestParam("studentId") String studentId, Model model) {
        model.addAttribute("studentId", studentId);
        model.addAttribute("programList", programRepository.findAll());
        return "programs";
    }

    @GetMapping("/studentPrograms")
    public String showStudentPrograms(@RequestParam(required = false) String studentId, Model model) {
        model.addAttribute("studentId", studentId);
        model.addAttribute("programList", programRepository.findAll());
        return "studentPrograms";
    }

    @PostMapping("/addProgram")
    public String addProgram(
            @RequestParam("programCode") String programCode,
            @RequestParam("programName") String programName,
            @RequestParam("duration") String duration,
            @RequestParam("fee") double fee,
            @RequestParam("startDates") String startDates,
            @RequestParam("studentId") String studentId) {

        if (programRepository.existsById(programCode)) {
            return "redirect:/programs?studentId=" + studentId + "&error=ProgramCodeExists";
        }

        Program program = new Program();
        program.setProgramCode(programCode);
        program.setProgramName(programName);
        program.setDuration(duration);
        program.setFee(fee);
        program.setStartDates(startDates);
        programRepository.save(program);

        return "redirect:/programs?studentId=" + studentId;
    }

    @GetMapping("/deleteProgram")
    public String deleteProgram(@RequestParam("programCode") String programCode, @RequestParam("studentId") String studentId) {
        if (!programRepository.existsById(programCode)) {
            return "redirect:/programs?studentId=" + studentId + "&error=ProgramNotFound";
        }

        programRepository.deleteById(programCode);
        return "redirect:/programs?studentId=" + studentId;
    }

    @GetMapping("/editProgram")
    public String editProgram(@RequestParam("programCode") String programCode, @RequestParam("studentId") String studentId, Model model) {
        Program program = programRepository.findById(programCode).orElse(null);
        if (program == null) {
            return "redirect:/programs?studentId=" + studentId + "&error=ProgramNotFound";
        }

        model.addAttribute("program", program);
        model.addAttribute("studentId", studentId);
        return "editProgram";
    }

    @PostMapping("/updateProgram")
    public String updateProgram(
            @RequestParam("programCode") String programCode,
            @RequestParam("programName") String programName,
            @RequestParam("duration") String duration,
            @RequestParam("fee") double fee,
            @RequestParam("startDates") String startDates,
            @RequestParam("studentId") String studentId) {

        Program program = programRepository.findById(programCode).orElse(null);
        if (program == null) {
            return "redirect:/programs?studentId=" + studentId + "&error=ProgramNotFound";
        }

        program.setProgramName(programName);
        program.setDuration(duration);
        program.setFee(fee);
        program.setStartDates(startDates);
        programRepository.save(program);

        return "redirect:/programs?studentId=" + studentId;
    }
}
