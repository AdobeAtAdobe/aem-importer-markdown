package io.adobe.udp.markdownimporter.mappings;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.vladsch.flexmark.ast.Node;

public  class MarkdownMappings {
	
	public static final Map<Class, MarkdownNodeMapper> markdownMapping = new HashMap<Class, MarkdownNodeMapper>();
	private static final MarkdownNodeMapper defaultMapper = new ParagraphsMergingMapper();
	
	public static void configure(Map<String, String> mappings) {
		for(Map.Entry<String, String> entry : mappings.entrySet()) {
			try {
				Constructor<MarkdownNodeMapper> mappingConstructor = (Constructor<MarkdownNodeMapper>) Class.forName(entry.getValue()).getConstructor();
				MarkdownMappings.markdownMapping.put(Class.forName(entry.getKey()), mappingConstructor.newInstance());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static MarkdownNodeMapper getMarkdownNodeMapper(com.vladsch.flexmark.ast.Node node) {
		MarkdownNodeMapper mappedMapper = markdownMapping.get(node.getClass());
		if( mappedMapper != null) {
			return mappedMapper;
		}
		return MarkdownMappings.defaultMapper;
	}
	
	public static  boolean hasMapping(Node node) {
		return markdownMapping.get(node.getClass()) != null;
	}


}
