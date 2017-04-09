package effects.impl;


import effects.Effect;

public class Tremolo extends Effect {
    private double OMEGA = 0;

    private String name = "Tremolo";

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;

        OMEGA = OMEGA + clcPhi();
        return (1 + (Math.cos(OMEGA)/4)) * sig[i];
    }

    private double clcPhi() {
        return (27 * 2 * Math.PI)/sampleFreq;
    }

    public String getName() {
        return name;
    }
}