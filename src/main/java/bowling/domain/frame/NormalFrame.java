package bowling.domain.frame;

import bowling.exception.IllegalNormalFrameException;

public final class NormalFrame extends Frame {

    private NormalFrame(RoundNumber roundNumber, FrameScore frameScore) {
        super(roundNumber, frameScore);
    }

    public static Frame of(RoundNumber roundNumber, FrameScore frameScore) {
        validateNormalRoundNumber(roundNumber);
        return new NormalFrame(roundNumber, frameScore);
    }

    private static void validateNormalRoundNumber(RoundNumber roundNumber) {
        if (roundNumber.value() == RoundNumber.MAX) {
            throw new IllegalNormalFrameException();
        }
    }

    public static Frame createFirstFrame() {
        return NormalFrame.of(RoundNumber.firstRoundNumber(), new FrameScore());
    }

    @Override
    public Frame createNextFrame() {
        return NormalFrame.of(roundNumber().nextRoundNumber(), new FrameScore());
    }

    @Override
    public void knockDownPin(Pin pin) {

    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
