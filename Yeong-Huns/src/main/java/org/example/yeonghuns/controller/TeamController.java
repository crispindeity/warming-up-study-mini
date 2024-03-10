package org.example.yeonghuns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.yeonghuns.dto.team.request.CreateTeamRequest;
import org.example.yeonghuns.dto.team.request.UpdateDayBeforeAnnualRequest;
import org.example.yeonghuns.dto.team.response.GetAllTeamsResponse;
import org.example.yeonghuns.service.team.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/team")
    public ResponseEntity<Void> createTeam(@RequestBody CreateTeamRequest request) {
        teamService.createTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/team")
    public ResponseEntity<List<GetAllTeamsResponse>> getAllTeams() {
        List<GetAllTeamsResponse> allTeamsList = teamService.getAllTeams();
        return ResponseEntity.ok().body(allTeamsList);
    }

    @PutMapping("/team/day-before-annual")
    public void updateDayBeforeAnnual(@RequestBody @Valid UpdateDayBeforeAnnualRequest request){
        teamService.updateDayBeforeAnnual(request);
    }
}
