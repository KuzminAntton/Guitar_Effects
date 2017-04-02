package effects.impl;


import effects.Effect;

public class NaturalEcho extends Effect {
    private final double A = 0.7;
    private final double L = 0.6;

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;

        double norm = 1.0 / (1 + A);
        int N = (int) (0.3 * sampleFreq);

        return (norm * (		sig[i] -
                L * sig[(i + bufLen - 1) & mask] +
                L * sig[(i + bufLen - 1) & mask]+
                A *(1- L) * sig[(i + bufLen - N) & mask]));
    }
}