package de.uniqueck.asciidoctorj.extensions.decisiontable.lfet.model;

import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;
import org.simpleframework.xml.core.PersistenceException;
import org.simpleframework.xml.core.Validate;

@Root
public abstract class AbstractLink<T> {
	@Attribute(name = "link")
	private String link;
	
	private T linkedModel;
	
	protected AbstractLink() {
		this.link = null;
		this.linkedModel = null;
	}
	
	protected String getLink() {
		return link;
	}
	
	protected void setLink(String link) {
		this.link = link;
	}
	
	public T getLinkedModel() {
		return linkedModel;
	}

	protected void setLinkedModel(T linkedModel) {
		this.linkedModel = linkedModel;
	}
	
	@Validate
	public void validate(Map<String,Object> session) throws PersistenceException {
		if (session.isEmpty()) {
			throw new PersistenceException("Map must not be empty");
		}
	}
	
	@Commit
	@SuppressWarnings("unchecked")
	public void commit(Map<String, Object> session) {
		Object condition = session.get(getLink());
		setLinkedModel((T) condition);
	}

	public abstract boolean isOccurencesLink();
}
