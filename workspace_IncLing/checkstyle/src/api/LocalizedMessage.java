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
package api;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.*;
import java.util.ResourceBundle.Control;


/**
 * Represents a message that can be localised. The translations come from
 * message.properties files. The underlying implementation uses
 * java.text.MessageFormat.
 *
 * @author Oliver Burn
 * @author lkuehne
 * @version 1.0
 */
public final class LocalizedMessage
    implements Comparable<LocalizedMessage>, Serializable
{
    /** Required for serialization. */
    private static final long serialVersionUID = 5675176836184862150L;

    /** hash function multiplicand */
    private static final int HASH_MULT = 29;

    /** the locale to localise messages to **/
    private static Locale sLocale = Locale.getDefault();

    /**
     * A cache that maps bundle names to RessourceBundles.
     * Avoids repetitive calls to ResourceBundle.getBundle().
     */
    private static final Map<String, ResourceBundle> BUNDLE_CACHE = new HashMap<String, ResourceBundle>();

    /** the line number **/
    private final int lineNo;
    /** the column number **/
    private final int colNo;

    /** the severity level **/
    private final SeverityLevel severityLevel;

    /** the id of the module generating the message. */
    private final String moduleId;

    /** the default severity level if one is not specified */
    private static final SeverityLevel DEFAULT_SEVERITY = SeverityLevel.ERROR;

    /** key for the message format **/
    private final String key;

    /** arguments for MessageFormat **/
    private final Object[] args;

    /** name of the resource bundle to get messages from **/
    private final String bundle;

    /** class of the source for this LocalizedMessage */
    private final Class<?> sourceClass;

    /** a custom message overriding the default message from the bundle. */
    private final String customMessage;

    private final int columnCharIndex;
    
    private final int tokenType;

    @Override
    public boolean equals(Object object)
    {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LocalizedMessage)) {
            return false;
        }

        final LocalizedMessage localizedMessage = (LocalizedMessage) object;

        if (colNo != localizedMessage.colNo) {
            return false;
        }
        if (lineNo != localizedMessage.lineNo) {
            return false;
        }
        if (!key.equals(localizedMessage.key)) {
            return false;
        }

        if (!Arrays.equals(args, localizedMessage.args)) {
            return false;
        }
        // ignoring bundle for perf reasons.

        // we currently never load the same error from different bundles.

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        result = lineNo;
        result = HASH_MULT * result + colNo;
        result = HASH_MULT * result + key.hashCode();
        for (final Object element : args) {
            result = HASH_MULT * result + element.hashCode();
        }
        return result;
    }

    /**
     * Creates a new <code>LocalizedMessage</code> instance.
     *
     * @param lineNo line number associated with the message
     * @param colNo column number associated with the message
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the message
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the Class that is the source of the message
     * @param customMessage optional custom message overriding the default
     */
    public LocalizedMessage(int lineNo,
                            int colNo,
                            
                            String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage)
    {
    	this.columnCharIndex = 0;
    	this.tokenType=0;

        this.lineNo = lineNo;
        this.colNo = colNo;
        this.key = key;
        this.args = (null == args) ? null : args.clone();
        this.bundle = bundle;
        this.severityLevel = severityLevel;
        this.moduleId = moduleId;
        this.sourceClass = sourceClass;
        this.customMessage = customMessage;
    }

    /**
     * Creates a new <code>LocalizedMessage</code> instance.
     *
     * @param lineNo line number associated with the message
     * @param colNo column number associated with the message
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the Class that is the source of the message
     * @param customMessage optional custom message overriding the default
     */
    public LocalizedMessage(int lineNo,
                            int colNo,
                            String bundle,
                            String key,
                            Object[] args,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage)
    {
        this(lineNo,
             colNo,
             bundle,
             key,
             args,
             DEFAULT_SEVERITY,
             moduleId,
             sourceClass,
             customMessage);
    }

    /**
     * Creates a new <code>LocalizedMessage</code> instance.
     *
     * @param lineNo line number associated with the message
     * @param bundle resource bundle name
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param severityLevel severity level for the message
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the source class for the message
     * @param customMessage optional custom message overriding the default
     */
    public LocalizedMessage(int lineNo,
                            String bundle,
                            String key,
                            Object[] args,
                            SeverityLevel severityLevel,
                            String moduleId,
                            Class<?> sourceClass,
                            String customMessage)
    {
        this(lineNo, 0, bundle, key, args, severityLevel, moduleId,
                sourceClass, customMessage);
    }

    /**
     * Creates a new <code>LocalizedMessage</code> instance. The column number
     * defaults to 0.
     *
     * @param lineNo line number associated with the message
     * @param bundle name of a resource bundle that contains error messages
     * @param key the key to locate the translation
     * @param args arguments for the translation
     * @param moduleId the id of the module the message is associated with
     * @param sourceClass the name of the source for the message
     * @param customMessage optional custom message overriding the default
     */
    public LocalizedMessage(
        int lineNo,
        String bundle,
        String key,
        Object[] args,
        String moduleId,
        Class<?> sourceClass,
        String customMessage)
    {
        this(lineNo, 0, bundle, key, args, DEFAULT_SEVERITY, moduleId,
                sourceClass, customMessage);
    }

    /** Clears the cache. */
    public static void clearCache()
    {
//        synchronized (BUNDLE_CACHE) {
            BUNDLE_CACHE.clear();
//        }
    }

    /** @return the translated message **/
    public String getMessage()
    {

        final String customMessage = getCustomMessage();
        if (customMessage != null) {
            return customMessage;
        }

        try {
            // Important to use the default class loader, and not the one in
            // the GlobalProperties object. This is because the class loader in
            // the GlobalProperties is specified by the user for resolving
            // custom classes.
            final ResourceBundle bundle = getBundle(this.bundle);
            final String pattern = bundle.getString(key);
            return MessageFormat.format(pattern, args);
        }
        catch (final MissingResourceException ex) {
            // If the Check author didn't provide i18n resource bundles
            // and logs error messages directly, this will return
            // the author's original message
            return MessageFormat.format(key, args);
        }
    }

    /**
     * Returns the formatted custom message if one is configured.
     * @return the formatted custom message or <code>null</code>
     *          if there is no custom message
     */
    private String getCustomMessage()
    {

        if (customMessage == null) {
            return null;
        }

        return MessageFormat.format(customMessage, args);
    }

    /**
     * Find a ResourceBundle for a given bundle name. Uses the classloader
     * of the class emitting this message, to be sure to get the correct
     * bundle.
     * @param bundleName the bundle name
     * @return a ResourceBundle
     */
    private ResourceBundle getBundle(String bundleName)
    {
//        synchronized (BUNDLE_CACHE) {
            ResourceBundle bundle = BUNDLE_CACHE
                    .get(bundleName);
            if (bundle == null) {
            	System.out.println(bundleName);
                bundle = ResourceBundle.getBundle(bundleName, sLocale,
                        sourceClass.getClassLoader(), new UTF8Control());
                BUNDLE_CACHE.put(bundleName, bundle);
            }
            return bundle;
//        }
    }

    /** @return the line number **/
    public int getLineNo()
    {
        return lineNo;
    }

    /** @return the column number **/
    public int getColumnNo()
    {
        return colNo;
    }

    /** @return the severity level **/
    public SeverityLevel getSeverityLevel()
    {
        return severityLevel;
    }

    /** @return the module identifier. */
    public String getModuleId()
    {
        return moduleId;
    }

    /**
     * Returns the message key to locate the translation, can also be used
     * in IDE plugins to map error messages to corrective actions.
     *
     * @return the message key
     */
    public String getKey()
    {
        return key;
    }

    /** @return the name of the source for this LocalizedMessage */
    public String getSourceName()
    {
        return sourceClass.getName();
    }

    /** @param locale the locale to use for localization **/
    public static void setLocale(Locale locale)
    {
        sLocale = locale;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Interface Comparable methods
    ////////////////////////////////////////////////////////////////////////////

    /** {@inheritDoc} */
    @Override
    public int compareTo(LocalizedMessage other)
    {
        if (getLineNo() == other.getLineNo()) {
            if (getColumnNo() == other.getColumnNo()) {
                return getMessage().compareTo(other.getMessage());
            }
            return (getColumnNo() < other.getColumnNo()) ? -1 : 1;
        }

        return (getLineNo() < other.getLineNo()) ? -1 : 1;
    }

    /**
     * <p>
     * Custom ResourceBundle.Control implementation which allows explicitly read
     * the properties files as UTF-8
     * </p>
     *
     * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
     */
    private static class UTF8Control extends Control
    {
        @Override
        public ResourceBundle newBundle(String aBaseName, Locale aLocale, String aFormat,
                 ClassLoader aLoader, boolean aReload) throws IllegalAccessException,
                  InstantiationException, IOException
        {
            // The below is a copy of the default implementation.
            final String bundleName = toBundleName(aBaseName, aLocale);
            final String resourceName = toResourceName(bundleName, "properties");
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (aReload) {
                final URL url = aLoader.getResource(resourceName);
                if (url != null) {
                    final URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            }
            else {
                stream = aLoader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try (Reader streamReader = new InputStreamReader(stream, "UTF-8")) {
                    // Only this line is changed to make it to read properties files as UTF-8.
                    bundle = new PropertyResourceBundle(streamReader);
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }
    public int getColumnCharIndex() {
        return columnCharIndex;
    }
    public int getTokenType() {
        return tokenType;
    }
    @Override
    public String toString() {
    	return lineNo + " " + getMessage();// TODO Jens
    }
}
