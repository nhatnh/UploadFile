package com.movie.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.model.MovieRecord;

@Service("movieRecordService")
@Transactional
public class MovieRecordServiceImpl implements MovieRecordService {

	// Auto Generate ID Number
	private static final AtomicLong counter = new AtomicLong();

	// List of current movie records
	private static List<MovieRecord> movieRecords;

	// Generate dummy records
	static {
		movieRecords = populateDummyMovieRecords();
	}

	/*
	 * Find a movie record by the input id.
	 */
	public MovieRecord findById(long id) {
		for (MovieRecord record : movieRecords) {
			if (record.getId() == id) {
				return record;
			}
		}
		return null;
	}

	/*
	 * Find a movie record by the input checkSum, it is used for checking the CheckSum.
	 */
	public MovieRecord findByCheckSum(String checkSum) {
		for (MovieRecord record : movieRecords) {
			if (record.getCheckSum().equalsIgnoreCase(checkSum)) {
				return record;
			}
		}
		return null;
	}

	/*
	 * Find a movie record by the input title, for checking the duplicated record.
	 */
	public MovieRecord findByTitle(String title) {
		for (MovieRecord record : movieRecords) {
			if (record.getTitle().equalsIgnoreCase(title)) {
				return record;
			}
		}
		return null;
	}

	/*
	 * Save a movie record to the current list.
	 */
	public void saveMovieRecord(MovieRecord record) {
		record.setId(counter.incrementAndGet());
		movieRecords.add(record);
	}

	/*
	 * Update a movie record to the current list.
	 */
	public void updateMovieRecord(MovieRecord record) {
		int index = movieRecords.indexOf(record);
		movieRecords.set(index, record);
	}

	/*
	 * Delete a movie record to the current list.
	 */
	public void deleteMovieRecordById(long id) {
		for (Iterator<MovieRecord> iterator = movieRecords.iterator(); iterator.hasNext();) {
			MovieRecord record = iterator.next();
			if (record.getId() == id) {
				iterator.remove();
			}
		}
	}

	/*
	 * Retrieve all movie records.
	 */
	public List<MovieRecord> findAllMovieRecords() {
		return movieRecords;
	}

	/*
	 * Check whether a movie record is existed in the current list by the Title.
	 */
	public boolean isMovieRecordExist(MovieRecord record) {
		return findByTitle(record.getTitle()) != null;
	}

	/*
	 * Check whether a movie record is matched in the current list by the CheckSum.
	 */
	public boolean isCheckSumMatched(MovieRecord record) {
		return findByCheckSum(record.getCheckSum()) != null;
	}

	/*
	 * Generate dummy data at the beginning.
	 */
	private static List<MovieRecord> populateDummyMovieRecords() {
		List<MovieRecord> records = new ArrayList<MovieRecord>();
		records.add(new MovieRecord.Builder()
				.id(counter.incrementAndGet())
				.title("Titanic")
        		.checkSum("13d0583476e63446a60862887f6f2939")
                .director("James Cameron")
                .description("Romantic Love Movie")
                .producer("New Picture")
                .mainActor("Jack, Rose")
                .sourceFile("Titanic.mp4")
                .build());
		records.add(new MovieRecord.Builder()
				.id(counter.incrementAndGet())
				.title("King Kong")
        		.checkSum("13d0583476e63446a60862887f6f2939")
                .director("Peter Jackson")
                .description("An Amazing Adventure Movie")
                .producer("ABC Picture")
                .mainActor("Naomi, Adrien")
                .sourceFile("")
                .build());

		return records;
	}

}
