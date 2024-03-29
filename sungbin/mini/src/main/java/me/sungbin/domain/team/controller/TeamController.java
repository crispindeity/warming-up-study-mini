package me.sungbin.domain.team.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sungbin.domain.team.model.request.RegistrationTeamRequestDto;
import me.sungbin.domain.team.model.response.TeamInfoResponseDto;
import me.sungbin.domain.team.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.team.controller
 * @fileName : TeamController
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/register")
    public void registerTeam(@RequestBody @Valid RegistrationTeamRequestDto requestDto) {
        this.teamService.registerTeam(requestDto);
    }

    @GetMapping
    public List<TeamInfoResponseDto> findTeamInfo() {
        return this.teamService.findTeamInfo();
    }
}
