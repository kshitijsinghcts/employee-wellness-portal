package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
