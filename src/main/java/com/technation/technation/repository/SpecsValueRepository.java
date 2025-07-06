package com.technation.technation.repository;

import com.technation.technation.model.SpecsValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecsValueRepository extends JpaRepository<SpecsValue, Integer> {

    List<SpecsValue> findAllByProductId(int id);
}
