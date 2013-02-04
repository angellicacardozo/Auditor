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
		String line = new String("TSO-Ticket#;Idade;T√≠tulo;Criado;Alterado;Hora de Fechamento;Fila;liente;ID. do Cliente;Servi√ßo;Tipo;Data de Decis√£o;Data vencimentoetc");
		assertFalse(ExcelDataExtractor.isTicketInformationLine(line));
	}
	
	@Test
	public void testIfTicketLineInformationIsTicketInformationLine() {
		String line = new String("2012100510000011;6394649;sem assunto;2012-10-05 10:15:05;2012-10-05 10:48:36;;Triagem;client.mail@yahoo.com.br;client.mail@yahoo.com.br;;default;;;");
		assertTrue(ExcelDataExtractor.isTicketInformationLine(line));
	}
	
	@Test
	public void testObtainingOpenTicketDataFromCSVLine() {
		String line= "2012121810000028;1008;Nome de projetos com acentos s√£o gravados erradamente;2010-12-18 11:15:51;2012-12-18 11:15:51;;Suporte Interno;tso.client.name;Client-Company;Manuten√ß√£o de Software;Defeito em Funcionalidade;;;";
		Ticket ticket= ExcelDataExtractor.openTicketByLineInformation(line);
		assertTrue(EStates.OPEN.compareTo(ticket.getCurrentState().getState())==0);
	}
	
	@Test
	public void testObtainingClosedTicketDataFromCSVLine() {
		String line= "2012121410000026;349954;Sistema Controle de Projetos;2012-12-14 10:20:05;2012-12-18 11:15:34;2012-12-18 11:15:34;Suporte Interno;tso.marco.rodriguez;Target-Solutions;;default;;;";
		Ticket ticket= ExcelDataExtractor.openTicketByLineInformation(line);
		assertTrue(EStates.CLOSED.compareTo(ticket.getCurrentState().getState())==0);
	}
	
	@Test
	public void testObtainingTicketSUPORTEINTERNOQueeFromCSVLine() {
		String line= "2012121410000026;349954;Sistema Controle de Projetos;2012-12-14 10:20:05;2012-12-18 11:15:34;2012-12-18 11:15:34;Suporte Interno;tso.marco.rodriguez;Target-Solutions;;default;;;";
		Ticket ticket= ExcelDataExtractor.openTicketByLineInformation(line);
		assertTrue(EQuee.SUPORTEINTERNO.compareTo(ticket.getCurrentState().getQuee())==0);
	}
	
	@Test
	public void testObtainingTicketWDTSecondLevelQueeFromCSVLine() {
		String line = new String("2012121310000046;524704;AR 1-4282742 - Erro ao atualizar o DSLAM RJTIJ__-EZI101;2012-12-13 17:10:05;2012-12-19 18:12:12;;WDT::1∫ NÌvel;alu.luciano.feltrin;Alcatel-Lucent;Suporte ‡ OperaÁ„o;Defeito em Funcionalidade;;2013-03-13 15:10:00;");
		Ticket ticket= ExcelDataExtractor.openTicketByLineInformation(line);
		assertTrue(EQuee.WDT.compareTo(ticket.getCurrentState().getQuee())==0);
	}
}
