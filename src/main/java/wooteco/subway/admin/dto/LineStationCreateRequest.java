package wooteco.subway.admin.dto;

import wooteco.subway.admin.domain.LineStation;

public class LineStationCreateRequest {
    private Long preStationId;
    private Long stationId;
    private String preStationName;
    private String stationName;
    private int distance;
    private int duration;

    public LineStationCreateRequest() {
    }

    public LineStationCreateRequest(Long preStationId, Long stationId, int distance, int duration) {
        this.preStationId = preStationId;
        this.stationId = stationId;
        this.distance = distance;
        this.duration = duration;
    }

    public LineStation toLineStation() {
        return new LineStation(preStationId, stationId, distance, duration);
    }

    public Long getPreStationId() {
        return preStationId;
    }

    public Long getStationId() {
        return stationId;
    }

    public String getPreStationName() {
        return preStationName;
    }

    public String getStationName() {
        return stationName;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setPreStationId(Long preStationId) {
        this.preStationId = preStationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public boolean hasNotAnyId() {
        return preStationId == null && stationId == null;
    }
}
