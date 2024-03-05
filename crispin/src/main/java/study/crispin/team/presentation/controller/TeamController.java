package study.crispin.team.presentation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.crispin.team.application.request.TeamRegistrationRequest;
import study.crispin.team.application.service.TeamService;
import study.crispin.team.presentation.response.TeamRegistrationResponse;
import study.crispin.team.presentation.response.TeamRetrieveResponses;

@RestController
@RequestMapping("/api/v1")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/team")
    public ResponseEntity<TeamRetrieveResponses> retrieve() {
        return ResponseEntity.ok(teamService.retrieve());
    }

    @PostMapping("/team")
    public ResponseEntity<TeamRegistrationResponse> registration(
            @RequestBody @Valid TeamRegistrationRequest request
    ) {
        return ResponseEntity.ok(teamService.registration(request));
    }
}
