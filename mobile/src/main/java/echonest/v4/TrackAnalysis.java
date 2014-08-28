package echonest.v4;

import echonest.v4.*;
import echonest.v4.Segment;
import echonest.v4.util.MQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrackAnalysis {

    @SuppressWarnings("unchecked")
    private Map map;
    private MQuery mq;

    @SuppressWarnings("unchecked")
    TrackAnalysis(Map map) {
        this.map = map;
        this.mq = new MQuery(map);
    }

    @SuppressWarnings("unchecked")
    public Map getMap() {
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public Integer getNumSamples() {
        return mq.getInteger("track.num_samples");
    }

    public Double getDuration() {
        return mq.getDouble("track.duration");
    }

    public String getMD5() {
        return mq.getString("track.sample_md5");
    }

    public Double getSampleRate() {
        return mq.getDouble("track.analysis_sample_rate");
    }

    public Integer getNumChannels() {
        return mq.getInteger("track.analysis_channels");
    }

    public Double getEndOfFadeIn() {
        return mq.getDouble("track.end_of_fade_in");
    }

    public Double getStartOfFadeOut() {
        return mq.getDouble("track.start_of_fade_out");
    }

    public Double getLoudness() {
        return mq.getDouble("track.loudness");
    }

    public Double getTempo() {
        return mq.getDouble("track.tempo");
    }

    public Double getTempoConfidence() {
        return mq.getDouble("track.tempo_confidence");
    }

    public Integer getTimeSignature() {
        return mq.getInteger("track.time_signature");
    }

    public Double getTimeSignatureConfidence() {
        return mq.getDouble("track.time_signature_confidence");
    }

    public Integer getKey() {
        return mq.getInteger("track.key");
    }

    public Double getKeyConfidence() {
        return mq.getDouble("track.key_confidence");
    }

    public Integer getMode() {
        return mq.getInteger("track.mode");
    }

    public Double getModeConfidence() {
        return mq.getDouble("track.mode_confidence");
    }

    @SuppressWarnings("unchecked")
    public List<echonest.v4.TimedEvent> getSections() {
        List<echonest.v4.TimedEvent> results = new ArrayList<echonest.v4.TimedEvent>();
        List event = (List) mq.getObject("sections");
        for (int i = 0; i < event.size(); i++) {
            results.add(new echonest.v4.TimedEvent((Map) event.get(i)));
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public List<echonest.v4.TimedEvent> getBars() {
        List<echonest.v4.TimedEvent> results = new ArrayList<echonest.v4.TimedEvent>();
        List event = (List) mq.getObject("bars");
        for (int i = 0; i < event.size(); i++) {
            results.add(new echonest.v4.TimedEvent((Map) event.get(i)));
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public List<echonest.v4.TimedEvent> getBeats() {
        List<echonest.v4.TimedEvent> results = new ArrayList<echonest.v4.TimedEvent>();
        List event = (List) mq.getObject("beats");
        for (int i = 0; i < event.size(); i++) {
            results.add(new echonest.v4.TimedEvent((Map) event.get(i)));
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public List<echonest.v4.TimedEvent> getTatums() {
        List<echonest.v4.TimedEvent> results = new ArrayList<echonest.v4.TimedEvent>();
        List event = (List) mq.getObject("tatums");
        for (int i = 0; i < event.size(); i++) {
            results.add(new echonest.v4.TimedEvent((Map) event.get(i)));
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public List<echonest.v4.Segment> getSegments() {
        List<echonest.v4.Segment> results = new ArrayList<echonest.v4.Segment>();
        List event = (List) mq.getObject("segments");
        for (int i = 0; i < event.size(); i++) {
            results.add(new echonest.v4.Segment((Map) event.get(i)));
        }
        return results;
    }

    public void dump() {
        System.out.println("num samples : " + getNumSamples());
        System.out.println("sample md5  : " + getMD5());
        System.out.println("num channels: " + getNumChannels());
        System.out.println("duration    : " + getDuration());

        System.out.println(" Sections ");
        List<echonest.v4.TimedEvent> sections = getSections();
        for (echonest.v4.TimedEvent e : sections) {
            System.out.println(e);
        }

        System.out.println(" Bars ");
        List<echonest.v4.TimedEvent> bars = getBars();
        for (echonest.v4.TimedEvent e : bars) {
            System.out.println(e);
        }

        System.out.println(" Beats ");
        List<echonest.v4.TimedEvent> beats = getBeats();
        for (echonest.v4.TimedEvent e : beats) {
            System.out.println(e);
        }

        System.out.println(" Tatums ");
        List<echonest.v4.TimedEvent> tatums = getTatums();
        for (echonest.v4.TimedEvent e : tatums) {
            System.out.println(e);
        }

        System.out.println(" Segments ");
        List<echonest.v4.Segment> segments = getSegments();
        for (Segment e : segments) {
            System.out.println(e);
        }
    }
}
