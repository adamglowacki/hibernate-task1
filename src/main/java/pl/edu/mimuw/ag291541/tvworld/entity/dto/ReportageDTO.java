package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.Reportage;

public class ReportageDTO implements Comparable<ReportageDTO> {
	private final Long id;
	private String subject;
	private String content;

	public ReportageDTO(Reportage reportage) {
		this.id = reportage.getId();
		this.subject = reportage.getSubject();
		this.content = reportage.getContent();
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

	public Long getId() {
		return id;
	}

	@Override
	public int compareTo(ReportageDTO o) {
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
		if (!(obj instanceof ReportageDTO))
			return false;
		ReportageDTO other = (ReportageDTO) obj;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
}
