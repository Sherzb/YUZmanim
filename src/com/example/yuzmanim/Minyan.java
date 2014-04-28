package com.example.yuzmanim;

public class Minyan
{
	private String minyanTime;
	private String minyanLocation;
	
	public Minyan(String time, String location) {
		minyanTime = time;
		minyanLocation = location;
	}
	
	public String getTime() {
		return minyanTime;
	}
	public void setTime(String minyanTime) {
		this.minyanTime = minyanTime;
	}
	public String getLocation() {
		return minyanLocation;
	}
	public void setLocation(String minyanLocation) {
		this.minyanLocation = minyanLocation;
	}
}
