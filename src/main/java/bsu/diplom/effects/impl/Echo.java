package bsu.diplom.effects.impl;

import bsu.diplom.effects.Effect;

public class Echo extends Effect {
    private final double A = 1;
    private final double B = 0.7;
    private final double C = 0.5;

    private String name = "Echo";

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;
        double norm = 1.0 / (A + B + C);
        int N = (int) (0.3 * sampleFreq);

        return (norm * (A *	sig[i] +
                B * sig[(i + bufLen - N) & mask] +
                C  * sig[(i + bufLen - 2 * N) & mask]));
    }
    public String getName() {
        return name;
    }
}