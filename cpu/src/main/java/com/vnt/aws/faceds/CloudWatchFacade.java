package com.vnt.aws.faceds;

import java.util.Date;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;

public class CloudWatchFacade {
	private AmazonCloudWatch cw;
	
	public CloudWatchFacade(String awsAccessID, String secretAccessKey) {
		this.cw = AmazonCloudWatchClient.builder()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessID, secretAccessKey)))
				.withRegion(Regions.US_EAST_2)
				.build();
	}
	
	public List<Datapoint> getCPUUtilization(String incidentID, int period, int lastHours) {
        long offsetInMilliseconds = 1000 * 60 * 60 * lastHours ;

        Dimension dimension = new Dimension()
                .withName("InstanceId")
                .withValue(incidentID);

        GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
                .withStartTime(new Date(new Date().getTime() - offsetInMilliseconds)).withNamespace("AWS/EC2")
                .withPeriod(period)
                .withMetricName("CPUUtilization").withStatistics("Average", "Maximum", "Minimum").withEndTime(new Date())
                .withDimensions(dimension);

        GetMetricStatisticsResult getMetricStatisticsResult = cw.getMetricStatistics(request);

        System.out.println("request " + request.toString());
        System.out.println("label : " + getMetricStatisticsResult.getLabel());
        System.out.println("DataPoint Size : " + getMetricStatisticsResult.getDatapoints().size());

        List<Datapoint> dataPoints = getMetricStatisticsResult.getDatapoints();
        return dataPoints;
	}
}
