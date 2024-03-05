package study.crispin.team.application.service;

import study.crispin.team.application.request.TeamRegistrationRequest;
import study.crispin.team.presentation.response.TeamRegistrationResponse;
import study.crispin.team.presentation.response.TeamRetrieveResponses;

public interface TeamService {
    TeamRegistrationResponse registration(TeamRegistrationRequest request);

    TeamRetrieveResponses retrieve();
}
