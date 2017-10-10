package com.dex.services;

import java.io.Serializable;
import java.util.Comparator;

public class Thinger implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	public Thinger() {
	}
	public Thinger(String name) {
		this.setName(name);
	}
	public Thinger(String id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Thinger withId(String id) {
		this.setId(id);
		return this;
	}
	public Thinger withName(String name) {
		this.setName(name);
		return this;
	}
	
	public static class NameComparator implements Comparator<Thinger> {

		@Override
		public int compare(Thinger o1, Thinger o2) {
			int result = 0;
			
			if (o1 != null && o1.name != null && o2 != null) {
				result = o1.name.compareTo(o2.name);
			}
			
			return result;
		}
	}
}