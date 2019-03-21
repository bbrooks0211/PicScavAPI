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
	 * @param Timestamp 
	 * @param long
	 * @return Timestamp
	 */
	public static Timestamp addHoursToTimestamp(Timestamp t, long hours) {
		Timestamp newTimestamp = new Timestamp(new Date().getTime());
		newTimestamp.setTime(t.getTime() + ( hours * 3600000 ));
		return newTimestamp;
	}
}

/*
Copyright 2019, Brendan Brooks.  

This file is part of PicScav.

PicScav is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PicScav is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PicScav.  If not, see <https://www.gnu.org/licenses/>.
*/