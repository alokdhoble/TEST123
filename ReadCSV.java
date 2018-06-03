package com.dvla.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dvla.dataobject.VehicleDetails;
import com.dvla.inter.DvlaVehicle;

public class ReadCSV implements DvlaVehicle {
	final static Logger logger = Logger.getLogger(ReadCSV.class);

	private static ReadCSV csv = null;

	private ReadCSV() {

	}

	public static ReadCSV getInstance() {
		synchronized (ReadCSV.class) {
			if (csv == null) {
				csv = new ReadCSV();
			}

		}
		return csv;
	}

	public List<VehicleDetails> getVehicleDetails(String filePath) {
		List<VehicleDetails> list = null;

		if (filePath == null || "".equals(filePath.trim())) {
			logger.error("Source Path is empty... filePath -->" + filePath + "<--");
			return null;

		} else {
			File fl = new File(filePath);
			if (!fl.exists() || !fl.isFile()) {
				logger.error(
						"Invalid source Path OR filePath is a direcotry and not a fully qualified file path... filePath -->"
								+ filePath + "<--");
				return null;
			} else {
				String fileName = fl.getName();
				if (fileName.lastIndexOf(".") == -1) {
					logger.error("Please give file name with extention...");
					return null;
				}
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
				String csvExtentions = "csv";
				if (ext.equals(null) || ext.trim().equals("")
						|| !csvExtentions.toLowerCase().contains(ext.toLowerCase())) {
					logger.error("Given file is not CSV file... ");
					return null;
				}
			}

		}

		list = readCSV(filePath);
		return list;
	}

	private List<VehicleDetails> readCSV(String filePath) {
		logger.info("Entry point for readCSV() method ");
		String csvFile = filePath;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<VehicleDetails> lst = new ArrayList<VehicleDetails>();
		try {
			logger.info("Read csv File " + filePath);
			VehicleDetails dtls = null;
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] vehicleCSVDtls = line.split(cvsSplitBy);
				if (vehicleCSVDtls.length < 3) {
					logger.error("This row does not contain all minimum required data row -->" + line);
					continue;
				}
				dtls = new VehicleDetails();

				dtls.setVehicleNumber(vehicleCSVDtls[0].trim());
				dtls.setVehicleColor(vehicleCSVDtls[1].trim());
				dtls.setVehicleModel(vehicleCSVDtls[2].trim());
				lst.add(dtls);

			}
			logger.info("Total No of vehicles found in CSV file -->" + lst.size());

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);

				}
			}

		}
		logger.info("Exit from readCSV() method ");
		return lst;
	}

}
