package br.com.auditor.extractor;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import br.com.auditor.domain.EQuee;
import br.com.auditor.domain.EStates;
import br.com.auditor.domain.Ticket;
import br.com.auditor.domain.Update;

import au.com.bytecode.opencsv.CSVReader;

public class ExcelDataExtractorTest {

	/**
	 * http://opencsv.sourceforge.net/
	 */
	@Test
	public void testExtractTicketListFromCSVData() {
		List<Ticket> tickets= ExcelDataExtractor.extractTicketListFromCSVData("WebContent/report.csv");
		assertTrue(tickets.size()>0);
	}
	
	@Test
	public void testIfExcelHeaderIsTicketInformationLine() {
		String line = new String("");
		assertFalse(ExcelDataExtractor.isTicketInformationLine(line));
	}
	
	@Test
	public void testIfTicketLineInformationIsTicketInformationLine() {
		String line = new String("");
		assertTrue(ExcelDataExtractor.isTicketInformationLine(line));
	}
	
	@Test
	public void testObtainingOpenTicketDataFromCSVLine() {
		String line= "";
		Ticket ticket= ExcelDataExtractor.openTicketByLineInformation(line);
		assertTrue(EStates.OPEN.compareTo(ticket.getCurrentState().getState())==0);
	}
	
	@Test
	public void testObtainingClosedTicketDataFromCSVLine() {
		String line= "";
		Ticket ticket= ExcelDataExtractor.openTicketByLineInformation(line);
		assertTrue(EStates.CLOSED.compareTo(ticket.getCurrentState().getState())==0);
	}
}
