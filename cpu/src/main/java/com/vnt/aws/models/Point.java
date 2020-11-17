package com.vnt.aws.models;

import java.util.Date;

public class Point implements Comparable<Point> {
	private Date x;
	private Double y;
	
	public Point(Date x, Double y) {
		this.x = x;
		this.y = y;
	}
	public Date getX() {
		return x;
	}
	public void setX(Date x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	@Override
	public int compareTo(Point o) {
		if (this.x.after(o.x))
			return 1;
		else 
			return -1;
	}
}
