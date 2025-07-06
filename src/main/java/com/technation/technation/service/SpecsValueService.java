package com.technation.technation.service;

import com.technation.technation.dto.SpecsValueDTO;
import com.technation.technation.repository.SpecsValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecsValueService {

    private SpecsValueRepository specsValueRepo;

    @Autowired
    SpecsValueService(SpecsValueRepository specsValueRepo){
        this.specsValueRepo = specsValueRepo;
    }

    public List<SpecsValueDTO> getSpecsValueByProductId(int id){
        return specsValueRepo.findAllByProductId(id)
                .stream()
                .map(SpecsValueDTO::new)
                .collect(Collectors.toList());
    }
}
