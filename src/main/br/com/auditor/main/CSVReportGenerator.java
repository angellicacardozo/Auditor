package br.com.auditor.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import br.com.auditor.domain.Quee;
import br.com.auditor.domain.Ticket;
import br.com.auditor.extractor.ExcelDataExtractor;
import br.com.auditor.extractor.ExcelDataQueeExtractor;

import au.com.bytecode.opencsv.CSVWriter;

public class CSVReportGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			BufferedWriter out= new BufferedWriter(new FileWriter("WebContent/csvResult.csv"));
			
			char separator= ';';
			
			CSVWriter writer= new CSVWriter(out, separator);
			
			List<Quee> quees= ExcelDataQueeExtractor.extractQueeListFromCSVFile("WebContent/quee.production.csv");
			List<Ticket> tickets= ExcelDataExtractor.extractTicketListFromCSVData("WebContent/report.csv", quees);
		
			writer.writeNext(new String[]{"Numero do chamado","Titulo", "Total de dias em aberto", "Estado", "Data de abertura","Primeira resposta","Fila"});
			
			String[] values;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Ticket ticket : tickets) {
			    String firstResponse = sdf.format(ticket.getFirstResponseDate().getTime());
			    String openDate = sdf.format(ticket.getOpenDate().getTime());
				values= new String[]{ticket.getNumber(),ticket.getTitle(), String.valueOf(ticket.getTotalOpenDays()), ticket.getCurrentState().getState(), openDate, firstResponse, ticket.getCurrentState().getQuee().getName()};
				writer.writeNext(values);
			}
			
			out.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
