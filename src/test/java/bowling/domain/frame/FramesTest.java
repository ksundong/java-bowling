package bowling.domain.frame;

import bowling.domain.TestFixture;
import bowling.domain.pin.Pins;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FramesTest {

    @Test
    @DisplayName("Frames를 생성한다.")
    void create() {
        // given
        final Frames frames = Frames.initialize();
        final List<Frame> frameList = frames.value();

        // when
        // then
        assertAll(
                () -> assertThat(frames).isEqualTo(Frames.initialize()),
                () -> assertThat(frameList.get(0)).isEqualTo(NormalFrame.createFirstFrame()),
                () -> assertThat(frameList.get(frameList.size() - 1)).isEqualTo(FinalFrame.from(Pins.create()))
        );
    }

    @Test
    @DisplayName("지정된 Frame이 종료되었는지 확인한다.")
    void isEnded() {
        // given
        final Frames frames = Frames.initialize();

        // when
        final boolean ended = frames.isEnded(new RoundNumber(1));

        // then
        assertThat(ended).isFalse();
    }

    @Test
    @DisplayName("지정된 Frame의 Pin을 쓰러뜨린다.")
    void knockDownPin() {
        // given
        final Frames frames = Frames.initialize();
        final RoundNumber roundNumber = new RoundNumber(1);

        // when
        frames.knockDownPin(roundNumber, TestFixture.STRIKE_PIN);

        // then
        assertThat(frames.isEnded(roundNumber)).isTrue();
    }
}
