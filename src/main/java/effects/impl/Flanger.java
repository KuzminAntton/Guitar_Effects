package effects.impl;

import effects.Effect;

public class Flanger extends Effect {
    private double phi;
    private double omega;
    private long sampleFreq;

    public Flanger(long sf) {
        phi = (0.1 * 2 * Math.PI)/sampleFreq;
        omega = 0;

        sampleFreq = sf;
    }

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;
        int N = (int) (0.002 * sampleFreq);
        int d = (int) (N * (1 + Math.cos(omega)));
        //int N = (int) (0.3 * sampleFreq);
        omega = omega + phi;
        return (0.5 + (sig[i] + sig[(i + bufLen - d) & mask]));
    }
}