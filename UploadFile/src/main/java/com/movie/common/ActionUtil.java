package com.movie.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.movie.model.MovieRecord;

public class ActionUtil {

	private ActionUtil() {
		// prevent instantiate
	}

	/*
	 * Get the file from client and save to the directory file path.
	 */
	public static String saveFileToDirectory(MultipartFile file) {
		BufferedOutputStream stream;
		try {
			String workingDir = SystemConstant.DIRECTORY_FILE_PATH + file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			stream = new BufferedOutputStream(new FileOutputStream(new File(workingDir)));
			stream.write(bytes);
			stream.close();
			//System.out.println("Uploaded File has been save to: " + workingDir);
			return workingDir;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Process XML file upload. Read the file by the input path. Parse and
	 * return a list of Movie Records
	 */
	public static List<MovieRecord> processUploadedXMLFile(String path) {
		File fXmlFile = new File(path);
		try {
			if (fXmlFile == null || !fXmlFile.isFile()
					|| "XML".equalsIgnoreCase(URLConnection.guessContentTypeFromName(fXmlFile.getName()))) {
				//System.out.println("Invalid file!");
				fXmlFile.delete();
				return null;
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			List<MovieRecord> records = new ArrayList<MovieRecord>();
			NodeList nList = doc.getElementsByTagName("record");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					String checkSum = eElement.getAttribute("checkSum");
					String title = eElement.getElementsByTagName("title").item(0).getTextContent();
					String description = eElement.getElementsByTagName("description").item(0).getTextContent();
					String mainActor = eElement.getElementsByTagName("mainActor").item(0).getTextContent();
					String producer = eElement.getElementsByTagName("producer").item(0).getTextContent();
					String director = eElement.getElementsByTagName("director").item(0).getTextContent();

					new ArrayList<MovieRecord>();
					records.add(new MovieRecord.Builder()
							.id(null)
							.title(title)
			        		.checkSum(checkSum)
			                .director(director)
			                .description(description)
			                .producer(producer)
			                .mainActor(mainActor)
			                .sourceFile("")
			                .build());
				}
			}
			fXmlFile.delete();
			return records;
		} catch (Exception e) {
			//System.out.println("Deleted File: " + fXmlFile.getName());
			fXmlFile.delete();
			return null;
		}
	}

	/*
	 * Check whether the input CheckSum and the generated CheckSum of the file
	 * are matched.
	 */
	@SuppressWarnings("finally")
	public static boolean isCheckSumMatched(String checkSum, String filePath) {
		boolean check = false;
		if (checkSum == null || checkSum.equals("") || filePath == null || filePath.equals("")) {
			return false;
		}
		File fXmlFile = new File(filePath);
		FileInputStream fis;
		try {
			fis = new FileInputStream(fXmlFile);
			String generatedCheckSum = DigestUtils.md5DigestAsHex(fis);
			fis.close();
			System.out.println("CheckSum: " + checkSum + " - Generated CheckSum: " + generatedCheckSum);
			if (checkSum.equalsIgnoreCase(generatedCheckSum)) {
				check = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			check = false;
		} finally {
			//System.out.println("Deleted File: " + fXmlFile.getName());
			fXmlFile.delete();
			return check;
		}
	}

}
