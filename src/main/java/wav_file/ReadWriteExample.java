package wav_file;

import effects.Effect;
import effects.factory.EffectsFactory;
import effects.impl.Reverb;

import java.io.*;

public class ReadWriteExample{

    public static void main(String[] args){
        try
        {
            WavFile wavFile = WavFile.openWavFile(new File("/home/anton/Anton/Diplom/wavIN/Trav_Ac_17.wav"));

            int numChannels = wavFile.getNumChannels();
            int validBytes = wavFile.getValidBits();
            int buffLen = 4096 * 4;
            double[][] buffer = new double[2][buffLen * numChannels];
            long sampleRate = wavFile.getSampleRate();
            long numFrames = wavFile.getFramesRemaining();

            WavFile wavNewFile = WavFile.newWavFile(new File("/home/anton/Anton/Diplom/WavOut/WAV_OUT"), numChannels, numFrames, validBytes, sampleRate);

            Double [] channel1 = new Double [buffLen];
            Double [] channel2 = new Double [buffLen];

            //long framesCounter = 0;
            int framesRead;
            //effcts
            Effect effect = EffectsFactory.getInstance().getNaturalEcho();
            effect.setSampleFreq(sampleRate);
            do
            {
                // Read frames into buffer
                framesRead = wavFile.readFrames(buffer, buffLen);
                long remaining = wavFile.getFramesRemaining();
                int toWrite = (remaining > buffLen) ? buffLen : (int) remaining;
                for(int i = 0; i < toWrite; i++){
                    channel1[i] = buffer[0][i];
                    channel2[i] = buffer[1][i];
                }

                Double[] newChanel1 = effect.transform(channel1);
                Double[] newChanel2 = effect.transform(channel2);
                double[][] transBuffer = new double[2][newChanel1.length];

                for (int i=0 ; i < newChanel1.length ; i++)
                {
                    transBuffer[0][i] = newChanel1[i];
                    transBuffer[1][i] = newChanel2[i];
                }
                wavNewFile.writeFrames(transBuffer, toWrite);
            }
            while (framesRead != 0);
            //for(int i = 0; i < buffLen; i++)
            //System.out.print(buffer[i] + ", ");
            //System.out.print(buffer.length);
            wavFile.close();
            wavNewFile.close();

        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}