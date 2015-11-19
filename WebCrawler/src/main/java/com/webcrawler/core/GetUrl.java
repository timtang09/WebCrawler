package com.webcrawler.core;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

public class GetUrl {
	
	public List<String> importCsv(String fileName) {
		List<String> list = new ArrayList<>();
		CsvReader reader = null;
		try {
			reader = new CsvReader(fileName, ',', Charset.forName("GBK"));
			while (reader.readRecord()) {
				String[] str = reader.getValues();
				if (str != null && str.length > 0) {
					if (str[0] != null && !"".equals(str[0].trim())) {
						list.add(str[1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
		return list;
	}

}
