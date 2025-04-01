package com.spring.JspringProject.controller;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.JspringProject.service.MemberService;
import com.spring.JspringProject.service.StudyService;
import com.spring.JspringProject.vo.CrawlingVo;
import com.spring.JspringProject.vo.MailVo;
import com.spring.JspringProject.vo.MemberVo;

import io.github.bonigarcia.wdm.WebDriverManager;


//@RestController
@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	private StudyService studyService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping("/ajax/ajaxForm")
	public String ajaxFormGet() {
		return "study/ajax/ajaxForm";
	}
	
//  동기식 호출
//	@RequestMapping("/ajax/ajaxTest1")
//	public String ajaxTest1Get(Model model, int idx) {
//		model.addAttribute("idx", idx);
//		return "study/ajax/ajaxForm";
//	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest1", method = RequestMethod.POST, produces="application/text; charset=utf-8")
//	@RequestMapping(value="/ajax/ajaxTest1", method = RequestMethod.POST)
	public String ajaxTest1Post(int idx) {
		String str = "전송값 : " + idx;
		return str;
	}
	
	@RequestMapping(value="/ajax/ajaxTest2_1", method = RequestMethod.GET)
	public String ajaxTest2_1Get() {
		return "study/ajax/ajaxTest2_1";
	}
	
