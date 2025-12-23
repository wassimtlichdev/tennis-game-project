import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tennisgame.domain.model.*;
import org.tennisgame.domain.port.out.ScoreOutputPort;
import org.tennisgame.domain.application.PlayTennisGameService;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class TennisGameTest {

    @Mock
    private ScoreOutputPort scorePresenter;

    @InjectMocks
    private PlayTennisGameService gameService;

    @Captor
    private ArgumentCaptor<Score> scoreCaptor;


    @ParameterizedTest
    @CsvSource({
            "A, 15-0",
            "AA, 30-0",
            "AAA, 40-0",
            "BB, 0-30",
            "ABAB, 30-30",
            "AAAB, 40-15"
    })
    void test_score_progression_parameterized(String sequence, String expectedScoreStr) {
        // When
        gameService.play(sequence);

        // Then
        verify(scorePresenter, times(sequence.length())).display(scoreCaptor.capture());
        Score lastScore = scoreCaptor.getAllValues().getLast();

        Score expectedScore = parseExpectedScore(expectedScoreStr);
        assertThat(lastScore).isEqualTo(expectedScore);
    }

    @Test
    void test_full_scenario_from_kata() {
        // When
        gameService.play("ABABAA");

        // Then
        verify(scorePresenter, times(6)).display(scoreCaptor.capture());
        List<Score> history = scoreCaptor.getAllValues();

        assertThat(history).hasSize(6);
        assertThat(history.get(4)).isEqualTo(new PointScore(40, 30));
        assertThat(history.getLast()).isEqualTo(new GameWon('A'));
    }

    @Test
    void test_deuce_and_advantage_back_and_forth() {
        // When
        gameService.play("ABABABABBB");

        // Then
        verify(scorePresenter, times(10)).display(scoreCaptor.capture());
        List<Score> history = scoreCaptor.getAllValues();

        assertThat(history).contains(new Deuce());
        assertThat(history.getLast()).isEqualTo(new GameWon('B'));
    }

    @Test
    void test_love_game_victory() {
        // When
        gameService.play("AAAA");

        // Then
        verify(scorePresenter, times(4)).display(scoreCaptor.capture());
        List<Score> history = scoreCaptor.getAllValues();

        assertThat(history.getLast()).isEqualTo(new GameWon('A'));
        assertThat(history).containsExactly(
                new PointScore(15, 0),
                new PointScore(30, 0),
                new PointScore(40, 0),
                new GameWon('A')
        );
    }

    @Test
    void test_simple_game_victory() {
        // When
        gameService.play("AABABA");

        // Then
        verify(scorePresenter, times(6)).display(scoreCaptor.capture());
        List<Score> history = scoreCaptor.getAllValues();

        assertThat(history.getLast()).isEqualTo(new GameWon('A'));
        assertThat(history).contains(new PointScore(40, 30));
        assertThat(history).doesNotContain(new Deuce());
    }

    @Test
    void test_invalid_characters() {
        assertThrows(IllegalArgumentException.class, () -> {
            gameService.play("A X A");
        });
    }

    private Score parseExpectedScore(String scoreStr) {
        String[] parts = scoreStr.split("-");
        int scoreA = Integer.parseInt(parts[0]);
        int scoreB = Integer.parseInt(parts[1]);
        return new PointScore(scoreA, scoreB);
    }
}