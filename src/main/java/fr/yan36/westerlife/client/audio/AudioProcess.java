package fr.yan36.westerlife.client.audio;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

public class AudioProcess implements Runnable {
    private final SourceDataLine sourceDataLine;

    private final AudioInputStream audioInputStream;

    private final AudioPlayer audioPlayer;

    public boolean seekSignal;

    public int numBytes;

    private boolean threadStopped;

    private Vector v;

    private ByteBuffer byteBuffer;

    private boolean seekingBack;

    private int j;

    private int k;

    private int seekPos;

    public AudioProcess(SourceDataLine sourceDataLine, AudioInputStream audioInputStream, AudioPlayer audioPlayer) {
        this.v = new Vector();
        this.seekingBack = false;
        this.seekPos = 0;
        this.sourceDataLine = sourceDataLine;
        this.audioInputStream = audioInputStream;
        this.audioPlayer = audioPlayer;
        this.byteBuffer = ByteBuffer.allocate(StandardAudioDataFormats.COMMON_PCM_S16_BE.maximumChunkSize());
    }

    public void run() {
        while (!this.threadStopped) {
            try {
                byte[] data = this.byteBuffer.array();
                int K = 0;
                while (!this.audioPlayer.isPaused()) {
                    if (this.seekingBack) {
                        K = this.seekPos;
                        this.k = data.length;
                        for (this.j = 0; this.j < data.length; this.j++) {
                            if (this.seekPos + this.j < this.v.size()) {
                                data[this.j] = ((Byte)this.v.get(this.seekPos + this.j)).byteValue();
                            } else {
                                this.k = this.j;
                                break;
                            }
                        }
                        this.sourceDataLine.write(data, 0, this.k);
                        this.seekPos += this.k;
                        K += this.k;
                        if (this.seekPos > this.v.size() - 1)
                            this.seekingBack = false;
                    } else {
                        this.k = this.audioInputStream.read(data, 0, data.length);
                        if (this.k < 0)
                            break;
                        this.sourceDataLine.write(data, 0, this.k);
                        if (K >= this.v.size())
                            for (this.j = 0; this.j < this.k; ) {
                                this.v.add(Byte.valueOf(data[this.j]));
                                this.j++;
                            }
                        K += this.k;
                    }
                    if (this.seekSignal) {
                        if (this.seekingBack) {
                            if (this.numBytes < 0) {
                                this.seekPos += this.numBytes;
                                if (this.seekPos < 0)
                                    this.seekPos = 0;
                            } else if (this.numBytes + this.seekPos < this.v.size()) {
                                this.seekPos += this.numBytes;
                            } else {
                                int rem = this.numBytes - this.v.size() - this.seekPos;
                                K = this.v.size();
                                while (rem > 0) {
                                    this.k = this.audioInputStream.read(data, 0, data.length);
                                    if (this.k < 0)
                                        break;
                                    if (K >= this.v.size())
                                        for (this.j = 0; this.j < this.k; ) {
                                            this.v.add(Byte.valueOf(data[this.j]));
                                            this.j++;
                                        }
                                    rem -= this.k;
                                    K += this.k;
                                }
                            }
                        } else if (this.numBytes >= 0) {
                            while (this.numBytes > 0) {
                                this.k = this.audioInputStream.read(data, 0, data.length);
                                if (this.k < 0)
                                    break;
                                if (K >= this.v.size())
                                    for (this.j = 0; this.j < this.k; ) {
                                        this.v.add(Byte.valueOf(data[this.j]));
                                        this.j++;
                                    }
                                this.numBytes -= this.k;
                                K += this.k;
                            }
                        } else {
                            this.seekingBack = true;
                            this.seekPos = this.v.size() + this.numBytes;
                            if (this.seekPos < 0)
                                this.seekPos = 0;
                        }
                        this.seekSignal = false;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() {
        this.threadStopped = true;
    }
}