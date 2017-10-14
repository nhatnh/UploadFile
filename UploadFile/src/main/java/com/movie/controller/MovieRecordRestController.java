package com.movie.controller;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.movie.common.ActionUtil;
import com.movie.model.MovieRecord;
import com.movie.service.MovieRecordService;

@RestController
public class MovieRecordRestController {

	private transient final Logger logger = Logger.getLogger(MovieRecordRestController.class.getName());

	@Autowired
	MovieRecordService movieRecordService;

	/*
	 * Retrieve all movie records.
	 */
	@RequestMapping(value = "/record/", method = RequestMethod.GET)
	public ResponseEntity<List<MovieRecord>> listAllMovieRecords() {
		try {
			logger.log(Level.INFO, "Started - listAllMovieRecords.");
			List<MovieRecord> record = movieRecordService.findAllMovieRecords();
			if (record.isEmpty()) {
				return new ResponseEntity<List<MovieRecord>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<MovieRecord>>(record, HttpStatus.OK);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed - listAllMovieRecords: {}", e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Create a movie record.
	 */
	@RequestMapping(value = "/record/", method = RequestMethod.POST)
	public ResponseEntity<Void> createMovieRecord(@RequestBody MovieRecord record, UriComponentsBuilder ucBuilder) {
		try {
			logger.log(Level.INFO, "Started - createMovieRecord. object={}", record.toString());
			if (movieRecordService.isMovieRecordExist(record)) {
				logger.log(Level.INFO, "Finished - createMovieRecord. Already Existed Movie Title {}", record.getTitle());
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

			movieRecordService.saveMovieRecord(record);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/record/{id}").buildAndExpand(record.getId()).toUri());
			logger.log(Level.INFO, "Finished - createMovieRecord. New Movie Record Title {}", record.getTitle());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.log( Level.WARNING, "Failed - createMovieRecord: {}", e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * Upload file with both cases:
	 * 1. id <=0 : upload XML file to system for generating data
	 * 2. id > 0 : upload file to go with a record
	 */
	@RequestMapping(value = "/record/upload/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> handleFileUpload(@PathVariable("id") long id,
			@RequestParam("file") MultipartFile file) {
		try {
			logger.log(Level.INFO, "Started - handleFileUpload. id={}", id);
			ResponseEntity<Void> responseEntity = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			String workingDir = ActionUtil.saveFileToDirectory(file);
			if (!file.isEmpty() && workingDir != null) {
				if (id <= 0) {
					// Process upload XML file
					List<MovieRecord> records = ActionUtil.processUploadedXMLFile(workingDir);
					if (records != null && records.size() > 0) {
						for (MovieRecord record : records) {
							if (movieRecordService.isMovieRecordExist(record)) {
								//System.out.println("A MovieRecord with title " + record.getTitle() + " is already exist");
								continue;
							}
							movieRecordService.saveMovieRecord(record);
							logger.log(Level.INFO, "Saved - handleFileUpload. id={} and Record Movie Title {}",
									Arrays.asList(id, record.getTitle()));
						}
						responseEntity = new ResponseEntity<Void>(HttpStatus.CREATED);
					}
				} else {
					// Process uploading file to go, update the source file name if check sum are matched
					MovieRecord checkRec = movieRecordService.findById(id);
					if (checkRec != null && ActionUtil.isCheckSumMatched(checkRec.getCheckSum(), workingDir)) {
						String sourceFileName = file.getOriginalFilename();
						checkRec.setSourceFile(sourceFileName);
						movieRecordService.updateMovieRecord(checkRec);
						responseEntity = new ResponseEntity<Void>(HttpStatus.OK);
						logger.log(Level.INFO, "Uploaded - handleFileUpload. id={}", id);
					} else {
						responseEntity = new ResponseEntity<Void>(HttpStatus.RESET_CONTENT);
					}
				}
			}
			logger.log(Level.INFO, "Finished - handleFileUpload. id={}", id);
			return responseEntity;
		} catch (Exception e) {
			logger.log( Level.WARNING, "Failed - handleFileUpload: {}", e.toString());
			throw new RuntimeException(e.getMessage());
		}
	}

}
