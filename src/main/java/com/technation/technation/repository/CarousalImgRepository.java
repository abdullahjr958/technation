package com.technation.technation.repository;

import com.technation.technation.model.CarousalImg;
import com.technation.technation.model.SpecsImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarousalImgRepository extends JpaRepository<CarousalImg, Integer> {
    List<CarousalImg> findAllByProductId(int id);
}
