package com.uniqueck.asciidoctorj.lfet.model;

import org.simpleframework.xml.Attribute;

import java.util.Optional;

public class Url {
	@Attribute(name = "title")
	private String title;
	
	@Attribute(name = "url")
	private String link;
	
	@Attribute(name="executable", required = false)
	private Boolean executable;
	
	public Url(@Attribute(name = "title") String title, @Attribute(name = "url") String link, @Attribute(name="executable", required = false) Boolean executable) {
		this.title = title;
		this.link = link;
		this.executable = executable;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLink() {
		return link;
	}
	
	public boolean isExecutable() {
		return Optional.ofNullable(executable).orElse(false);
	}
}
