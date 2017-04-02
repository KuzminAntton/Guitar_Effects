package effects.impl;

public abstract class Effect {

    protected abstract Double effect(int i, Double[] sig, int bufLen);

    public Double[] transform(Double[] sig) {
        int bufLen = 1;
        while (bufLen <= sig.length)
            bufLen *= 2;
        bufLen /= 2;

        Double[] trans = new Double[bufLen];
        for (int i = 0; i < bufLen; i++)
            trans[i] = effect(i, sig, bufLen);

        return trans;
    }
}
