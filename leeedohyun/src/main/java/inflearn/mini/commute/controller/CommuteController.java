package inflearn.mini.commute.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inflearn.mini.commute.dto.request.CommutingRequestDto;
import inflearn.mini.commute.dto.request.EndOfWorkRequestDto;
import inflearn.mini.commute.service.CommuteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommuteController {

    private final CommuteService commuteService;

    @PostMapping("/work")
    public void goToWork(@RequestBody final CommutingRequestDto commutingRequestDto) {
        commuteService.goToWork(commutingRequestDto);
    }

    @PatchMapping("/leave")
    public void leaveWork(@RequestBody final EndOfWorkRequestDto endOfWorkRequest) {
        commuteService.leaveWork(endOfWorkRequest);
    }
}
