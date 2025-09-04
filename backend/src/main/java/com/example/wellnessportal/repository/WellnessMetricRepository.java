package com.example.wellnessportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wellnessportal.model.WellnessMetric;

public interface WellnessMetricRepository extends JpaRepository<WellnessMetric, Long> {

}
