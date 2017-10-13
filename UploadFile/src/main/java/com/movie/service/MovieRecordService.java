package com.movie.service;

import java.util.List;

import com.movie.model.MovieRecord;

public interface MovieRecordService {
	MovieRecord findById(long id);

	MovieRecord findByCheckSum(String checkSum);
	
	MovieRecord findByTitle(String title);

	void saveMovieRecord(MovieRecord record);

	void updateMovieRecord(MovieRecord record);

	void deleteMovieRecordById(long id);

	List<MovieRecord> findAllMovieRecords();

	public boolean isMovieRecordExist(MovieRecord record);
	
	public boolean isCheckSumMatched(MovieRecord record);
}
