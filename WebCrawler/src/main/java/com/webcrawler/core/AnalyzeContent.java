package com.webcrawler.core;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyzeContent {
	
	private String webContent;
	
	public AnalyzeContent(String webContent) {
		this.webContent = webContent;
	}
	
	public boolean containsChinese() {
		boolean hasChinese = false;
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher match = pattern.matcher(webContent);
		if (match.find()) {
            hasChinese = true;
        }
		return hasChinese;
	}
	
	public int matchingDegree() {
		int degree = 0;
		List<String> list = Arrays.asList("零售","付款","支付","分期","男装","女装","数码","家电","图书"
				,"登陆","注册","订单","采购","服装","搜索","购物","特惠","家用","手机","团购","商店");
		for (String keyWords : list) {
			if (webContent.contains(keyWords)) {
				degree++;
			}
		}
		return degree;
	}

}
