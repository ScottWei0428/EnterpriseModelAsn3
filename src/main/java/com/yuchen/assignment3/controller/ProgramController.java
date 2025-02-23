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
    public String showPrograms(Model model) {
        model.addAttribute("programList", programRepository.findAll());
        return "programs";
    }

    @GetMapping("/studentPrograms")
    public String showStudentPrograms(Model model) {
        model.addAttribute("programs", programRepository.findAll());
        return "studentPrograms";
    }

    @PostMapping("/addProgram")
    public String addProgram(
            @RequestParam("programCode") String programCode,
            @RequestParam("programName") String programName,
            @RequestParam("duration") String duration,
            @RequestParam("fee") double fee,
            @RequestParam("startDates") String startDates) {

        Program program = new Program();
        program.setProgramCode(programCode);
        program.setProgramName(programName);
        program.setDuration(duration);
        program.setFee(fee);
        program.setStartDates(startDates);

        programRepository.save(program);
        return "redirect:/programs";
    }

    @GetMapping("/deleteProgram")
    public String deleteProgram(@RequestParam("programCode") String programCode) {
        programRepository.deleteById(programCode);
        return "redirect:/programs";
    }

    @GetMapping("/editProgram")
    public String editProgram(@RequestParam("programCode") String programCode, Model model) {
        Program program = programRepository.findById(programCode).orElse(null);
        model.addAttribute("program", program);
        return "editProgram";
    }

    @PostMapping("/updateProgram")
    public String updateProgram(
            @RequestParam("programCode") String programCode,
            @RequestParam("programName") String programName,
            @RequestParam("duration") String duration,
            @RequestParam("fee") double fee,
            @RequestParam("startDates") String startDates) {

        Program program = programRepository.findById(programCode).orElse(null);
        if (program != null) {
            program.setProgramName(programName);
            program.setDuration(duration);
            program.setFee(fee);
            program.setStartDates(startDates);

            programRepository.save(program);
        }

        return "redirect:/programs";
    }
}
