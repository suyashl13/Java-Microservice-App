package com.hiresuyash.quiz_app.service;

import com.hiresuyash.quiz_app.dao.QuestionDao;
import com.hiresuyash.quiz_app.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
