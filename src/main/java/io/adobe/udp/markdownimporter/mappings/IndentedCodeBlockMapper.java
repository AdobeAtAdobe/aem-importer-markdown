package io.adobe.udp.markdownimporter.mappings;

import io.adobe.udp.markdownimporter.MarkdownPageData;
import io.adobe.udp.markdownimporter.utils.Constants;

import java.util.HashMap;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.JcrConstants;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

public class IndentedCodeBlockMapper implements MarkdownNodeMapper {
	
	public com.vladsch.flexmark.ast.Node mapToComponen(com.vladsch.flexmark.ast.Node markdownNode,
			MarkdownPageData pageData, Parser parser, HtmlRenderer renderer) throws RepositoryException {
		HashMap<String, String> component = new HashMap<String, String>();
		String elementHtml = renderer.render(markdownNode);
		elementHtml = elementHtml.replaceFirst("<pre><code.*>", "").replace("</code></pre>", "");
		component.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
		component.put("codeBlock", elementHtml);
		component.put(Constants.SLING_RESOURCE_TYPE, "udp/components/content/highlightedcodeblock");
		pageData.getComponents().add(component);
		return markdownNode.getNext();
	}

}
