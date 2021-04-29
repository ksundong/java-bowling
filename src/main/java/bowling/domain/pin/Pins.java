package bowling.domain.pin;

import bowling.domain.frame.FrameStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Pins {

    private final List<Pin> pins;

    public Pins() {
        this(new ArrayList<>());
    }

    public Pins(Pin... pins) {
        this(Arrays.asList(pins));
    }

    public Pins(List<Pin> pins) {
        this.pins = pins;
    }

    public Pin firstPin() {
        return null;
    }

    public Pin secondPin() {
        return null;
    }

    public Pin thirdPin() {
        return null;
    }

    public FrameStatus frameStatus() {
        return null;
    }

    public Pins knockDownPin(final Pin pin) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pins pins1 = (Pins) o;
        return Objects.equals(pins, pins1.pins);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pins);
    }
}
