package br.com.auditor.controller;

import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import br.com.auditor.domain.Quee;
import br.com.auditor.domain.Ticket;
import br.com.auditor.extractor.ExcelDataExtractor;
import br.com.auditor.extractor.ExcelDataQueeExtractor;

@Controller
@RequestMapping("/home")
public class MainController {

	@RequestMapping(method=RequestMethod.GET)
	public String get(ModelMap model) {
		
		List<Quee> quees= ExcelDataQueeExtractor.extractQueeListFromCSVFile("resources/reports/quee.production.csv");
		List<Ticket> tickets= ExcelDataExtractor.extractTicketListFromCSVData("resources/reports/report.csv", quees);
		
		model.addAttribute("tickets", tickets);
		return "home";
	}


}
