package com.hiresuyash.quiz_app.service;

import com.hiresuyash.quiz_app.dao.QuestionDao;
import com.hiresuyash.quiz_app.dao.QuizDao;
import com.hiresuyash.quiz_app.model.Question;
import com.hiresuyash.quiz_app.model.QuestionWrapper;
import com.hiresuyash.quiz_app.model.Quiz;
import com.hiresuyash.quiz_app.model.QuizResponse;
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
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        System.out.println(questions.size());

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Successfully created quiz", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getQuestionsFromQuiz(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);

        if (quiz.isPresent()) {
            List<QuestionWrapper> questionWrappers = quiz.get().getQuestions().parallelStream().map(
                    question -> new QuestionWrapper(
                            question.getId(),
                            question.getQuestionTitle(),
                            question.getOption1(),
                            question.getOption2(),
                            question.getOption3(),
                            question.getOption4()
                    )
            ).toList();

            return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
        }
        return new ResponseEntity<>("Quiz with id: " + id + " not found.", HttpStatus.OK);
    }

    public ResponseEntity<?> calculateResult(int quizId, List<QuizResponse> quizResponses) {
        Optional<Quiz> quiz = quizDao.findById(quizId);

        if (quiz.isEmpty()) {
            return new ResponseEntity<>("Quiz with id: "+ quizId + " not found", HttpStatus.BAD_REQUEST);
        }

        List<Question> quizQuestions = quiz.get().getQuestions();
        int right = 0;
        int iterator = 0;

        for(Question question : quizQuestions){
            QuizResponse quizResponse = quizResponses.get(iterator);
            if (quizResponse.getResponse().equals(question.getRightAnswer())) {
                right++;
            }
            iterator++;
        }

        return new ResponseEntity<>("Your result is: " + right, HttpStatus.OK);
    }
}
