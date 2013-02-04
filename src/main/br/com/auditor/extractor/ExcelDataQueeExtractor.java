package br.com.auditor.extractor;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import br.com.auditor.domain.Quee;

public class ExcelDataQueeExtractor {
	
	public static List<Quee> extractQueeListFromCSVFile(String filepath) {
		List<Quee> quees= new ArrayList<Quee>();
		
		try {
			CSVReader reader= new CSVReader(new FileReader(filepath));
			String[] nextLine, values;
			
			while((nextLine= reader.readNext()) != null) {
				values= nextLine[0].split(";");
				quees.add(new Quee(values[0],values[1]));
			}
			
			reader.close();
			
		} catch(Exception e){
			System.out.println("Something wasnt in our plans with the quees..."+e.getMessage());
			System.out.println("Caused by: "+e.getCause());
		}
		
		return quees;
	}
}
