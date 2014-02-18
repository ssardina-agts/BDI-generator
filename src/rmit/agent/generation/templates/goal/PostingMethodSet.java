package rmit.agent.generation.templates.goal;

import rmit.utils.collections.ImmutableSet;

public class PostingMethodSet extends ImmutableSet<PostingMethodTemplate> {

	private static final long serialVersionUID = 1L;

	public PostingMethodSet(Iterable<PostingMethodTemplate> iterable) {
		super(PostingMethodTemplate.class, iterable);
	}
	
	public PostingMethodSet(PostingMethodTemplate ... array) {
		super(PostingMethodTemplate.class, array);
	}

}
