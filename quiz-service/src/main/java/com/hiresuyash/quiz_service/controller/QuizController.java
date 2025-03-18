package com.hiresuyash.quiz_service.controller;


import com.hiresuyash.quiz_service.dto.QuizDto;
import com.hiresuyash.quiz_service.model.QuizResponse;
import com.hiresuyash.quiz_service.service.QuizService;
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
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQ(), quizDto.getTitle());
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
