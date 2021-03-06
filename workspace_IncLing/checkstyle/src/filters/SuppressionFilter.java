////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
package filters;

import api.*;

/**
 * <p>
 * This filter accepts AuditEvents according to file, check, line, and
 * column, as specified in a suppression file.
 * </p>
 * @author Rick Giles
 */
public class SuppressionFilter
    extends AutomaticBean
    implements Filter
{
    /** set of individual suppresses */
    private FilterSet filters = new FilterSet();

    /**
     * Loads the suppressions for a file.
     * @param fileName name of the suppressions file.
     * @throws CheckstyleException if there is an error.
     */
    public void setFile(String fileName)
        throws CheckstyleException
    {
        filters = SuppressionsLoader.loadSuppressions(fileName);
    }

    /** {@inheritDoc} */
    @Override
    public boolean accept(AuditEvent event)
    {
        return filters.accept(event);
    }

    @Override
    public String toString()
    {
        return filters.toString();
    }

    @Override
    public int hashCode()
    {
        return filters.hashCode();
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof SuppressionFilter) {
            final SuppressionFilter other = (SuppressionFilter) object;
            return this.filters.equals(other.filters);
        }
        return false;
    }
}
