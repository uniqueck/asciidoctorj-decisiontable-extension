package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import java.util.List;
import java.util.Optional;

import de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.puml.activity.Language;
import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root
public abstract class AbstractRulePart<T extends AbstractOccurrence> {
	@Attribute(name = "uId")
	private String uId;

	@Getter
	@Setter
	@ElementList(entry = "Title", inline =  true, required =  true)
	private List<Title> title;

	private List<T> occurrences;

	@ElementList(name = "SourceCode", required = false, inline = true)
	private List<SourceCode> sourceCodes;

	@Element(name = "Text", required = false)
	private Text text;
	
	@Path(value = "UrlsOut") 
	@ElementList(entry = "Url", required = false, inline = true) 
	private List<Url> urls;

	protected AbstractRulePart(String uid, List<Title> title, Text text, List<SourceCode> sourceCodes, List<T> occurrences, List<Url> urls) {
		this.uId = uid;
		this.title = title;
		this.text = text;
		this.sourceCodes = sourceCodes;
		this.occurrences = occurrences;
		this.urls = urls;
	}

	public String getUId() {
		return uId;
	}

	public void setUId(String uId) {
		this.uId = uId;
	}

	public Title getTitle(Language language) {
		Optional<Title> optionalTitle = getTitle().stream().filter(t -> Language.getEnum(t.getLanguage()).equals(language)).findFirst();
		return optionalTitle.orElse(null);
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public List<SourceCode> getSourceCodes() {
		return sourceCodes;
	}
	
	public void setSourceCodes(List<SourceCode> sourceCodes) {
		this.sourceCodes = sourceCodes;
	}
	
	public List<T> getOccurrences() {
		return occurrences;
	}
	
	public void setOccurrences(List<T> occurrences) {
		this.occurrences = occurrences;
	}
	
	public List<Url> getUrls() {
		return urls;
	}
	
	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
}
