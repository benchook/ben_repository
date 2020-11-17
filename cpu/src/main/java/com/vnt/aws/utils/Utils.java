package com.vnt.aws.utils;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.vnt.aws.models.Point;

public class Utils {
	public static List<Point> DP2Points(List<Datapoint> datapoints) {
		List<Point> points = new ArrayList<>();
		for (Datapoint dp : datapoints) 
			points.add(new Point(dp.getTimestamp(), dp.getAverage()));
		return points;
	}
	
	public static int getHoursAgo(String lastTime) {
		if (lastTime.equalsIgnoreCase("lasthour"))
			return 1;
		if (lastTime.equalsIgnoreCase("lastday"))
			return 24;
		if (lastTime.equalsIgnoreCase("lastweek")) 
			return 24*7;
		if (lastTime.equalsIgnoreCase("lastmonth")) 
			return 24*31;
		else 
			return 24;
	}
}
