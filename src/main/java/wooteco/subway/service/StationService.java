package wooteco.subway.service;

import org.springframework.stereotype.Service;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.Station;
import wooteco.subway.dto.StationRequest;
import wooteco.subway.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public StationResponse createStation(StationRequest station) {
        Station newStation = Station.from(station);
        validateDuplicateName(newStation);
        return StationResponse.from(stationDao.save(newStation));
    }

    public List<StationResponse> getAllStations() {
        return stationDao.findAll().stream()
                .map(StationResponse::from)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        validateExist(id);
        stationDao.deleteById(id);
    }

    private void validateDuplicateName(Station station) {
        boolean isExisting = stationDao.findByName(station.getName()).isPresent();

        if (isExisting) {
            throw new IllegalArgumentException("이미 존재하는 지하철 역입니다.");
        }
    }

    private void validateExist(Long id) {
        boolean isExisting = stationDao.findById(id).isPresent();

        if (!isExisting) {
            throw new IllegalArgumentException("삭제하려는 지하철 역 ID가 존재하지 않습니다.");
        }
    }
}
