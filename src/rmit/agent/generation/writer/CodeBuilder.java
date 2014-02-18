package rmit.agent.generation.writer;

import rmit.agent.generation.JackCodeUtils;

public class CodeBuilder {

	private StringBuilder sb;
	private int blockDepth;
	
	public CodeBuilder() {
		sb = new StringBuilder();
		blockDepth = 0;
	}

	public void clear() {
		sb = new StringBuilder();
	}
	
	public void writeLine(Object o) {
		writeLine(o.toString());
	}
	
	public void writeLine(String s) {
		writeTabs();
		s = s.replace(JackCodeUtils.CODE_TERM, "").trim();
		sb.append(s + JackCodeUtils.CODE_TERM + JackCodeUtils.NEW_LINE);
		
	}
	
	public void writeLine(String pre, String ... commaDelimited) {
		writeTabs();
		pre = pre.replace(JackCodeUtils.CODE_TERM, "").trim();
		sb.append(pre + JackCodeUtils.OPEN_PAREN);
		for (int i = 0; i < commaDelimited.length; i++) {
			sb.append(commaDelimited[i]);
			if (i < commaDelimited.length-1)
				sb.append(", ");
		}
		sb.append(JackCodeUtils.CLOSE_PAREN + JackCodeUtils.CODE_TERM + JackCodeUtils.NEW_LINE);
	}
	

	
	public void openBlock(Object o) {
		openBlock(o.toString());
	}
	
	public void openBlock(String s) {
		writeTabs();
		s = s.replace(JackCodeUtils.OPEN_BRACE, "").trim();
		sb.append(s + " " + JackCodeUtils.OPEN_BRACE + JackCodeUtils.NEW_LINE);
		blockDepth++;
	}
	
	public void openBlock(String pre, String ... commaDelimited) {
		writeTabs();
		sb.append(pre + JackCodeUtils.OPEN_PAREN);
		for (int i = 0; i < commaDelimited.length; i++) {
			sb.append(commaDelimited[i]);
			if (i < commaDelimited.length-1)
				sb.append(", ");
		}
		sb.append(JackCodeUtils.CLOSE_PAREN + " " + JackCodeUtils.OPEN_BRACE + JackCodeUtils.NEW_LINE);
		blockDepth++;
		
	}
	
	public void closeBlock() {
		blockDepth--;
		writeTabs();
		sb.append(JackCodeUtils.CLOSE_BRACE + JackCodeUtils.NEW_LINE);
	}
	
	public void writeComment(Object o) {
		writeComment(o.toString());
	}
	
	public void writeComment(String comment) {
		sb.append(JackCodeUtils.COMMENT_PREFIX + comment.trim() + JackCodeUtils.NEW_LINE);
	}
	
	public void newLine() {
		sb.append(JackCodeUtils.NEW_LINE);
	}
	
	private void writeTabs() {
		for (int i = 0; i < blockDepth; i++)
			sb.append(JackCodeUtils.TAB);
	}
	
	public String getCodeString() {
		if (blockDepth != 0)
			throw new IllegalStateException("BlockDepth = " + blockDepth);
		return sb.toString();
	}
	
	public static String formatCSV(String pre, String[] commaDelimited) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(pre + JackCodeUtils.OPEN_PAREN);
		for (int i = 0; i < commaDelimited.length; i++) {
			strBuilder.append(commaDelimited[i]);
			if (i < commaDelimited.length-1)
				strBuilder.append(", ");
		}
		strBuilder.append(JackCodeUtils.CLOSE_PAREN);
		return strBuilder.toString();
	}
	
}
