package fr.yan36.westerlife.client.audio;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream;
import com.sedmelluq.discord.lavaplayer.format.Pcm16AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.beam.BeamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlayerManager {
    private final AudioPlayer audioPlayer;

    private final AudioPlayerManager audioPlayerManager;

    private final AudioDataFormat audioDataFormat;

    private final TrackScheduler trackScheduler;

    private final SourceDataLine sourceDataLine;

    private final AudioInputStream audioInputStream;

    private final DataLine.Info sourceDataLineInfo;

    private final AudioProcess audioProcess;

    private final Thread audioProcessThread;

    public PlayerManager() throws LineUnavailableException {
        this.audioPlayerManager = (AudioPlayerManager) new DefaultAudioPlayerManager();
        this.audioPlayerManager.registerSourceManager((AudioSourceManager) new YoutubeAudioSourceManager());
        this.audioPlayerManager.registerSourceManager((AudioSourceManager) SoundCloudAudioSourceManager.createDefault());
        this.audioPlayerManager.registerSourceManager((AudioSourceManager) new BandcampAudioSourceManager());
        this.audioPlayerManager.registerSourceManager((AudioSourceManager) new VimeoAudioSourceManager());
        this.audioPlayerManager.registerSourceManager((AudioSourceManager) new TwitchStreamAudioSourceManager());
        this.audioPlayerManager.registerSourceManager((AudioSourceManager) new BeamAudioSourceManager());
        this.audioPlayerManager.registerSourceManager((AudioSourceManager) new HttpAudioSourceManager());
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        this.audioPlayerManager.getConfiguration().setOutputFormat((AudioDataFormat) new Pcm16AudioDataFormat(2, 44100, 960, true));
        this.audioPlayer = this.audioPlayerManager.createPlayer();
        this.trackScheduler = new TrackScheduler(this.audioPlayer);
        this.audioPlayer.addListener((AudioEventListener) this.trackScheduler);
        this.audioDataFormat = this.audioPlayerManager.getConfiguration().getOutputFormat();
        this.audioInputStream = AudioPlayerInputStream.createStream(this.audioPlayer, this.audioDataFormat, 10000L, false);
        this.sourceDataLineInfo = new DataLine.Info(SourceDataLine.class, this.audioInputStream.getFormat());
        this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(this.sourceDataLineInfo);
        this.audioProcess = new AudioProcess(this.sourceDataLine, this.audioInputStream, this.audioPlayer);
        this.sourceDataLine.open(this.audioInputStream.getFormat());
        this.sourceDataLine.start();
        this.audioProcessThread = new Thread(this.audioProcess, "AudioProcessThread");
        this.audioProcessThread.start();
    }

    public void seek(int secondsToSkip) {
        if (this.audioPlayer.getPlayingTrack() != null) {
            this.audioProcess.numBytes = this.audioInputStream.getFormat().getFrameSize() * (int) this.audioInputStream.getFormat().getFrameRate() * secondsToSkip;
            this.audioProcess.seekSignal = true;
        }
    }

    public void back(int secondsToSkip) {
        if (this.audioPlayer.getPlayingTrack() != null)
            this.audioPlayer.getPlayingTrack().setPosition(Math.max(0L, this.audioPlayer.getPlayingTrack().getPosition() - secondsToSkip * 1000L));
    }

    public void kill() throws LineUnavailableException {
        this.audioProcess.stop();
        this.audioProcess.stop();
        this.audioPlayer.stopTrack();
        this.audioPlayer.destroy();
        this.sourceDataLine.drain();
        this.sourceDataLine.stop();
        this.sourceDataLine.close();
    }

    public void pause() throws LineUnavailableException {
        if (this.audioPlayer.getPlayingTrack() != null) this.audioPlayer.setPaused(!this.audioPlayer.isPaused());
    }

    public void loadTrack(String url) {
        this.audioPlayerManager.loadItemOrdered(this.audioPlayerManager, url, new AudioLoadResultHandler() {
            public void trackLoaded(AudioTrack track) {
                PlayerManager.this.trackScheduler.queue(track);
            }

            public void playlistLoaded(AudioPlaylist playlist) {
            }

            public void noMatches() {
            }

            public void loadFailed(FriendlyException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void loadPlaylist(AudioPlaylist playlist) {
        this.trackScheduler.queue(playlist.getTracks().get(0));
    }

    public void skipTrack() throws LineUnavailableException {
        if (!this.trackScheduler.getQueue().isEmpty()) this.trackScheduler.nextTrack();
    }

    public AudioPlayer getAudioPlayer() {
        return this.audioPlayer;
    }

    public AudioPlayerManager getAudioPlayerManager() {
        return this.audioPlayerManager;
    }

    public AudioDataFormat getAudioDataFormat() {
        return this.audioDataFormat;
    }

    public TrackScheduler getTrackScheduler() {
        return this.trackScheduler;
    }

    public SourceDataLine getSourceDataLine() {
        return this.sourceDataLine;
    }

    public AudioInputStream getAudioInputStream() {
        return this.audioInputStream;
    }

    public DataLine.Info getSourceDataLineInfo() {
        return this.sourceDataLineInfo;
    }

    public AudioProcess getAudioProcess() {
        return this.audioProcess;
    }

    public Thread getAudioProcessThread() {
        return this.audioProcessThread;
    }
}