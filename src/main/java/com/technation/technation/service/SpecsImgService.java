package com.technation.technation.service;

import com.technation.technation.dto.SpecsImgDTO;
import com.technation.technation.repository.SpecsImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecsImgService {

    private SpecsImgRepository specsImgRepository;

    @Autowired
    SpecsImgService(SpecsImgRepository specsImgRepository){
        this.specsImgRepository = specsImgRepository;
    }

    public List<SpecsImgDTO> getSpecsImgByProductId(int id){
        return specsImgRepository.findAllByProductId(id)
                .stream()
                .map(SpecsImgDTO::new)
                .collect(Collectors.toList());
    }

}
