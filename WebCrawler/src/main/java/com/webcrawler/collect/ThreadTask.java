package com.webcrawler.collect;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.webcrawler.core.AnalyzeContent;
import com.webcrawler.core.CrawlerService;
import com.webcrawler.core.GetUrl;
import com.webcrawler.model.LinkTypeData;

/**
 * Output a list of websites into a txt file including its url, original rank in csv file and its
 * posibility of being a retailer.
 *
 * @author tim
 */
public class ThreadTask extends Thread {

    private static final int MYTHREADS = 16;
    private static final String csvLocation = "/home/vagrant/Downloads/top-1m.csv";
    private static final String outputLocation = "/home/vagrant/Downloads/1.txt";
    public static List<Map<String, String>> successList = new ArrayList<>();
    public static Map<String, String> info = null;

    public static void main(String args[]) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        GetUrl urls = new GetUrl();
        List<String> list = urls.importCsv(csvLocation);
        for (int i = 1; i < 100; i++) {
            String url = list.get(i);
            Runnable worker = new MyRunnable(url, i);
            executor.execute(worker);
        }
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {

        }
        System.out.println("All threads finished.");
        // write to txt file
        FileWriter writer = null;
        try {
            writer = new FileWriter(outputLocation);
            for (int j = 0; j < successList.size(); j++) {
                writer.write(successList.get(j).toString() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    public static class MyRunnable implements Runnable {
        private final String url;
        private final int i;
        private CrawlerService service = new CrawlerService();

        MyRunnable(String url, int i) {
            this.url = url;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                List<LinkTypeData> datas = service.extract(url);
                String webContent = datas.get(0).getLinkText();
                AnalyzeContent ana = new AnalyzeContent(webContent);
                if (ana.containsChinese() && ana.matchingDegree() > 0) {
                    info = new HashMap<>();
                    System.out.println(ana.matchingDegree());
                    info.put("MatchingDegree", String.valueOf(ana.matchingDegree()));
                    System.out.println(datas.get(0).getUrl());
                    info.put("Website", url);
                    int rank = i + 1;
                    System.out.println("rank is: " + rank);
                    info.put("Rank", String.valueOf(rank));
                    successList.add(info);
                }
            } catch (Exception e) {
            }
        }

    }
}
