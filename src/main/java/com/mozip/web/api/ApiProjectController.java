package com.mozip.web.api;

import com.mozip.domain.keep.Keep;
import com.mozip.domain.likes.Likes;
import com.mozip.dto.CMRespDto;
import com.mozip.dto.req.ProjectCreateDto;
import com.mozip.service.KeepService;
import com.mozip.dto.resp.ProjectEditDto;
import com.mozip.dto.resp.RecruitListDto;
import com.mozip.dto.resp.ShowEditDto;
import com.mozip.service.LikesService;
import com.mozip.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ApiProjectController {
    private final ProjectService projectService;
    private final LikesService likesService;
    private final KeepService keepService;

    // 프로젝트 모집 완료
    @PatchMapping("/recruit/done")
    public ResponseEntity<?> recruitDone(@RequestParam("projectId") int projectId) {
        return ResponseEntity.ok().body(new CMRespDto<>(1, "통신성공!", projectService.recruitIsDone(projectId)));
    }

    // 프로젝트 생성
    @PostMapping("/project")
    public ResponseEntity<?> createProject(@RequestBody ProjectCreateDto dto) {
        int projectId = projectService.createProject(dto);

        return ResponseEntity.ok().body(new CMRespDto<>(1, "통신성공", projectId));
    }

    // 프로젝트 수정
    @PatchMapping("/project/update")
    public ResponseEntity<?> updateRecruitProject(@RequestBody ProjectEditDto dto) {
        projectService.updateRecruitProject(dto);

        return ResponseEntity.ok().body(new CMRespDto<>(1, "프로젝트 수정 성공!", dto.getId()));
    }

    // 좋아요
    @PostMapping("/like")
    public ResponseEntity<?> likeProject(@RequestBody Likes likes){
        // 좋아요, 좋아요 취소 구분하여 처리
        likesService.likeValidation(likes);

        return ResponseEntity.ok().body(new CMRespDto<>(1,"통신성공", likesService.likeCount(likes.getProjectId())));
    }

    // 프로젝트 삭제 메서드
    @DeleteMapping("/show/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") int projectId) {

        projectService.deleteProject(projectId); // 프로젝트 삭제 로직

        return ResponseEntity.ok().body(new CMRespDto<>(1,"통신성공",projectId));
    }

    // 프로젝트 자랑 수정
    @PatchMapping("/show/{projectId}")
    public ResponseEntity<?> editSelectShow(@RequestBody ShowEditDto dto, @PathVariable("projectId") int projectId) {
        projectService.updateShow(dto, projectId);
        return ResponseEntity.ok().body(new CMRespDto<>(1,"통신성공",dto.getId()));
    }

    // 프로젝트 참여 신청
    @PostMapping("/project/join")
    public ResponseEntity<?> projectJoin(@RequestParam("memberId") int memberId, @RequestParam("projectId") int projectId){
        return ResponseEntity.ok().body(new CMRespDto<>(1, "통신성공", projectService.projectJoin(memberId,projectId)));
    }

    // 멤버모집 리스트 검색
    @GetMapping("/project/search")
    @ResponseBody
    public ResponseEntity<?> searchProject(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok().body(new CMRespDto<>(1, "통신성공", projectService.searchProject(keyword)));
    }

    // 북마크
    @PostMapping("/keep")
    public ResponseEntity<?> keepProject(@RequestBody Keep keep){
        // 북마크, 북마크 취소 구분하여 처리
        keepService.keepValidation(keep);

        return ResponseEntity.ok().body(new CMRespDto<>(1,"통신성공", keepService.keepCount(keep.getProjectId(), keep.getMemberId())));
    }

    //projectFilterSearch
    @PostMapping("/project/filter")
    public ResponseEntity<?> filterProject(@RequestParam("filter") String filter){
        return ResponseEntity.ok().body(new CMRespDto<>(1, "통신성공", projectService.projectFilterSearch(filter)));
    }
}
