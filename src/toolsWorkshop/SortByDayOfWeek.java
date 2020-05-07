package toolsWorkshop;

import java.util.Comparator;

public class SortByDayOfWeek implements Comparator<Flight> {

	@Override
	public int compare(Flight o1, Flight o2) {
		return o2.getDate().getDay() - o1.getDate().getDay();
	}
}