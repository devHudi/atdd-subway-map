package wooteco.subway.admin.dto;

import org.springframework.data.relational.core.mapping.Column;

import wooteco.subway.admin.domain.LineStation;

public class LineStationCreateRequest {
    @Column("pre_station")
    private Long preStationId;
    @Column("station")
    private Long stationId;
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

    public Long getPreStationId() {
        return preStationId;
    }

    public Long getStationId() {
        return stationId;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public LineStation toLineStation() {
        return new LineStation(preStationId, stationId, distance, duration);
    }
}
