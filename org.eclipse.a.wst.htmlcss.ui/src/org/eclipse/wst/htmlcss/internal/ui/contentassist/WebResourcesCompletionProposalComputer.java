/**
 *  Copyright (c) 2013-2014 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.wst.htmlcss.internal.ui.contentassist;

import org.eclipse.wst.htmlcss.internal.ui.WebResourcesTextRegion;
import org.eclipse.wst.htmlcss.internal.ui.DOMHelper;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.ui.contentassist.CompletionProposalInvocationContext;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;
import org.eclipse.wst.xml.ui.internal.contentassist.DefaultXMLCompletionProposalComputer;

/**
 * Completion proposal computer for Web resources inside HTML:
 * 
 * <ul>
 * <li>@class : completion for CSS class name inside @class attribute.</li>
 * <li>@id : completion for CSS ID inside @id attribute.</li>
 * <li>script/@src : completion for JS files inside script/@src attribute.</li>
 * <li>link/@href : completion for CSS files inside link/@href attribute.</li>
 * <li>img/@src : completion for Images files inside img/@src attribute.</li>
 * </ul>
 *
 */
public class WebResourcesCompletionProposalComputer extends
		DefaultXMLCompletionProposalComputer {

	@Override
	protected void addAttributeValueProposals(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context) {
		IDOMNode element = (IDOMNode) contentAssistRequest.getNode();

		IStructuredDocumentRegion documentRegion = contentAssistRequest
				.getDocumentRegion();
		WebResourcesTextRegion attrValueRegion = DOMHelper.getTextRegion(
				documentRegion, context.getInvocationOffset());
		if (attrValueRegion != null) {
			String attrValue = DOMHelper.getAttrValue(documentRegion
					.getText(attrValueRegion.getRegion()));
			switch (attrValueRegion.getType()) {
			case CSS_CLASS_NAME:
			case CSS_ID:
				// Completion for CSS class name or id.
				processCSSCompletion(contentAssistRequest, context, attrValue,
						attrValueRegion);
				break;
			case SCRIPT_SRC:
			case LINK_HREF:
			case IMG_SRC:
				// Completion for js, css, images files
				processFilesCompletion(contentAssistRequest, context,
						attrValue, attrValueRegion);
				break;
			}

		}
	}

	/**
	 * Process completion for CSS class name or id.
	 * 
	 * @param contentAssistRequest
	 * @param context
	 * @param attrValue
	 * @param attrValueRegion
	 */
	private void processCSSCompletion(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context, String attrValue,
			WebResourcesTextRegion attrValueRegion) {
		CSSContentAssistTraverser traverser = new CSSContentAssistTraverser(
				contentAssistRequest, context.getInvocationOffset(), attrValue,
				attrValueRegion.getType());
		traverser.process();
	}

	/**
	 * Process completion for js, css, images files.
	 * 
	 * @param contentAssistRequest
	 * @param context
	 * @param attrValue
	 * @param attrValueRegion
	 */
	private void processFilesCompletion(
			ContentAssistRequest contentAssistRequest,
			CompletionProposalInvocationContext context, String attrValue,
			WebResourcesTextRegion attrValueRegion) {
		// TODO : implements files completion
	}
}