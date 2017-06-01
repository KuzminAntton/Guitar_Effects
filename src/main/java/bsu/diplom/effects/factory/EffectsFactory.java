package bsu.diplom.effects.factory;

import bsu.diplom.effects.impl.*;

public class EffectsFactory {
    private static EffectsFactory instance = new EffectsFactory();

    protected EffectsFactory() {

    }

    private final Distortion distortion = new Distortion();
    private final Echo echo = new Echo();
    private final NaturalEcho naturalEcho = new NaturalEcho();
    private final Reverb reverb = new Reverb();
    private final Flanger flanger = new Flanger();
    private final Tremolo tremolo = new Tremolo();
    private final DistortionAndEcho distortionAndEcho = new DistortionAndEcho();
    private final Chorus chorus = new Chorus();
    private final DistortionAndFlanger distortionAndFlanger = new DistortionAndFlanger();

    public static EffectsFactory getInstance() {
        if(instance == null) {
            instance = new EffectsFactory();
        }
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

    public DistortionAndEcho getDistortionAndEcho() {
        return distortionAndEcho;
    }

    public Chorus getChorus() {
        return chorus;
    }

    public DistortionAndFlanger getDistortionAndFlanger() {
        return distortionAndFlanger;
    }
}
