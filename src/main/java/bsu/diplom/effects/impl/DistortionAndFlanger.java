package bsu.diplom.effects.impl;

import bsu.diplom.effects.Effect;

public class DistortionAndFlanger extends Effect {
    private final double LIMIT = 0.01;
    private final double G = 3;
    private double OMEGA = 0;
    private String name = "Distortion And Flanger";

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;

        double y = sig[i];
        if(y > LIMIT){y = LIMIT;}
        if(y < LIMIT){y = -LIMIT;}

        int N = (int) (0.011 * sampleFreq);
        int d = (int) (N * (1 + Math.cos(OMEGA)));
        OMEGA = OMEGA + clcPhi();
        return (G * y) + (0.5 + (sig[i] + sig[(i + bufLen - d) & mask]));

    }

    public String getName() {
        return name;
    }

    private double clcPhi() {
        return  (0.1 * 2 * Math.PI)/sampleFreq;
    }
}
