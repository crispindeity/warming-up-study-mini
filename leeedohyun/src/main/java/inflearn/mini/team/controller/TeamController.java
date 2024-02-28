package inflearn.mini.team.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import inflearn.mini.team.service.TeamService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/register")
    public void registerTeam(@RequestBody TeamRegisterRequestDto request) {
        teamService.registerTeam(request);
    }
}
