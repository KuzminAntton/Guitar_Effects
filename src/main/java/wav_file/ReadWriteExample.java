package wav_file;

import effects.Effect;
import effects.factory.EffectsFactory;
import project_constants.ProjectConstants;
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
            Effect effect = EffectsFactory.getInstance().getNaturalEcho();
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
            //for(int i = 0; i < buffLen; i++)
            //System.out.print(buffer[i] + ", ");
            //System.out.print(buffer.length);
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

            GraphsWorker graphsWorker = new GraphsWorker();

            graphsWorker.addToDataset(channel1);

            graphsWorker.plotSignal(effect.getName());

        }

        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}