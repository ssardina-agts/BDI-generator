package rmit.agent.generation.templates.goal;

import java.io.Serializable;

import rmit.agent.generation.writer.JackCodeUtils;
/**
 * Still very simple!
 * 
 * @author s3273631
 *
 */
public class PostingMethodTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final PostingType postingType;
	
	public PostingMethodTemplate() {
		this(JackCodeUtils.DEFAULT_POST_METHOD_NAME, PostingType.POSTED_AS);
	}
	
	public PostingMethodTemplate(String name, PostingType method) {
		this.name = name;
		this.postingType = method;
		if (postingType.equals(PostingType.POSTED_WHEN))
			throw new UnsupportedOperationException(postingType + " not supported yet!");
	}
	
	public String getName() {
		return name;
	}
	
	public PostingType getPostingType() {
		return postingType;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (o instanceof PostingMethodTemplate) {
			PostingMethodTemplate pmt = (PostingMethodTemplate) o;
			return name.equals(pmt.name) && postingType.equals(pmt.postingType);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() | postingType.hashCode();
	}
	
}
