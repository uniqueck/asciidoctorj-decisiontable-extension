package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.core.Commit;

public class ActionOccurrence extends AbstractOccurrence {
	private Action action;
	
	public ActionOccurrence(
			@Attribute(name = "uId") 
			String uid,
			
			@Element(name = "Symbol") 
			Symbol symbol, 
			
			@ElementList(entry = "Title", inline = true, required = true)
			List<Title> title,
			
			@Element(name = "Text", required = false)
			Text text,
			
			@ElementList(name = "SourceCode", required = false, inline = true) 
			List<SourceCode> sourceCodes,
			
			@Path(value = "UrlsOut") 
			@ElementList(entry = "Url", required = false, inline = true)
			List<Url> urls
			) {
		super(uid, symbol, title, text, sourceCodes, urls);
		setAction(null);
	}

	public Action getAction() {
		return action;
	}

	protected void setAction(Action action) {
		this.action = action;
	}

	@Commit
	public void commit(Map<String, Object> session) {
		session.put(getUId(), this);
	}
}
