package com.hiresuyash.question_service.controller;


import com.hiresuyash.question_service.model.Question;
import com.hiresuyash.question_service.model.QuestionWrapper;
import com.hiresuyash.question_service.model.QuizResponse;
import com.hiresuyash.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;


    @GetMapping
    @RequestMapping("category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getAllQuestionsByCategory(category);
    }

    @PostMapping
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("{id}")
    public void updateQuestion() {}

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestionById(id);
    }

    // Generate
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numQuestions) {
        return questionService.getQuestionsForQuiz(category, numQuestions);
    }

    // getQuestions
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) { return questionService.getQuestionsFromId(questionIds); }

    // GetScore
    @PostMapping
    @RequestMapping("score")
    public ResponseEntity<?> getScore(@RequestBody List<QuizResponse> quizResponses) {
        return questionService.calculateResult(quizResponses);
    }
}
