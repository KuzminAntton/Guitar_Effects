package effects.impl;


import effects.Effect;

public class Reverb extends Effect {
    private double a = 0.4;

    public Double effect(int i, Double[] sig1, int bufLen) {
        int mask = bufLen - 1;

        //double norm = 1.0 / (1 + a);
        int N = (int) (0.02 * sampleFreq);

        return ( -a * sig1[i] +
                sig1[(i + bufLen - N) & mask] +
                a * sig1[(i + bufLen - N) & mask]);
        //c * sig[(i + bufLen - 2 * N) & mask]);
    }
}