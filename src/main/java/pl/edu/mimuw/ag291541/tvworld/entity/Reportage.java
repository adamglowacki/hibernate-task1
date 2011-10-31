package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Collection;

/**
 * Represents a single part of the news TV production.
 * 
 * @author adas
 * 
 */
public class Reportage {
	private Long id;
	private String subject;
	/**
	 * A transcription of the reportage.
	 */
	private String content;
	/**
	 * All the news TV productions that include this reportage.
	 */
	private Collection<News> occurrences;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Collection<News> getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(Collection<News> occurrences) {
		this.occurrences = occurrences;
	}
}
