package com.prem.myfirstapp.about.repository;

import com.prem.myfirstapp.about.model.AboutmeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AboutmeRepo extends JpaRepository<AboutmeModel, Long> {

    List<AboutmeModel> findByIsActiveTrueOrderByDisplayOrderAsc();

    List<AboutmeModel> findByProfileIdAndIsActiveTrueOrderByDisplayOrderAsc(Long profileId);
}