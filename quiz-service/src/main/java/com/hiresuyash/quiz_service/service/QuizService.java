package com.hiresuyash.quiz_service.service;


import com.hiresuyash.quiz_service.dao.QuizDao;

import com.hiresuyash.quiz_service.feign.QuizInterface;
import com.hiresuyash.quiz_service.model.QuestionWrapper;
import com.hiresuyash.quiz_service.model.Quiz;
import com.hiresuyash.quiz_service.model.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questions);

        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz with id: " + 1 + " not found.", HttpStatus.OK);
    }

    public ResponseEntity<?> getQuestionsFromQuiz(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if (quiz.isEmpty()) return new ResponseEntity<>("Quiz with id: " + id + " not found.", HttpStatus.OK);

        List<Integer> questionIdList = quiz.get().getQuestionsIds();
        List<QuestionWrapper> questionWrappers = quizInterface.getQuestionsFromId(questionIdList).getBody();
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<?> calculateResult(Integer id, List<QuizResponse> responses) {
        ResponseEntity<Integer> score = (ResponseEntity<Integer>) quizInterface.getScore(responses).getBody();
        return score;
    }
}
