package com.webcrawler.core;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcrawler.model.LinkTypeData;

public class CrawlerService {
	Logger log = LoggerFactory.getLogger(this.getClass());
	public List<LinkTypeData> extract(String url) {

		List<LinkTypeData> datas = new ArrayList<LinkTypeData>();
		LinkTypeData data = null;

		try {
			Connection conn = Jsoup.connect("http://www." + url);
			Document doc = conn.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) "
					+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36")
					.get();
			Elements links = doc.getElementsByTag("body");

			for (Element link : links) {
				String linkHref = link.attr("href");
				String linkText = link.text();

				data = new LinkTypeData();
				data.setLinkHref(linkHref);
				data.setLinkText(linkText);
				data.setUrl(url);
				datas.add(data);
			}

		} catch (Exception e) {
			log.warn("Fail to connect to the website: {}, because {}", url, e.getMessage());
		}

		return datas;
	}

}
