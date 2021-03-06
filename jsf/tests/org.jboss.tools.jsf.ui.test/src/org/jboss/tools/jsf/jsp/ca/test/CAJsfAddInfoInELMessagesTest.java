/*******************************************************************************
 * Copyright (c) 2011-2012 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.jsf.jsp.ca.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.jboss.tools.common.base.test.contentassist.CATestUtil;
import org.jboss.tools.jst.jsp.contentassist.AutoELContentAssistantProposal;
import org.jboss.tools.jst.jsp.test.ca.ContentAssistantTestCase;
import org.jboss.tools.test.util.JobUtils;
import org.jboss.tools.test.util.ProjectImportTestSetup;

/**
 * The JUnit test case for JBIDE-9910 issue
 * 
 * @author Victor V. Rubezhny
 *
 */
public class CAJsfAddInfoInELMessagesTest extends ContentAssistantTestCase {
	private static final String PROJECT_NAME = "JSF2KickStartWithoutLibs";
	private static final String PAGE_NAME = "WebContent/pages/inputname.xhtml";
	private static final String PREFIXES[] = new String[] {"${ms" , "${msgs.pr"};
	private static final String ADD_INFOS[] = new String[] {
			"<html><body text=\"#000000\" bgcolor=\"#ffffe1\"><b>Base Name:</b> resources<br><br><b>Resource Bundle:</b> /JSF2KickStartWithoutLibs/JavaSource/resources.properties</body></html>", 
			"<html><body text=\"#ffffff\" bgcolor=\"#000000\"><b>Property:</b> prompt<br><b>Base Name:</b> resources<br><br><b>Resource Bundle:</b> /JSF2KickStartWithoutLibs/JavaSource/resources.properties<br><b>Value:</b> Your Name:</body></html>"
		};

	public void setUp() throws Exception {
		project = ProjectImportTestSetup.loadProject(PROJECT_NAME);
	}

	public static Test suite() {
		return new TestSuite(CAForELJavaAndJSTCompareTest.class);
	}

	public void testCAJsfAddInfoInELMessages () {
		for (int i = 0; i < PREFIXES.length; i++) {
			AutoELContentAssistantProposal jstProposals[] = getJSTProposals(PREFIXES[i]);
			assertFalse ("No EL Proposals found in Web page: " + PAGE_NAME, (jstProposals == null || jstProposals.length == 0));
			assertEquals ("Content Assist in returned more than 1 proposal for Web page: " + PAGE_NAME + 
					". Test project and/or data should be verfied/updated.", 1, jstProposals.length);

			for (AutoELContentAssistantProposal proposal : jstProposals) {
				String addInfo = proposal.getAdditionalProposalInfo();

				String addInfoValue = html2Text(addInfo);
				String compareValue = html2Text(ADD_INFOS[i]);
				assertTrue("Additional Info exists but its value is not expected:\nAdd. Info: [" + addInfoValue + "]\nExpected Value: [" + compareValue + "]", compareValue.equalsIgnoreCase(addInfoValue));
			}
		}
	}
	
	String html2Text(String html) {
		StringBuilder sb = new StringBuilder();
		int state = 0;
		for (char ch : html.toCharArray()) {
			switch (state) {
			case (int)'<':
				// Read to null until '>'-char is read
				if (ch != '>')
					continue;
				state = 0;
				break;
			default:
				if (ch == '<') {
					state = '<';
					continue;
				}
				sb.append(ch);
				break;
			}
		}
		return sb.toString();
	}

	AutoELContentAssistantProposal[] getJSTProposals(String prefix) {
		openEditor(PAGE_NAME);
		try {
			String documentContent = document.get();
			int start = (documentContent == null ? -1 : documentContent.indexOf(prefix));
			assertFalse("Required node '" + prefix + "' not found in document", (start == -1));
			int offsetToTest = start + prefix.length();
			
			JobUtils.waitForIdle();
			
			List<ICompletionProposal> res = CATestUtil.collectProposals(contentAssistant, viewer, offsetToTest);
	
			assertTrue("Content Assistant returned no proposals", (res != null && res.size() > 0));

			Set<AutoELContentAssistantProposal> jstProposals = new HashSet<AutoELContentAssistantProposal>();
			for (ICompletionProposal p : res) {
				if (p instanceof AutoELContentAssistantProposal) {
					jstProposals.add((AutoELContentAssistantProposal)p);
				}
			}
			
			return jstProposals.toArray(new AutoELContentAssistantProposal[0]);
		} finally {
			closeEditor();
		}
	}
}
