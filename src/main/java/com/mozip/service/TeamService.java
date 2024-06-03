package com.mozip.service;

import com.mozip.domain.project.ProjectRepository;
import com.mozip.domain.teamnote.TeamnoteRepository;
import com.mozip.dto.resp.TeamnoteListDto;
import com.mozip.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.NClob;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamnoteRepository teamnoteRepository;

    public List<TeamnoteListDto> findTeamNoteList(int memberId) {
        List<TeamnoteListDto> teamNoteList = teamnoteRepository.findTeamNoteList(memberId);
        for (TeamnoteListDto teamnoteListDto : teamNoteList) {
            teamnoteListDto.setProjectInfo(Util.clobToString((NClob) teamnoteListDto.getProjectInfo()));
        }
        return teamNoteList;
    }
}
