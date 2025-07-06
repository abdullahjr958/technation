package com.technation.technation.repository;

import com.technation.technation.model.SpecsImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecsImgRepository extends JpaRepository<SpecsImg, Integer> {
    List<SpecsImg> findAllByProductId(int id);
}
