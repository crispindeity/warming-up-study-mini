package inflearn.mini.team.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import inflearn.mini.team.dto.response.TeamResponseDto;
import inflearn.mini.team.service.TeamService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/register")
    public void registerTeam(@RequestBody @Valid TeamRegisterRequestDto request) {
        teamService.registerTeam(request);
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDto>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }
}
