package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import java.util.List;

import de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.puml.activity.Language;
import lombok.Getter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root
public abstract class AbstractOccurrence {
	@Attribute(name = "uId")
	private String uId;

	@Element(name = "Symbol")
	private Symbol symbol;

	@Getter
	@ElementList(entry = "Title", inline = true, required = true)
	private List<Title> title;
	
	@Element(name = "Text", required = false)
	private Text text;
		
	@ElementList(name = "SourceCode", required = false, inline = true)
	private List<SourceCode> sourceCodes;
	
	@Path(value = "UrlsOut") 
	@ElementList(entry = "Url", required = false, inline = true) 
	private List<Url> urls;

	protected AbstractOccurrence(String uId, Symbol symbol, List<Title> title, Text text, List<SourceCode> sourceCodes, List<Url> urls) {
		this.uId = uId;
		this.symbol = symbol;
		this.title = title;
		this.text = text;
		this.sourceCodes = sourceCodes;
		this.urls = urls;
	}
	
	public String getUId() {
		return uId;
	}
	
	public Symbol getSymbol() {
		return symbol;
	}
	
	public Title getTitle(Language language) {
		return title.stream().filter(t -> t.getLanguage().equals(language.name())).findFirst().orElse(null);
	}
	
	public Text getText() {
		return text;
	}

	public List<SourceCode> getSourceCodes() {
		return sourceCodes;
	}
	
	public List<Url> getUrls() {
		return urls;
	}
}
