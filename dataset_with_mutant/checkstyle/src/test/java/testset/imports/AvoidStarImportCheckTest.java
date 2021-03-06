////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package testset.imports;

import static checks.imports.AvoidStarImportCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

//import checkstyle.AbstractModuleTestSupport;
import checkstyle.DefaultConfiguration;
import testset.checkstyle.AbstractModuleTestSupport;
import api.TokenTypes;

import checks.imports.AvoidStarImportCheck;

public class AvoidStarImportCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/avoidstarimport";
    }

    @Test
    public void testDefaultOperation()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AvoidStarImportCheck.class);
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.checks.imports.*"),
            "9: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "10: " + getCheckMessage(MSG_KEY, "java.lang.*"),
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.*"),
        };

        verify(checkConfig, getPath("InputAvoidStarImportDefault.java"),
                expected);
    }

    @Test
    public void testExcludes()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("excludes",
            "java.io,java.lang,javax.swing.WindowConstants.*, javax.swing.WindowConstants");
        // allow the java.io/java.lang,javax.swing.WindowConstants star imports
        final String[] expected2 = {
            "7: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.checks.imports.*"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.*"),
        };
        verify(checkConfig, getPath("InputAvoidStarImportDefault.java"),
                expected2);
    }

    @Test
    public void testAllowClassImports() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("allowClassImports", "true");
        // allow all class star imports
        final String[] expected2 = {
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.*"), };
        verify(checkConfig, getPath("InputAvoidStarImportDefault.java"), expected2);
    }

    @Test
    public void testAllowStaticMemberImports() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("allowStaticMemberImports", "true");
        // allow all static star imports
        final String[] expected2 = {
            "7: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.checks.imports.*"),
            "9: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "10: " + getCheckMessage(MSG_KEY, "java.lang.*"),
        };
        verify(checkConfig, getPath("InputAvoidStarImportDefault.java"), expected2);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AvoidStarImportCheck testCheckObject =
                new AvoidStarImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testGetRequiredTokens() {
        final AvoidStarImportCheck testCheckObject =
                new AvoidStarImportCheck();
        final int[] actual = testCheckObject.getDefaultTokens();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};

        assertArrayEquals("Default required tokens are invalid", expected, actual);
    }

}
