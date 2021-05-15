package wooteco.subway.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import wooteco.subway.exception.InvalidSectionOnLineException;
import wooteco.subway.exception.NotFoundException;

public class Sections {

    private static final int DELETABLE_COUNT = 2;
    private final List<Section> sections;

    public Sections(List<Section> sections) {
        this.sections = sections;
    }

    public boolean isBothEndSection(Section section) {
        Deque<Long> ids = sortedStationIds();
        return section.hasSameStation(ids.peekFirst()) || section.hasSameStation(ids.peekLast());
    }

    public boolean isBothEndStation(Long stationId) {
        Deque<Long> ids = sortedStationIds();
        return stationId.equals(ids.peekFirst())
            || stationId.equals(ids.peekLast());
    }

    public Deque<Long> sortedStationIds() {
        Deque<Long> stationIds = new ArrayDeque<>();
        Map<Long, Long> upStationIds = new LinkedHashMap<>();
        Map<Long, Long> downStationIds = new LinkedHashMap<>();

        initStationIds(stationIds, upStationIds, downStationIds);
        sortStationsById(stationIds, upStationIds, downStationIds);
        return new ArrayDeque<>(stationIds);
    }

    private void initStationIds(Deque<Long> stationIds, Map<Long, Long> upStationIds,
        Map<Long, Long> downStationIds) {
        for (Section section : sections) {
            upStationIds.put(section.getUpStationId(), section.getDownStationId());
            downStationIds.put(section.getDownStationId(), section.getUpStationId());
        }

        Section section = sections.get(0);
        stationIds.addFirst(section.getUpStationId());
        stationIds.addLast(section.getDownStationId());
    }

    private void sortStationsById(Deque<Long> stationIds, Map<Long, Long> upStationIds,
        Map<Long, Long> downStationIds) {
        while (upStationIds.containsKey(stationIds.peekLast())) {
            Long id = stationIds.peekLast();
            stationIds.addLast(upStationIds.get(id));
        }

        while (downStationIds.containsKey(stationIds.peekFirst())) {
            Long id = stationIds.peekFirst();
            stationIds.addFirst(downStationIds.get(id));
        }
    }

    public void validateInsertable(Section section) {
        boolean isUpStationExisted = isNotExistOnLine(section.getUpStationId());
        boolean isDownStationExisted = isNotExistOnLine(section.getDownStationId());

        if (isUpStationExisted == isDownStationExisted) {
            throw new InvalidSectionOnLineException();
        }
    }

    public void validateDeletableCount() {
        if (sections.size() < DELETABLE_COUNT) {
            throw new IllegalStateException("구간을 제거할 수 없습니다.");
        }
    }

    public void validateExistStation(Long stationId) {
        if (isNotExistOnLine(stationId)) {
            throw new NotFoundException();
        }
    }

    private boolean isNotExistOnLine(Long stationId) {
        return sections.stream()
            .noneMatch(section -> section.hasSameStation(stationId));
    }

    public boolean isNotEmpty() {
        return !sections.isEmpty();
    }

    public Section findByStationId(Section section) {
        return sections.stream()
            .filter(section::hasSameStationBySection)
            .findAny()
            .orElseThrow(InvalidSectionOnLineException::new);
    }
}
