package brooks.api.utility;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Utility class for special time functions
 * @author Brendan Brooks
 *
 */
public final class TimeUtility {
	
	/**
	 * Adds x amount of hours to a given Timestamp
	 * @param t 
	 * @param hours
	 * @return Timestamp
	 */
	public static Timestamp addHoursToTimestamp(Timestamp t, long hours) {
		Timestamp newTimestamp = new Timestamp(new Date().getTime());
		newTimestamp.setTime(t.getTime() + ( hours * 3600000 ));
		return newTimestamp;
	}
}
