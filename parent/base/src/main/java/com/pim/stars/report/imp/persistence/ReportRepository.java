package com.pim.stars.report.imp.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<ReportEntity, Long> {

	public List<ReportEntity> findByGameIdAndYearAndRaceId(String gameId, int year, String raceId);
}
