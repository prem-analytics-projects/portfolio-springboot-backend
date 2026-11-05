package com.prem.myfirstapp.about.service;

import com.prem.myfirstapp.about.dto.AboutmeRequest;
import com.prem.myfirstapp.about.dto.AboutmeResponse;
import com.prem.myfirstapp.about.mapper.AboutmeMapper;
import com.prem.myfirstapp.about.model.AboutmeModel;
import com.prem.myfirstapp.about.repository.AboutmeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AboutmeService {

    private final AboutmeRepo repository;

    public AboutmeService(AboutmeRepo repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AboutmeResponse> getActive(Long profileId) {
        List<AboutmeModel> rows = (profileId != null)
                ? repository.findByProfileIdAndIsActiveTrueOrderByDisplayOrderAsc(profileId)
                : repository.findByIsActiveTrueOrderByDisplayOrderAsc();
        return rows.stream().map(AboutmeMapper::toResponse).toList();
    }

    @Transactional
    public AboutmeResponse create(AboutmeRequest request) {
        return AboutmeMapper.toResponse(repository.save(AboutmeMapper.toEntity(request)));
    }
}