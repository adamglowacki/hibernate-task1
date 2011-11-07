package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Represents a single part of the news TV production.
 * 
 * @author adas
 * 
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Reportage implements Comparable<Reportage> {
	private Long id;
	/**
	 * All the authors of the reportage.
	 */
	private Set<Reporter> reporters = new TreeSet<Reporter>();
	/**
	 * Reportage needs a unique subject. Reporters must not write about the same
	 * thing two times. It is a business key.
	 */
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

	@Override
	public int compareTo(Reportage o) {
		return getSubject().compareTo(o.getSubject());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reportage other = (Reportage) obj;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
}
