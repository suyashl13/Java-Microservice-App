package com.hiresuyash.quiz_app.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("question")
public class QuestionController {
    @GetMapping
    public String getAllQuestions() { return  "Test Route"; }

    @PostMapping
    public void addQuestion() {}

    @PutMapping(":id")
    public void updateQuestion() {}

    @DeleteMapping(":id")
    public void deleteQuestion() {}
}
