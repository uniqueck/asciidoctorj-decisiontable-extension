package com.uniqueck.asciidoctorj.lfet.model;

import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

@Root
public class Action extends AbstractRulePart<ActionOccurrence> {
	public Action(
			@Attribute(name = "uId") 
			String uid, 
			
			@ElementList(entry = "Title", inline = true, required = true)
			List<Title> title,
			
			@Element(name = "Text", required = false) 
			Text text,
			
			@ElementList(entry = "SourceCode", required = false, inline = true) 
			List<SourceCode> sourceCodes,
			
			@Path(value = "ActionOccurrences") 
			@ElementList(entry = "ActionOccurrence", required = false, inline = true) 
			List<ActionOccurrence> occurences,
			
			@Path(value = "UrlsOut") 
			@ElementList(entry = "Url", required = false, inline = true)
			List<Url> urls
		) {
		super(uid, title, text, sourceCodes, occurences, urls);
	}

	@Override
	@Path("ActionOccurrences")
	@ElementList(entry = "ActionOccurrence", required = false, inline = true)
	public List<ActionOccurrence> getOccurrences() {
		return super.getOccurrences();
	}
	
	@Commit
	public void commit(Map<String, Object> session) {
		session.put(getUId(), this);
		if (getOccurrences() != null) {
			for (ActionOccurrence eachOcc : getOccurrences()) {
				eachOcc.setAction(this);
			}
		}
	}
}
