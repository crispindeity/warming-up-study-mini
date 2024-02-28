package study.crispin.team.presentation.port;

import study.crispin.team.application.request.TeamRegistrationRequest;
import study.crispin.team.presentation.response.TeamRegistrationResponse;

public interface TeamService {
    TeamRegistrationResponse registration(TeamRegistrationRequest request);
}
