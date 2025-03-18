package com.hiresuyash.quiz_service.feign;

import com.hiresuyash.quiz_service.model.QuestionWrapper;
import com.hiresuyash.quiz_service.model.QuizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numQuestions);

    // getQuestions
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    // GetScore
    @PostMapping
    @RequestMapping("question/score")
    public ResponseEntity<?> getScore(@RequestBody List<QuizResponse> quizResponses);
}
