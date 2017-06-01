package bsu.diplom.effects.impl;

import bsu.diplom.effects.Effect;

public class DistortionAndEcho extends Effect{
    private final double LIMIT = 0.01;
    private final double G = 4;
    private String name = "Distortion And Echo";

    private final double A = 1.5;
    private final double B = 1.3;
    private final double C = 1.1;

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;

        double y = sig[i];
        if(y > LIMIT){y = LIMIT;}
        if(y < LIMIT){y = -LIMIT;}


        double norm = 1.0 / (A + B + C);
        int N = (int) (0.3 * sampleFreq);

        return (G * y) + (norm * (A *	sig[i] +
                B * sig[(i + bufLen - N) & mask] +
                C  * sig[(i + bufLen - 2 * N) & mask]));

    }

    public String getName() {
        return name;
    }
}
