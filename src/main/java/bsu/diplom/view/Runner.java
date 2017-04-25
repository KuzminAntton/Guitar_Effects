package bsu.diplom.view;

import bsu.diplom.effects.Effect;
import bsu.diplom.effects.factory.EffectsFactory;
import bsu.diplom.project_constants.ProjectConstants;
import bsu.diplom.util.Complex;
import bsu.diplom.util.FFT;
import bsu.diplom.util.GraphsWorker;
import bsu.diplom.wav_file.WavFile;

import java.io.File;

public class Runner{

    public static void main(String[] args){
        try
        {
            WavFile wavFile = WavFile.openWavFile(new File(ProjectConstants.path + ProjectConstants.fileName));

            int numChannels = wavFile.getNumChannels();
            int validBytes = wavFile.getValidBits();

            int buffLen = 4096 * 8;

            double[][] buffer = new double[2][buffLen * numChannels * 100];

            long sampleRate = wavFile.getSampleRate();
            long numFrames = wavFile.getFramesRemaining();

            WavFile wavNewFile = WavFile.newWavFile(new File(ProjectConstants.pathToWritingFile +
                    ProjectConstants.outFileName)
                    , numChannels, numFrames, validBytes, sampleRate);

            Double [] channel1 = new Double [buffLen];
            Double [] channel2 = new Double [buffLen];

            Double [] buffChannel1;
            Double [] buffChannel2;


            //long framesCounter = 0;
            int framesRead;
            //bsu.diplom.effects
            Effect effect = EffectsFactory.getInstance().getEcho();
            effect.setSampleFreq(sampleRate);
            do
            {
                // Read frames into buffer
                framesRead = wavFile.readFrames(buffer, buffLen);
                long remaining = wavFile.getFramesRemaining();
                int toWrite = (remaining > buffLen) ? buffLen : (int) remaining ;
                for(int i = 0; i < toWrite; i++){
                    channel1[i] = buffer[0][i];
                    channel2[i] = buffer[1][i];
                }

                Double[] newChanel1 = effect.transform(channel1);
                Double[] newChanel2 = effect.transform(channel2);

                buffChannel1 = newChanel1;
                buffChannel2 = newChanel2;
                double[][] transBuffer = new double[2][newChanel1.length];

                for (int i=0 ; i < newChanel1.length ; i++)
                {
                    transBuffer[0][i] = newChanel1[i];
                    transBuffer[1][i] = newChanel2[i];
                }
                wavNewFile.writeFrames(transBuffer, toWrite);
            }
            while (framesRead != 0);

            wavFile.close();
            wavNewFile.close();

            Complex[] complexes1 = new Complex[channel1.length];
            Complex [] complexes2 = new Complex[buffChannel1.length];

            Double [] buff1 = new Double[channel1.length];
            Double [] buff2 = new Double[buffChannel1.length];

            for(int i = 0; i < channel1.length; i++) {
                complexes1[i] = new Complex(channel1[i],0);
                buff1[i] = complexes1[i].re();
            }


            for(int i = 0; i < buffChannel1.length; i++) {
                complexes2[i] = new Complex(buffChannel1[i],0);
                buff2[i] = complexes2[i].re();
            }

            complexes1 =  FFT.fft(complexes1);
            complexes2 =  FFT.fft(complexes2);

            for(int i = 0; i < buffChannel1.length; i++) {
                buff1[i] = complexes1[i].abs();
            }

            for(int i = 0; i < buffChannel1.length; i++) {
                buff2[i] = complexes2[i].abs();
            }


            GraphsWorker graphsWorker = new GraphsWorker();

            graphsWorker.addToDataset(buff1,buff2);

            graphsWorker.plotSignal(effect.getName());

        }


        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}