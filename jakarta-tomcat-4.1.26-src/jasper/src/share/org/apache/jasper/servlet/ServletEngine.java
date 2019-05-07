/*
 * $Header: /home/cvs/jakarta-tomcat-jasper/jasper2/src/share/org/apache/jasper/servlet/Attic/ServletEngine.java,v 1.1.1.1 2002/03/28 18:46:20 kinman Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/03/28 18:46:20 $
 *
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */ 
package org.apache.jasper.servlet;

import java.io.File;

import javax.servlet.ServletContext;

/**
 * Simple class to factor out servlet runner dependencies from the JSP
 * engine. There's a few motivations here: 
 *
 *	(a) ability for the JSP engine to be able to run on multiple
 *          servlet engines - 2.1 and 2.2
 *	(b) ability for the JSP engine to take advantage of specific
 *          servlet engines; this is crucial from a J2EE point of
 *          view. 
 *
 * @author Anil K. Vijendran
 * @author Harish Prabandham
 */
public class ServletEngine {
    static ServletEngine tomcat;
    static ServletEngine deflt;
    
    /**
     * Get a specific ServletEngine instance for the particular servlet runner
     * we are running on.
     */
    static ServletEngine getServletEngine(String serverInfo) {
        if (serverInfo.startsWith("Tomcat Web Server")) {
            if (tomcat == null) {
                try {
                    tomcat = (ServletEngine)
                        Class.forName("org.apache.jasper.runtime.TomcatServletEngine").newInstance();
                } catch (Exception ex) {
                    return null;
                }
            }
            return tomcat;
        } else {
            if (deflt == null) 
                deflt = new ServletEngine();
            return deflt;
        }
    }
    
    /**
     * Get the class loader for this ServletContext object. 
     */
    public ClassLoader getClassLoader(ServletContext ctx) {
        return null;
    }
}






