package com.prem.myfirstapp.about.mapper;

import com.prem.myfirstapp.about.dto.AboutmeRequest;
import com.prem.myfirstapp.about.dto.AboutmeResponse;
import com.prem.myfirstapp.about.model.AboutmeModel;

public final class AboutmeMapper {
    private AboutmeMapper() {}

    public static AboutmeResponse toResponse(AboutmeModel a) {
        return new AboutmeResponse( a.getProfileId(), a.getSectionTitle(),
                a.getDescription(), a.getDisplayOrder(), a.getIsActive());
    }

    public static AboutmeModel toEntity(AboutmeRequest r) {
        AboutmeModel a = new AboutmeModel();
        a.setProfileId(r.profileId());
        a.setSectionTitle(r.sectionTitle());
        a.setDescription(r.description());
        a.setDisplayOrder(r.displayOrder());
        a.setIsActive(r.isActive() != null ? r.isActive() : true);
        return a;
    }
}