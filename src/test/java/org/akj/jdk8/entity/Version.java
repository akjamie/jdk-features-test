package org.akj.jdk8.entity;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class Version {
	private String id;
	
	private String name;
	
	private String number;
	
	private Date createDate;
	
	private String releaseNotes;
	
	@Singular
	List<String> lists;
	
}
