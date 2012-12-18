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
		String line = new String("TSO-Ticket#;Idade;Título;Criado;Alterado;Hora de Fechamento;Fila;liente;ID. do Cliente;Serviço;Tipo;Data de Decisão;Data vencimentoetc");
		assertFalse(ExcelDataExtractor.isTicketInformationLine(line));
	}
	
	@Test
	public void testIfTicketLineInformationIsTicketInformationLine() {
		String line = new String("2012100510000011;6394649;sem assunto;2012-10-05 10:15:05;2012-10-05 10:48:36;;Triagem;client.mail@yahoo.com.br;client.mail@yahoo.com.br;;default;;;");
		assertTrue(ExcelDataExtractor.isTicketInformationLine(line));
	}
	
	@Test
	public void testObtainingOpenTicketDataFromCSVLine() {
		String line= "2012121810000028;1008;Nome de projetos com acentos são gravados erradamente;2010-12-18 11:15:51;2012-12-18 11:15:51;;Suporte Interno;tso.client.name;Client-Company;Manutenção de Software;Defeito em Funcionalidade;;;";
		
		Ticket ticket= ExcelDataExtractor.openTicketByLineInformation(line);
		assertTrue(EStates.NEW.compareTo(ticket.getCurrentState().getState())==0);
	}
	
}
