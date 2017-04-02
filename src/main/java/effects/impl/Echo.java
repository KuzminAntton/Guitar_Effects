package effects.impl;

import effects.Effect;

public class Echo extends Effect {
    private double a;
    private double b;
    private double c;
    private long sampleFreq;

    public Echo(long sf) {
        a = 1;
        b = 0.7;
        c = 0.5;
        sampleFreq = sf;
    }

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;
        double norm = 1.0 / (a + b + c);
        int N = (int) (0.3 * sampleFreq);

        return (norm * (	a *	sig[i] +
                b * sig[(i + bufLen - N) & mask] +
                c  * sig[(i + bufLen - 2 * N) & mask]));
    }
}