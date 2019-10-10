package it.magicorp.helpgrid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.Timer;

import javazoom.jl.player.Player;

public class SoundUtils
{
	public static boolean isPlaying = false;
	
	public static void play(int file)
	{
		try
		{
			Runtime.getRuntime().exec("mpg321 /home/pi/Desktop/HelpGrid/" + (file == 0 ? "beep1.mp3" : "beep2.mp3"));
			/*FileInputStream is = new FileInputStream(file == 0 ? "beep1.mp3" : "beep2.mp3");
			Player p = new Player(is);
			p.play();*/
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Deprecated
	public static void beep(double freq, final double millis) {
	    try
	    {
	    	SoundUtils.isPlaying = true;
	        final Clip clip = AudioSystem.getClip();
	        AudioFormat af = clip.getFormat();

	        if (af.getSampleSizeInBits() != 16)
	        {
	            System.err.println("Weird sample size.  Dunno what to do with it.");
	            return;
	        }
	        
	        int bytesPerFrame = af.getFrameSize();
	        double fps = 11025;
	        int frames = (int)(fps * (millis / 1000));
	        frames *= 4;

	        byte[] data = new byte[frames * bytesPerFrame];

	        double freqFactor = (Math.PI / 2) * freq / fps;
	        double ampFactor = (1 << af.getSampleSizeInBits()) - 1;

	        for (int frame = 0; frame < frames; frame++)
	        {
	            short sample = (short)(0.5 * ampFactor * Math.sin(frame * freqFactor));
	            data[(frame * bytesPerFrame) + 0] = (byte)((sample >> (1 * 8)) & 0xFF);
	            data[(frame * bytesPerFrame) + 1] = (byte)((sample >> (0 * 8)) & 0xFF);
	            data[(frame * bytesPerFrame) + 2] = (byte)((sample >> (1 * 8)) & 0xFF);
	            data[(frame * bytesPerFrame) + 3] = (byte)((sample >> (0 * 8)) & 0xFF);
	        }

	        clip.open(af, data, 0, data.length);

	        clip.addLineListener(new LineListener()
	        {
	            @Override
	            public void update(LineEvent event)
	            {
	                if (event.getType() == javax.sound.sampled.LineEvent.Type.START)
	                {
	                    Timer t = new Timer((int)millis + 1, new ActionListener()
	                    {
	                        @Override
	                        public void actionPerformed(ActionEvent e)
	                        {
	                            clip.close();
	                        }
	                    });
	                    t.setRepeats(false);
	                    t.start();
	                }
	            }
	        });
	        clip.start();
	        SoundUtils.isPlaying = false;
	    }
	    catch (Exception ex)
	    {
	       	ex.printStackTrace();
	        SoundUtils.isPlaying = false;
	    }
	}
}