//	@ResponseBody
//	@RequestMapping(value="/ajax/ajaxTest2_1", method = RequestMethod.POST)
//	public String[] ajaxTest2_1Post(String dodo) {
//		String[] strArray = new String[100];
//		strArray = studyService.getCityStringArray(dodo);
//		return strArray;
//	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest2_1", method = RequestMethod.POST)
	public String[] ajaxTest2_1Post(String dodo) {
		return studyService.getCityStringArray(dodo);
	}
	
	@RequestMapping(value="/ajax/ajaxTest2_2", method = RequestMethod.GET)
	public String ajaxTest2_2Get() {
		return "study/ajax/ajaxTest2_2";
	}
	
	@ResponseBody
	@RequestMapping(value="/ajax/ajaxTest2_2", method = RequestMethod.POST)
	public List<String> ajaxTest2_2Post(String dodo) {
		return studyService.getCityVosArray(dodo);
	}
	
	// 싱글파일 업로드 폼 보기
	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.GET)
	public String fileUploadGet(HttpServletRequest request, Model model) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload");
		String[] files = new File(realPath).list();
		model.addAttribute("files", files);
		
		return "study/fileUpload/fileUpload";
	}
	
	// 싱글파일 업로드 처리
	@RequestMapping(value = "/fileUpload/fileUpload", method = RequestMethod.POST)
	public String fileUploadPost(MultipartFile fName, String mid) {
		int res = studyService.fileUpload(fName, mid);
		
		if(res != 0) return "redirect:/message/fileUploadOk";
		else return "redirect:/message/fileUploadNo";
	}
	
	// 선택된 파일 1개 삭제 처리
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDelete", method = RequestMethod.POST)
	public String fileDeletePost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		String res = "0";
		File fName = new File(realPath + file);
		
		if(fName.exists()) {
			fName.delete();
			res = "1";
		}
		
		return res;
	}
	
	// 모든 파일 삭제 처리
	@ResponseBody
	@RequestMapping(value = "/fileUpload/fileDeleteAll", method = RequestMethod.POST)
	public String fileDeleteAllPost(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		String res = "0";
		File folder = new File(realPath);
		if(!folder.exists()) return res;
		
		File[] files = folder.listFiles();
		
		if(files.length != 0) {
			for(File file : files) {
				file.delete();
			}
			res = "1";
		}
		
		return res;
	}
	
	// 멀티파일 업로드 폼보기
	@RequestMapping(value = "/fileUpload/multiFile", method = RequestMethod.GET)
	public String multiFileGet() {
		return "study/fileUpload/multiFile";
	}
	
	// 멀티파일 업로드 처리
	@RequestMapping(value = "/fileUpload/multiFile", method = RequestMethod.POST)
	public String multiFilePost(MultipartHttpServletRequest mFile) {
		int res = studyService.multiFileUpload(mFile);
		
		if(res != 0) return "redirect:/message/multiFileUploadOk";
		else return "redirect:/message/multiFileUploadNo";
	}
	
	// 메일 연습폼 보기
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.GET)
	public String mailFormGet(HttpServletRequest request) {
		return "study/mail/mailForm";
	}
	
	// 메일 연습 보내기
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.POST)
	public String mailFormPost(HttpServletRequest request, MailVo vo) throws MessagingException {
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		// MimeMessage(), MideMessageHelper()
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메일보관함에 메세지 내용 저장...후... 처리
		messageHelper.setTo(toMail);
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		// 메세지에 추가로 필요한 사항을 messageHelper에 추가로 넣어준다.
		content = content.replace("\n", "<br>");
		content += "<br><hr><h3>JspringProject 에서 보냅니다.</h3><br>";
		content += "<p><img src=\"cid:main.jpg\" width='550px'></p>";
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/cjgreen'>Green Project</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);
		
		// 본문에 기재된 그림파일의 경로
		//FileSystemResource file = new FileSystemResource("D:\\springProject\\springframework\\works\\JspringProject\\src\\main\\webapp\\resources\\images\\main.jpg");
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.jpg"));
		messageHelper.addInline("main.jpg", file);
		
		// 첨부파일 보내기
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/chicago.jpg"));
		messageHelper.addAttachment("chicago.jpg", file);
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/chicago.zip"));
		messageHelper.addAttachment("chicago.zip", file);
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}
	
	@RequestMapping(value = "/modal/modalForm", method = RequestMethod.GET)
	public String modalFormGet(Model model) {
		model.addAttribute("name", "홍길동");
		model.addAttribute("age", "22");
		model.addAttribute("address", "서울");
		
		// 관리자의 정보를 front에 modal로 출력하시오.
		MemberVo vo = memberService.getMemberIdCheck("admin");
		model.addAttribute("vo", vo);
		System.out.println("vo : " + vo);
		
		List<MemberVo> vos = memberService.getMemberList(0, 1000, 99);	// level 99는 전체자료 조회(0번 인덱스부터 1000건 조회)
		model.addAttribute("vos", vos);
		
		return "study/modal/modalForm";
	}
	
	// 인터넷 달력 연습
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public String calendarGet() {
		studyService.getCalendar();
		return "study/calendar/calendar";
	}
	
	// 크롤링(jsoup) 폼 보기
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.GET)
	public String jsoupGet() {
		return "study/crawling/jsoup";
	}
	
	// 크롤링(jsoup) 처리...
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup", method = RequestMethod.POST, produces="application/text; charset=utf-8")
	public String jsoupPost(String url, String selector) throws IOException {
		
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements elements = document.select(selector);
		
		int i = 0;
		String str = "";
		for(Element element : elements) {
			i++;
			//System.out.println("element : " + element);
			System.out.println(i + ". " + element.text());
			str += i + ". " + element.text() + "<br/>";
		}
		return str;
	}
	
	// 크롤링(jsoup) 처리2...
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup2", method = RequestMethod.POST)
	public List<String> jsoup2Post(String url, String selector) throws IOException {
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements elements = document.select(selector);
		
		int i = 0;
		List<String> vos = new ArrayList<String>();
		for(Element element : elements) {
			i++;
			//System.out.println("element : " + element);
			System.out.println(i + ". " + element.text());
			vos.add(i + ". " + element.text());
		}
		return vos;
	}
	
	// 크롤링(jsoup) 처리3...
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup3", method = RequestMethod.POST)
	public List<String> jsoup3Post(String url, String selector) throws IOException {
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements elements = document.select(selector);
		
		int i = 0;
		List<String> vos = new ArrayList<String>();
		for(Element element : elements) {
			i++;
			System.out.println("element : " + element);
			//System.out.println(i + ". " + element.text());
			vos.add(i + ". " + element.html().replace("data-", ""));
		}
		return vos;
	}
	
	// 크롤링(jsoup) 처리4...
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup4", method = RequestMethod.POST)
	public List<String> jsoup4Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		Document document = conn.get();
		
		Elements elements = document.select(searchSelector);
		
		int i = 0;
		List<String> vos = new ArrayList<String>();
		for(Element element : elements) {
			i++;
			System.out.println("element : " + element);
			//System.out.println(i + ". " + element.text());
			//vos.add(i + ". " + element.html().replace("data-", ""));
			vos.add(i + ". " + element.text());
		}
		return vos;
	}
	
	// 크롤링(jsoup) 처리5...
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup5", method = RequestMethod.POST)
	public List<String> jsoup5Post(String search, String searchSelector) throws IOException {
		Connection conn = Jsoup.connect(search);
		Document document = conn.get();
		
		Elements elements = document.select(searchSelector);
		
		int i = 0;
		List<String> vos = new ArrayList<String>();
		for(Element element : elements) {
			i++;
			System.out.println("element : " + element);
			//System.out.println(i + ". " + element.text());
			//vos.add(i + ". " + element.text());
			//vos.add(i + ". " + element.html());
			vos.add(i + ". " + element.html().replace("data-lazy", ""));
		}
		return vos;
	}
	
	// 크롤링(jsoup) 처리6...
	@ResponseBody
	@RequestMapping(value = "/crawling/jsoup6", method = RequestMethod.POST)
	public List<CrawlingVo> jsoup6Post(String url, String selector) throws IOException {
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		
		Elements elements = null;
		String[] selectors = selector.split("/");
		
		elements = document.select(selectors[0]);
		ArrayList<String> titleVos = new ArrayList<String>();
		for(Element element : elements) {
			titleVos.add(element.html());
		}
		
		elements = document.select(selectors[1]);
		ArrayList<String> imageVos = new ArrayList<String>();
		for(Element element : elements) {
			imageVos.add(element.html());
		}
		
		elements = document.select(selectors[2]);
		ArrayList<String> broadcastVos = new ArrayList<String>();
		for(Element element : elements) {
			broadcastVos.add(element.html());
		}
		
		List<CrawlingVo> vos = new ArrayList<CrawlingVo>();
		CrawlingVo vo = null;
		for(int i=0; i<titleVos.size(); i++) {
			vo = new CrawlingVo();
			vo.setItem1(titleVos.get(i));
			vo.setItem2(imageVos.get(i));
			vo.setItem3(broadcastVos.get(i));
			vos.add(vo);
		}
		return vos;
	}
	
	// 크롤링(selenium) 폼 보기
	@RequestMapping(value = "/crawling/selenium", method = RequestMethod.GET)
	public String seleniumGet() {
		return "study/crawling/selenium";
	}
	
	// 크롤링(selenium) 처리하기
	@ResponseBody
	@RequestMapping(value = "/crawling/selenium1", method = RequestMethod.POST)
	public String selenium1Post(HttpServletRequest request, String url, String searchString) throws IOException {
		// 외부 드라이버를 다운받아서 '/resources/crawling'폴더에 넣어두었다면?
//		String realPath = request.getSession().getServletContext().getRealPath("/resources/crawling/");
//		System.setProperty("webdriver.chrome.driver", realPath + "chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebDriverManager.chromedriver().setup();
		
		driver.get(url);
		
		WebElement btnSearch = driver.findElement(By.name("q"));
		btnSearch.sendKeys(searchString);
		btnSearch.sendKeys(Keys.ENTER);
		
		//driver.close();
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/crawling/selenium2", method = RequestMethod.POST)
	public List<Map<String, Object>> selenium2Post(HttpServletRequest request, String url, String searchString) throws IOException {
		List<Map<String, Object>> vos = new ArrayList<>();
		
		WebDriver driver = new ChromeDriver();
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebDriverManager.chromedriver().setup();
		
		driver.get("http://www.cgv.co.kr/movies/?lt=1&ft=0");
		
		WebElement btnMore = driver.findElement(By.id("chk_nowshow"));
		btnMore.click();
		
		try { Thread.sleep(1000); } catch (Exception e) {}
		
		btnMore = driver.findElement(By.className("link-more"));
		btnMore.click();
		
		try { Thread.sleep(2000); } catch (Exception e) {}
		
		List<WebElement> elements = driver.findElements(By.cssSelector("div.sect-movie-chart ol li"));
		//int i=0;
		for(WebElement element : elements) {
			//i++;
			Map<String, Object> map = new HashMap<String, Object>();
			String link = element.findElement(By.tagName("a")).getAttribute("href");
			String title = "<a href='"+link+"' target='_blank'>" + element.findElement(By.className("title")).getText() + "</a>";
			String image = "<img src='"+ element.findElement(By.tagName("img")).getAttribute("src") + "' width='200px' />";
			String percent = element.findElement(By.className("percent")).getText();
			//String percent = element.findElement(By.xpath("//*[@id=\"contents\"]/div[1]/div[3]/ol[1]/li[1]/div[2]/div/div/span[2]")).getText();
			
			System.out.println("title : " + title);
			System.out.println("image : " + image);
			System.out.println("percent : " + percent);
			
			map.put("title", title);
			map.put("image", image);
			map.put("percent", percent);
			
			vos.add(map);
		}
		// DB저장처리
		
		driver.close();
		return vos;
	}
}
