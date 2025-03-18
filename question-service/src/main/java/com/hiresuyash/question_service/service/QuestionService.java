package com.hiresuyash.question_service.service;

import com.hiresuyash.question_service.dao.QuestionDao;
import com.hiresuyash.question_service.model.Question;
import com.hiresuyash.question_service.model.QuestionWrapper;
import com.hiresuyash.question_service.model.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getAllQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        return questionDao.save(question);
    }

    public  ResponseEntity<String> deleteQuestionById(Integer id) {
        Optional<Question> target = questionDao.findById(id);

        if (target.isPresent()){
            questionDao.delete(target.get());
            return new ResponseEntity<String>(
                    "Deleted Successfully!",
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<String>(
                "Question with id: " + id + " not found.",
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int noOfQuestions) {
        List<Integer> questionIds = questionDao.findRandomQuestionsByCategory(category,noOfQuestions);
        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }

    public ResponseEntity<?> calculateResult(List<QuizResponse> quizResponses) {

        int right = 0;

        for(QuizResponse quizResponse : quizResponses){
            Optional<Question> question = questionDao.findById(quizResponse.getId());
            if (question.isPresent() && question.get().getRightAnswer().equals(quizResponse.getResponse())) {
                right++;
            }
        }

        return new ResponseEntity<>("Your result is: " + right, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id : questionIds){
            questions.add(questionDao.findById(id).get());
        }

        for(Question question : questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }
}
