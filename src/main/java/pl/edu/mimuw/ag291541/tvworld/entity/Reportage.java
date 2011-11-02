package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a single part of the news TV production.
 * 
 * @author adas
 * 
 */
public class Reportage {
	private Long id;
	/**
	 * All the authors of the reportage.
	 */
	private Set<Reporter> reporters = new TreeSet<Reporter>();
	private String subject;
	/**
	 * A transcription of the reportage.
	 */
	private String content;
	/**
	 * All the news TV productions that include this reportage.
	 */
	private Set<News> occurrences = new TreeSet<News>();

	public Reportage() {

	}

	public Reportage(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Reporter> getReporters() {
		return reporters;
	}

	public void setReporters(Set<Reporter> reporters) {
		this.reporters = reporters;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<News> getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(Set<News> occurrences) {
		this.occurrences = occurrences;
	}
}
