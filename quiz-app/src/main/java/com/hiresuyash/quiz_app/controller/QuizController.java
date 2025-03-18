package com.hiresuyash.quiz_app.controller;


import com.hiresuyash.quiz_app.model.QuizResponse;
import com.hiresuyash.quiz_app.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping()
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<?> getQuizQuestions(@PathVariable int id) {
        return quizService.getQuestionsFromQuiz(id);
    }

    @PostMapping
    @RequestMapping("submit/{id}")
    public ResponseEntity<?> submitResponse(@PathVariable Integer id, @RequestBody List<QuizResponse> quizResponses ) {
        return quizService.calculateResult(id, quizResponses);
    }

}
