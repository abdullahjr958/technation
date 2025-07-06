package com.technation.technation.repository;

import com.technation.technation.model.SpecsLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecsLabelRepository extends JpaRepository<SpecsLabel, Integer> {
}
