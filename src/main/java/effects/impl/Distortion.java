package effects.impl;

public class Distortion extends Effect {
    private double limit;
    private double g;
    private long sampleFreq;

    public Distortion(long sf) {
        limit = 0.01;
        g = 3;

        sampleFreq = sf;
    }

    public Double effect(int i, Double[] sig, int bufLen) {
        int mask = bufLen - 1;

        double y = sig[i];
        if(y > limit){y = limit;}
        if(y < limit){y = -limit;}

        return g*y;
    }

//    static public void main(String args[]) {
//        System.out.println("Echo");
//
//        int M = 32;
//        Double[] sig = new Double[M];
//        for (int i = 0; i < M; i++) {
//            sig[i] = 0.0;
//        }
//
//        sig[10] = 1.0;
//
//        for (int i = 0; i < M; i++)
//            System.out.print(sig[i] + " ");
//        System.out.println();
//
//        int sampleFreq = 44100;
//        Distortion eff = new Distortion(sampleFreq);
//        Double[] trans = eff.transform(sig);
//
//        for (int i = 0; i < trans.length; i++)
//            System.out.print(trans[i] + " ");
//    }
}