package com.vnt.aws.faceds;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

public class EC2Facade {
	private AmazonEC2 ec2;
	
	public EC2Facade(String awsAccessID, String secretAccessKey) {
		this.ec2 = AmazonEC2Client.builder()
				.withRegion(Regions.US_EAST_2)
				.withCredentials(new AWSStaticCredentialsProvider((AWSCredentials) new BasicAWSCredentials(awsAccessID, secretAccessKey)))
				.build();
	}
	
	public String getIncidentID(String IP) {
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		DescribeInstancesResult response = ec2.describeInstances(request);
		for (Reservation reservation : response.getReservations()) 
			for (Instance instance : reservation.getInstances()) 
				if (instance.getPrivateIpAddress().equalsIgnoreCase(IP))
					return instance.getInstanceId();
		return null;
	}
}
