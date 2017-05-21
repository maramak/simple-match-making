package com.mk.service;


import com.mk.entity.Player;
import com.mk.entity.Team;
import com.mk.service.match.MatchMakingService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchMakingServiceTest {

    private MatchMakingService service;

    @Before
    public void before() {
        service = new MatchMakingService();
    }

    @Test
    public void shouldCreateCorrectTeamsForPlayers() throws IOException, URISyntaxException {
        List<Player> players = playersFromFile("matchmaking.txt");
        assertThat(players).hasSize(27);

        List<Team> teams = service.matchTeams(players);
        assertThat(teams).hasSize(2);

        Team noobTeam = teams.get(0);
        assertThat(noobTeam.getPlayers()).hasSize(8);
        assertThat(hasPlayer(noobTeam, "Chubaka")).isTrue();
        assertThat(hasPlayer(noobTeam, "Nagibator228")).isTrue();
        assertThat(hasPlayer(noobTeam, "Yolo_1999")).isTrue();
        assertThat(hasPlayer(noobTeam, "Mamku_v_kino_vodil")).isTrue();
        assertThat(hasPlayer(noobTeam, "NoobestNoob")).isTrue();
        assertThat(hasPlayer(noobTeam, "Tankist")).isTrue();
        assertThat(hasPlayer(noobTeam, "Nikakoy")).isTrue();
        assertThat(hasPlayer(noobTeam, "Vovan")).isTrue();

        Team highSkillTeam = teams.get(1);
        assertThat(highSkillTeam.getPlayers()).hasSize(8);
        assertThat(hasPlayer(highSkillTeam, "John")).isTrue();
        assertThat(hasPlayer(highSkillTeam, "Pete")).isTrue();
        assertThat(hasPlayer(highSkillTeam, "Mara")).isTrue();
        assertThat(hasPlayer(highSkillTeam, "Chupa")).isTrue();
        assertThat(hasPlayer(highSkillTeam, "h8uall")).isTrue();
        assertThat(hasPlayer(highSkillTeam, "WtfAmIDoingHere")).isTrue();
        assertThat(hasPlayer(highSkillTeam, "Dendi")).isTrue();
        assertThat(hasPlayer(highSkillTeam, "Serega")).isTrue();
    }

    private boolean hasPlayer(Team team, String playerName) {
        return team.getPlayers().stream().anyMatch(p -> p.getUser().equals(playerName));
    }

    private List<Player> playersFromFile(String path) throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource(path);
        assertThat(resource).isNotNull();

        Stream<String> lines = Files.lines(Paths.get(resource.toURI()));
        List<Player> players = new ArrayList<>();
        lines.forEach(l -> players.add(fromString(l)));

        return players;
    }

    private Player fromString(String line) {
        String[] split = line.split("\\W+");
        Player player = new Player(split[0], Byte.valueOf(split[1]));
        player.setWaitingTime(Long.valueOf(split[2]));

        return player;
    }
}
