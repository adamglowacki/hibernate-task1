package pl.edu.mimuw.ag291541.tvworld.entity;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.ReportageDTO;

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
	 * Reportage needs a unique subject. Reporters must not write about the same
	 * thing two times. It is a business key.
	 */
	private String subject;
	/**
	 * A transcription of the reportage.
	 */
	private String content;

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
		if (!(obj instanceof Reportage))
			return false;
		Reportage other = (Reportage) obj;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	public void update(ReportageDTO reportage) {
		if (!getSubject().equals(reportage.getSubject()))
			setSubject(reportage.getSubject());
		if (!getContent().equals(reportage.getContent()))
			setContent(reportage.getContent());
	}
}
