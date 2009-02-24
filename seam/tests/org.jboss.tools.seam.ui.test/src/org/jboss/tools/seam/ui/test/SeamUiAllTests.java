/*******************************************************************************
 * Copyright (c) 2007 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 

package org.jboss.tools.seam.ui.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.tools.seam.ui.test.ca.SeamELContentAssistJbide1645Test;
import org.jboss.tools.seam.ui.test.ca.SeamELContentAssistJbide1676Test;
import org.jboss.tools.seam.ui.test.ca.SeamELContentAssistTest;
import org.jboss.tools.seam.ui.test.hyperlink.SeamViewHyperlinkPartitionerTest;
import org.jboss.tools.seam.ui.test.preferences.SeamPreferencesPageTest;
import org.jboss.tools.seam.ui.test.preferences.SeamSettingsPreferencesPageTest;
import org.jboss.tools.seam.ui.test.view.SeamComponentsViewAllTests;
import org.jboss.tools.seam.ui.test.wizard.OpenSeamComponentDialogTest;
import org.jboss.tools.seam.ui.test.wizard.PackageNamesTest;
import org.jboss.tools.seam.ui.test.wizard.Seam12EARNewOperationTest;
import org.jboss.tools.seam.ui.test.wizard.Seam12WARNewOperationTest;
import org.jboss.tools.seam.ui.test.wizard.Seam12XOperationsTestSuite121EAP;
import org.jboss.tools.seam.ui.test.wizard.Seam20EARNewOperationTest;
import org.jboss.tools.seam.ui.test.wizard.Seam20WARNewOperationTest;
import org.jboss.tools.seam.ui.test.wizard.Seam20XOperationsTestSuite201GA;
import org.jboss.tools.seam.ui.test.wizard.SeamActionNewWizardTest;
import org.jboss.tools.seam.ui.test.wizard.SeamFormNewWizardTest;
import org.jboss.tools.seam.ui.test.wizard.SeamProjectNewWizardTest;
import org.jboss.tools.test.util.ProjectImportTestSetup;

/**
 * @author eskimo
 *
 */
public class SeamUiAllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Seam UI tests");

		suite.addTestSuite(OpenSeamComponentDialogTest.class);
		suite.addTest(SeamComponentsViewAllTests.suite());
		suite.addTest(SeamProjectNewWizardTest.suite());

		suite.addTest(new ProjectImportTestSetup(new TestSuite(SeamActionNewWizardTest.class), "org.jboss.tools.seam.core.test", new String[]{"projects/Test1-ear", "projects/Test1-ejb", "projects/Test1"}, new String[]{"Test1-ear", "Test1-ejb", "Test1"}));

		suite.addTestSuite(SeamFormNewWizardTest.class);
		suite.addTestSuite(SeamPreferencesPageTest.class);		
		suite.addTestSuite(SeamViewHyperlinkPartitionerTest.class);
		suite.addTestSuite(SeamELContentAssistTest.class);
		suite.addTestSuite(SeamELContentAssistJbide1676Test.class);
		suite.addTestSuite(SeamELContentAssistJbide1645Test.class);
		suite.addTest(new ProjectImportTestSetup(new TestSuite(SeamSettingsPreferencesPageTest.class), "org.jboss.tools.seam.ui.test", "projects/TestSeamSettingsPreferencesPage", "TestSeamSettingsPreferencesPage"));
		suite.addTestSuite(PackageNamesTest.class);
		return suite;
	}
}