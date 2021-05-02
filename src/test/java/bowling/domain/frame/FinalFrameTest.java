package bowling.domain.frame;

import bowling.domain.pin.Pin;
import bowling.domain.pin.Pins;
import bowling.exception.NoNextFrameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static bowling.domain.TestFixture.GUTTER_PIN;
import static bowling.domain.TestFixture.STRIKE_PIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class FinalFrameTest {

    static Stream<Arguments> endedFinalFrameSource() {
        return Stream.of(
                arguments(FinalFrame.from(Pins.create()), false),
                arguments(FinalFrame.from(Pins.of(STRIKE_PIN)), false),
                arguments(FinalFrame.from(Pins.of(GUTTER_PIN)), false),
                arguments(FinalFrame.from(Pins.of(new Pin(5))), false),
                arguments(FinalFrame.from(Pins.of(new Pin(5), new Pin(5))), false),
                arguments(FinalFrame.from(Pins.of(new Pin(5), GUTTER_PIN)), true),
                arguments(FinalFrame.from(Pins.of(GUTTER_PIN, GUTTER_PIN)), true),
                arguments(FinalFrame.from(Pins.of(STRIKE_PIN, STRIKE_PIN)), false),
                arguments(FinalFrame.from(Pins.of(new Pin(5), new Pin(5), GUTTER_PIN)), true),
                arguments(FinalFrame.from(Pins.of(STRIKE_PIN, STRIKE_PIN, STRIKE_PIN)), true)
        );
    }

    @Test
    @DisplayName("Pins를 받아 마지막 프레임을 생성한다.")
    void create() {
        // given
        final Pins pins = Pins.create();

        // when
        final FinalFrame finalFrame = FinalFrame.from(pins);

        // then
        assertThat(finalFrame).isEqualTo(FinalFrame.from(Pins.create()));
    }

    @Test
    @DisplayName("마지막 프레임의 다음 프레임을 요청할 경우 예외가 발생한다.")
    void nextFrame() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.create());

        // when
        // then
        assertThatThrownBy(() -> finalFrame.nextFrame())
                .isInstanceOf(NoNextFrameException.class)
                .hasMessage(NoNextFrameException.FINAL_FRAME_CANNOT_CREATE_NEXT_FRAME);
    }

    @Test
    @DisplayName("FinalFrame의 다음 프레임은 생성되지 않는다.")
    void createNextFrame() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.create());

        // when
        // then
        assertThatThrownBy(() -> finalFrame.createNextFrame())
                .isInstanceOf(NoNextFrameException.class)
                .hasMessage(NoNextFrameException.FINAL_FRAME_CANNOT_CREATE_NEXT_FRAME);
    }

    @Test
    @DisplayName("투구를 한다. 투구를 한 후 상태가 변경된다.")
    void knockDownPin() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.create());
        final Pin pin = new Pin(0);

        // when
        finalFrame.knockDownPin(pin);
        finalFrame.knockDownPin(pin);

        // then
        assertThat(finalFrame.isEnded()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("endedFinalFrameSource")
    @DisplayName("마지막 프레임의 상태별로 종료되었는지 여부를 반환한다.")
    void isEnded(FinalFrame finalFrame, boolean expected) {
        // given
        // when
        final boolean ended = finalFrame.isEnded();

        // then
        assertThat(ended).isEqualTo(expected);
    }

    @Test
    @DisplayName("처음 만들어진 FinalFrame은 점수를 산정할 수 없다.")
    void noScore() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.create());

        // when
        final Integer score = finalFrame.score();

        // then
        assertThat(score).isNull();
    }

    @Test
    @DisplayName("한 번 투구한 FinalFrame은 점수를 산정할 수 없다.")
    void noScoreOneThrow() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.of(new Pin(5)));

        // when
        final Integer score = finalFrame.score();

        // then
        assertThat(score).isNull();
    }

    @Test
    @DisplayName("첫 투구가 스트라이크인 FinalFrame은 두번째 투구를 던져도 점수를 산정할 수 없다.")
    void noScoreFirstThrowStrike() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.of(STRIKE_PIN, new Pin(5)));

        // when
        final Integer score = finalFrame.score();

        // then
        assertThat(score).isNull();
    }

    @Test
    @DisplayName("스페어로 시작하는 FinalFrame은 두번째 투구를 던져도 점수를 산정할 수 없다.")
    void noScoreSpare() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.of(new Pin(2), new Pin(8)));

        // when
        final Integer score = finalFrame.score();

        // then
        assertThat(score).isNull();
    }

    // TODO: Parameterized Test로 개선
    @Test
    @DisplayName("일반적인 투구 2회를 던진 FinalFrame은 점수를 산정할 수 있다.")
    void twoThrowScore() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.of(new Pin(2), new Pin(3)));

        // when
        final Integer score = finalFrame.score();

        // then
        assertThat(score).isEqualTo(5);
    }

    // TODO: Parameterized Test로 개선
    @Test
    @DisplayName("투구 3회를 던지면 FinalFrame은 점수를 산정할 수 있다.")
    void threeThrowScore() {
        // given
        final FinalFrame finalFrame = FinalFrame.from(Pins.of(STRIKE_PIN, STRIKE_PIN, STRIKE_PIN));

        // when
        final Integer score = finalFrame.score();

        // then
        assertThat(score).isEqualTo(30);
    }
}
