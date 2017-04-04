package effects.factory;

import effects.impl.*;

public class EffectsFactory {
    private static final EffectsFactory instance = new EffectsFactory();

    private final Distortion distortion = new Distortion();
    private final Echo echo = new Echo();
    private final NaturalEcho naturalEcho = new NaturalEcho();
    private final Reverb reverb = new Reverb();
    private final Flanger flanger = new Flanger();
    private final Tremolo tremolo = new Tremolo();

    public static EffectsFactory getInstance() {
        return instance;
    }

    public Distortion getDistortion() {
        return distortion;
    }

    public Echo getEcho() {
        return echo;
    }

    public NaturalEcho getNaturalEcho() {
        return naturalEcho;
    }

    public Reverb getReverb() {
        return reverb;
    }

    public Flanger getFlanger() {
        return flanger;
    }

    public Tremolo getTremolo() {
        return tremolo;
    }
}
