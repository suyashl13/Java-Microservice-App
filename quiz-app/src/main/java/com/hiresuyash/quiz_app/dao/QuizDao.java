package com.hiresuyash.quiz_app.dao;

import com.hiresuyash.quiz_app.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> { }
