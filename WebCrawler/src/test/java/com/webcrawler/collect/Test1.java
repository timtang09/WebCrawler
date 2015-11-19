package com.webcrawler.collect;

import java.util.List;

import com.webcrawler.core.AnalyzeContent;
import com.webcrawler.core.CrawlerService;
import com.webcrawler.core.GetUrl;
import com.webcrawler.model.LinkTypeData;

public class Test1 {
    private int index = 0;
    private CrawlerService service = new CrawlerService();
    private GetUrl urls = new GetUrl();
    private List<String> list = urls.importCsv("/home/vagrant/Downloads/top-1m.csv");

    @org.junit.Test
    public void testGetAllElements() {
        for (int i = 1; i < 200; i++) {
            try {
                List<LinkTypeData> datas = service.extract(list.get(i));
                String webContent = datas.get(0).getLinkText();
                AnalyzeContent ana = new AnalyzeContent(webContent);
                if (ana.containsChinese()) {
                    System.out.println(ana.matchingDegree());
                    System.out.println(datas.get(0).getUrl());
                    int rank = i + 1;
                    System.out.println("rank is: " + rank);
                    index = i + 1;
                }
            } catch (Exception e) {
                continue;
            }
        }

    }

}
