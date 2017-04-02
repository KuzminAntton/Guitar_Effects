package effects.impl;

import effects.Effect;

public class Flanger extends Effect {
    private double OMEGA = 0;

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;
        int N = (int) (0.002 * sampleFreq);
        int d = (int) (N * (1 + Math.cos(OMEGA)));
        //int N = (int) (0.3 * sampleFreq);
        OMEGA = OMEGA + clcPhi();
        return (0.5 + (sig[i] + sig[(i + bufLen - d) & mask]));
    }

    private double clcPhi() {
        return  (0.1 * 2 * Math.PI)/sampleFreq;
    }
}