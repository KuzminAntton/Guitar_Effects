package effects.impl;

import effects.Effect;

public class Distortion extends Effect {
    private final double LIMIT = 0.01;
    private final double G = 3;

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;

        double y = sig[i];
        if(y > LIMIT){y = LIMIT;}
        if(y < LIMIT){y = -LIMIT;}

        return G*y;
    }

}