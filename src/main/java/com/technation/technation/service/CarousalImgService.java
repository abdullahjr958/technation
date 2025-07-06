package com.technation.technation.service;

import com.technation.technation.dto.CarousalImgDTO;
import com.technation.technation.repository.CarousalImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarousalImgService {

    private CarousalImgRepository carousalImgRepository;

    @Autowired
    CarousalImgService(CarousalImgRepository carousalImgRepository){
        this.carousalImgRepository = carousalImgRepository;
    }

    public List<CarousalImgDTO> getCarousalImgByProductId(int id){
        return carousalImgRepository.findAllByProductId(id)
                .stream()
                .map(CarousalImgDTO::new)
                .collect(Collectors.toList());
    }
}
