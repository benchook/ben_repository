package com.vnt.aws.restservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.util.IOUtils;
import com.vnt.aws.faceds.CloudWatchFacade;
import com.vnt.aws.faceds.EC2Facade;
import com.vnt.aws.models.Point;
import com.vnt.aws.utils.Utils;

@RestController
public class Controller {
	private static final String INDEX_PATH = "/index.html";
	private static String AWS_ACCESS_ID = "MANDATORY";
	private static String SECRET_ACCESS_KEY = "MANDATORY";
	
	@GetMapping("/entry")
	public String entry() {
		try(InputStream is = getClass().getResourceAsStream(INDEX_PATH)) {
			return IOUtils.toString(is);
		} catch (IOException e) {
			return e.getMessage();
		}
	}
	
	@GetMapping("/cpu")
	public List<Point> cpu(@RequestParam(value = "time_period") String timePeriod,
							@RequestParam(value = "ip") String ip,
							@RequestParam(value = "period_dd") String periodDD) {

		EC2Facade ec2 = new EC2Facade(AWS_ACCESS_ID, SECRET_ACCESS_KEY);
		String incidentID = ec2.getIncidentID(ip);
		
		CloudWatchFacade cw = new CloudWatchFacade(AWS_ACCESS_ID, SECRET_ACCESS_KEY);
        List<Datapoint> dataPoints = cw.getCPUUtilization(incidentID, Integer.parseInt(timePeriod), Utils.getHoursAgo(periodDD));
        
        List<Point> points = Utils.DP2Points(dataPoints);
        Collections.sort(points);
        
		return points;
	}
}
