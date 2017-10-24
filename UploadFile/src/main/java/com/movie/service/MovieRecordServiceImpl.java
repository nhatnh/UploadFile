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
	private transient final Logger logger = Logger.getLogger(MovieRecordServiceImpl.class.getName());
	
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
		try {
			logger.log(Level.INFO, "Started - findById: {}", id);
			for (MovieRecord record : movieRecords) {
				if (record.getId() == id) {
					return record;
				}
			}
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - findById: {} - error: {}", id, e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Find a movie record by the input checkSum, it is used for checking the CheckSum.
	 */
	public MovieRecord findByCheckSum(String checkSum) {
		try {
			logger.log(Level.INFO, "Started - findByCheckSum: {}", checkSum);
			for (MovieRecord record : movieRecords) {
				if (record.getCheckSum().equalsIgnoreCase(checkSum)) {
					return record;
				}
			}
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - findByCheckSum: {} - error: {}", checkSum, e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Find a movie record by the input title, for checking the duplicated record.
	 */
	public MovieRecord findByTitle(String title) {
		try {
			logger.log(Level.INFO, "Started - findByTitle: {}", title);
			for (MovieRecord record : movieRecords) {
				if (record.getTitle().equalsIgnoreCase(title)) {
					return record;
				}
			}
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - findByTitle: {} - error: {}", title, e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Save a movie record to the current list.
	 */
	public void saveMovieRecord(MovieRecord record) {
		try {
			logger.log(Level.INFO, "Started - saveMovieRecord: {}", record.toString());
			record.setId(counter.incrementAndGet());
			movieRecords.add(record);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - saveMovieRecord: {} - error: {}", record.toString(), e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Update a movie record to the current list.
	 */
	public void updateMovieRecord(MovieRecord record) {
		try {
			logger.log(Level.INFO, "Started - updateMovieRecord: {}", record.toString());
			int index = movieRecords.indexOf(record);
			movieRecords.set(index, record);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - updateMovieRecord: {} - error: {}", record.toString(), e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Delete a movie record to the current list.
	 */
	public void deleteMovieRecordById(long id) {
		try {
			logger.log(Level.INFO, "Started - deleteMovieRecordById: {}", id);
			for (Iterator<MovieRecord> iterator = movieRecords.iterator(); iterator.hasNext();) {
				MovieRecord record = iterator.next();
				if (record.getId() == id) {
					iterator.remove();
				}
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - deleteMovieRecordById: {} - error: {}", id, e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Retrieve all movie records.
	 */
	public List<MovieRecord> findAllMovieRecords() {
		try {
			logger.log(Level.INFO, "Started - findAllMovieRecords");
			return movieRecords;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - findAllMovieRecords - error: {}", e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Check whether a movie record is existed in the current list by the Title.
	 */
	public boolean isMovieRecordExist(MovieRecord record) {
		try {
			logger.log(Level.INFO, "Started - isMovieRecordExist: {}", record.toString());
			return findByTitle(record.getTitle()) != null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - isMovieRecordExist: {} - error: {}", record.toString(), e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Check whether a movie record is matched in the current list by the CheckSum.
	 */
	public boolean isCheckSumMatched(MovieRecord record) {
		try {
			logger.log(Level.INFO, "Started - isCheckSumMatched: {}", record.toString());
			return findByCheckSum(record.getCheckSum()) != null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - isCheckSumMatched: {} - error: {}", record.toString(), e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Generate dummy data at the beginning.
	 */
	private static List<MovieRecord> populateDummyMovieRecords() {
		try {
			logger.log(Level.INFO, "Started - populateDummyMovieRecords");
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
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - populateDummyMovieRecords - error: {}", e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

}
