package bowling.domain;

import bowling.domain.pin.Pin;

public final class TestFixture {

    public static final Pin STRIKE_PIN = new Pin(10);
    public static final Pin GUTTER_PIN = new Pin(0);

    private TestFixture() {}
}
