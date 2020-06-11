package toolsWorkshop;

import java.util.Comparator;

public class SortByCity implements Comparator<Flight> {
	
	@Override
	public int compare(Flight o1, Flight o2) {
		if(o1.getToLocation().getCity().equals(o2.getToLocation().getCity()))
			return o1.getDate().compareTo(o2.getDate());
		return o1.getToLocation().getCity().compareTo(o2.getToLocation().getCity());
	}

}
