package com.movie.controller;

import java.util.List;

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

	@Autowired
	MovieRecordService movieRecordService;

	/*
	 * Retrieve all movie records.
	 */
	@RequestMapping(value = "/record/", method = RequestMethod.GET)
	public ResponseEntity<List<MovieRecord>> listAllMovieRecords() {
		List<MovieRecord> record = movieRecordService.findAllMovieRecords();
		if (record.isEmpty()) {
			return new ResponseEntity<List<MovieRecord>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MovieRecord>>(record, HttpStatus.OK);
	}

	/*
	 * Create a movie record.
	 */
	@RequestMapping(value = "/record/", method = RequestMethod.POST)
	public ResponseEntity<Void> createMovieRecord(@RequestBody MovieRecord record, UriComponentsBuilder ucBuilder) {
		//System.out.println("Creating MovieRecord Title " + record.getTitle());
		if (movieRecordService.isMovieRecordExist(record)) {
			//System.out.println("A MovieRecord with title " + record.getTitle() + " is already exist");
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		movieRecordService.saveMovieRecord(record);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/record/{id}").buildAndExpand(record.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
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
					} else {
						responseEntity = new ResponseEntity<Void>(HttpStatus.RESET_CONTENT);
					}
				}
			}
			return responseEntity;
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

}
