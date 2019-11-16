package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Attribute;

public class Url {
	@Attribute(name = "title")
	private String title;
	
	@Attribute(name = "url")
	private String url;
	
	@Attribute(name="executable", required = false)
	private Boolean executable;
	
	public Url(@Attribute(name = "title") String title, @Attribute(name = "url") String url, @Attribute(name="executable", required = false) Boolean executable) {
		this.title = title;
		this.url = url;
		this.executable = executable;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getUrl() {
		return url;
	}
	
	public boolean isExecutable() {
		return executable==null ? false : executable;
	}
}
