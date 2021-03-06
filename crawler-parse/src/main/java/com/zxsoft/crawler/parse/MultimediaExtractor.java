package com.zxsoft.crawler.parse;

//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.util.List;
//import java.util.Map;

//import org.apache.commons.collections.map.HashedMap;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
//import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlElement;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.zxisl.commons.utils.CollectionUtils;
import com.zxisl.commons.utils.StringUtils;
//import com.zxsoft.crawler.util.Utils;

/**
 * 解析图片、音频、视频
 * @author xiayun
 *
 */
public class MultimediaExtractor {

        private static final int URL_MAX_LENGTH = 500;
//	private static WebClient webClient = new WebClient();
//	private static HtmlPage htmlPage;

//	static {
//		webClient.getOptions().setJavaScriptEnabled(true);
//		webClient.getOptions().setCssEnabled(false);
//		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//		webClient.getOptions().setTimeout(50000);
//		webClient.getOptions().setThrowExceptionOnScriptError(false);
//	}

	/**
	 * 抽取视频URL地址
	 * @param url
	 */
//	public Map<String, String> extractVideo(String url) {
//		Map<String, String> videoMap = new HashedMap();
//		try {
//			htmlPage = webClient.getPage(url);
//		} catch (FailingHttpStatusCodeException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		List<HtmlElement> list = (List<HtmlElement>) htmlPage.getByXPath("//div[@class='vpic_wrap']");
//
//		if (CollectionUtils.isEmpty(list)) {
//			return null;
//		}
//		for (HtmlElement htmlElement : list) {
//
//			try {
//				htmlPage = htmlElement.click();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}
//
//		String pageXml = htmlPage.asXml();
//		Document document = null;
//		try {
//			document = Jsoup.parse(pageXml, Utils.getHost(url));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		String videoUrl = document.getElementsByTag("embed").first().absUrl("src");
//		return null;
//	}

	/**
	 * 抽取图片url, url.length < 500
	 * @param element
	 * @param urlfiter
	 * @return
	 */
	public static String extractImgUrl(Element element, String urlfiter) {
		Elements imgs = element.select("img");
		StringBuilder imgUrlSb = new StringBuilder();
		if (!StringUtils.isEmpty(urlfiter)) {
			for (Element img : imgs) {
				String src = img.attr("abs:src");
				if (imgUrlSb.length() + src.length() > URL_MAX_LENGTH ) 
				        break;
				if (!src.matches(urlfiter))
					imgUrlSb.append(" " + src);
			}
		} else {
			for (Element img : imgs) {
			        String src = img.attr("abs:src");
			        if (imgUrlSb.length() + src.length()> URL_MAX_LENGTH ) 
                                        break;
				imgUrlSb.append(" " + src);
			}
		}
		return imgUrlSb.toString();
	}

	public static String extractVideoUrl(Element element) {
		String url = "";
		Elements eles = element.getElementsByTag("embed");
		if (!CollectionUtils.isEmpty(eles) || !CollectionUtils.isEmpty(eles = element.getElementsByTag("object"))) {
			url = eles.first().absUrl("src");
			if (!url.matches("(http|https)://.*.(mp4|swf|webm)")) {
				return "";
			}
		}

		return url;
	}

	public static String extractAudioUrl(Element element) {
		String url = "";
		Elements eles = element.getElementsByTag("embed");
		if (!CollectionUtils.isEmpty(eles) || !CollectionUtils.isEmpty(eles = element.getElementsByTag("object"))
		        || !CollectionUtils.isEmpty(eles = element.getElementsByTag("audio"))) {

			url = eles.first().absUrl("src");
			if (!url.matches("(http|https)://.*.(mp3|ogg)")) {
				return "";
			}
		}

		return url;
	}
}
