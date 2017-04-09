package wav_file;

import effects.Effect;
import effects.factory.EffectsFactory;
import project_constants.ProjectConstants;
import util.Complex;
import util.FFT;
import util.GraphsWorker;

import java.io.File;

public class ReadWriteExample{

    public static void main(String[] args){
        try
        {
            WavFile wavFile = WavFile.openWavFile(new File("/home/anton/Anton/Diplom/wavIN/"
                    + ProjectConstants.fileName));

            int numChannels = wavFile.getNumChannels();
            int validBytes = wavFile.getValidBits();

            int buffLen = 4096 * 8;

            double[][] buffer = new double[2][buffLen * numChannels * 100];

            long sampleRate = wavFile.getSampleRate();
            long numFrames = wavFile.getFramesRemaining();

            WavFile wavNewFile = WavFile.newWavFile(new File("/home/anton/Anton/Diplom/WavOut/WAV_OUT"), numChannels, numFrames, validBytes, sampleRate);

            Double [] channel1 = new Double [buffLen];
            Double [] channel2 = new Double [buffLen];

            Double [] buffChannel1;
            Double [] buffChannel2;


            //long framesCounter = 0;
            int framesRead;
            //effects
            Effect effect = EffectsFactory.getInstance().getDistortion();
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

//            final XYSeries simpleSignal = new XYSeries( "Simple Signal" );
//            int length1 = channel1.length;
//            for(int i = 0; i < length1; i++) {
//                simpleSignal.add(i,channel1[i]);
//            }
//
//            final XYSeries outSignal = new XYSeries( "Out Signal" );
//            int length2 = buffChannel1.length;
//            for(int i = 0; i < length2; i++) {
//                outSignal.add(i,buffChannel1[i]);
//            }
//
//            final XYSeriesCollection dataset1 = new XYSeriesCollection();
//            dataset1.addSeries( simpleSignal );
//            dataset1.addSeries(outSignal);
//
//
//            JFreeChart chart1 = ChartFactory.createXYLineChart(
//                    effect.getName(),
//                    "Category",
//                    "Score",
//                    dataset1,
//                    PlotOrientation.VERTICAL,
//                    true, true, false);
//            JFrame frame = new JFrame("Test");
//            frame.setContentPane(new ChartPanel(chart1));
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.pack();
//            frame.setVisible(true);

            Complex [] complexes1 = new Complex[channel1.length];
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
//            graphsWorker.addToDataset(buff2);

            graphsWorker.plotSignal(effect.getName());

        }

        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}