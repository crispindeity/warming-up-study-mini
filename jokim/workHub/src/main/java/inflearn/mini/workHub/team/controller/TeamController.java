package inflearn.mini.workHub.team.controller;

import inflearn.mini.workHub.team.dto.TeamCreateRequest;
import inflearn.mini.workHub.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/workhub/team/register")
    public void registerTeam(@RequestBody @Valid final TeamCreateRequest request) {
        teamService.registerTeam(request);
    }
}
