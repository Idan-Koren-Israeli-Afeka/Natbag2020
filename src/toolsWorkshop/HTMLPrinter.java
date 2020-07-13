package toolsWorkshop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import toolsWorkshop.Flight.FlightType;

public class HTMLPrinter {
	
	final static String HTML_FILE_NAME = "Natbag2020.html";
	
	private static HTMLPrinter single_instance = null;
	
	
	private HTMLPrinter() {
		
	}
	
	public static HTMLPrinter get_instance() {
		if(single_instance==null)
			single_instance = new HTMLPrinter();
		return single_instance;
	}
	
	
	public void printPage(ArrayList<Flight> flightsToPrint) {
		

		try  {
			File file=new File(HTML_FILE_NAME); 
			FileReader fr=new FileReader(file); 
			BufferedReader br=new BufferedReader(fr);
			StringBuffer sb=new StringBuffer();  
			String line;  
			while((line=br.readLine())!=null) {  
				sb.append(line); 
				sb.append("\n");  
			}  
			fr.close();
			System.out.println(sb.toString()); ///Printing whole html file line by line 
		}  
		catch(IOException e)  
		{  
			e.printStackTrace();  
		}

		
		for(Flight f : flightsToPrint)
			printTableRow(f);
		
		System.out.println("</tbody>\r\n" + 
				"</table>\r\n" + 
				"</html>");
		
	}
	
	
	
	private void printTableRow(Flight flightToPrint) {
		
		Airport location;
		if(flightToPrint.getFlightType() == FlightType.Arrival)
			location = flightToPrint.getFromLocation();
		else
			location = flightToPrint.getToLocation();
		
		System.out.println("<tr>\r\n" + 
				"<td style=\"width: 66.2px; text-align: center;\">&nbsp; "+ flightToPrint.getFlightID() +"</td>\r\n" + 
				"<td style=\"width: 76.8px; text-align: center;\">"+ flightToPrint.getFlightType().name() + "</td>\r\n" + 
				"<td style=\"width: 68px; text-align: center;\">"+ flightToPrint.getFlightCompany()+ "</td>\r\n" + 
				"<td style=\"width: 61px; text-align: center;\">"+ location.getCountry() +"</td>\r\n" + 
				"<td style=\"width: 84px; text-align: center;\">" + location.getCity() + "</td>\r\n" + 
				"<td style=\"width: 51px; text-align: center;\">" + location.getName() + "</td>\r\n" + 
				"<td style=\"width: 56px; text-align: center;\">"+ flightToPrint.getDate().toString()+"</td>\r\n" + //Need to fix here
				"<td style=\"width: 55px; text-align: center;\">" +flightToPrint.getDate().toString()+ "</td>\r\n" +  //Need to fix here
				"<td style=\"width: 56px; text-align: center;\">"+flightToPrint.getTerminal()+"</td>\r\n" + 
				"			</tr>");
	}
	
	
	
	
}
