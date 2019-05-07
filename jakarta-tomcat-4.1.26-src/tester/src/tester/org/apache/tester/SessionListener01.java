/* ========================================================================= *
 *                                                                           *
 *                 The Apache Software License,  Version 1.1                 *
 *                                                                           *
 *      Copyright (c) 1999, 2000, 2001  The Apache Software Foundation.      *
 *                           All rights reserved.                            *
 *                                                                           *
 * ========================================================================= *
 *                                                                           *
 * Redistribution and use in source and binary forms,  with or without modi- *
 * fication, are permitted provided that the following conditions are met:   *
 *                                                                           *
 * 1. Redistributions of source code  must retain the above copyright notice *
 *    notice, this list of conditions and the following disclaimer.          *
 *                                                                           *
 * 2. Redistributions  in binary  form  must  reproduce the  above copyright *
 *    notice,  this list of conditions  and the following  disclaimer in the *
 *    documentation and/or other materials provided with the distribution.   *
 *                                                                           *
 * 3. The end-user documentation  included with the redistribution,  if any, *
 *    must include the following acknowlegement:                             *
 *                                                                           *
 *       "This product includes  software developed  by the Apache  Software *
 *        Foundation <http://www.apache.org/>."                              *
 *                                                                           *
 *    Alternately, this acknowlegement may appear in the software itself, if *
 *    and wherever such third-party acknowlegements normally appear.         *
 *                                                                           *
 * 4. The names  "The  Jakarta  Project",  "Tomcat",  and  "Apache  Software *
 *    Foundation"  must not be used  to endorse or promote  products derived *
 *    from this  software without  prior  written  permission.  For  written *
 *    permission, please contact <apache@apache.org>.                        *
 *                                                                           *
 * 5. Products derived from this software may not be called "Apache" nor may *
 *    "Apache" appear in their names without prior written permission of the *
 *    Apache Software Foundation.                                            *
 *                                                                           *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES *
 * INCLUDING, BUT NOT LIMITED TO,  THE IMPLIED WARRANTIES OF MERCHANTABILITY *
 * AND FITNESS FOR  A PARTICULAR PURPOSE  ARE DISCLAIMED.  IN NO EVENT SHALL *
 * THE APACHE  SOFTWARE  FOUNDATION OR  ITS CONTRIBUTORS  BE LIABLE  FOR ANY *
 * DIRECT,  INDIRECT,   INCIDENTAL,  SPECIAL,  EXEMPLARY,  OR  CONSEQUENTIAL *
 * DAMAGES (INCLUDING,  BUT NOT LIMITED TO,  PROCUREMENT OF SUBSTITUTE GOODS *
 * OR SERVICES;  LOSS OF USE,  DATA,  OR PROFITS;  OR BUSINESS INTERRUPTION) *
 * HOWEVER CAUSED AND  ON ANY  THEORY  OF  LIABILITY,  WHETHER IN  CONTRACT, *
 * STRICT LIABILITY, OR TORT  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN *
 * ANY  WAY  OUT OF  THE  USE OF  THIS  SOFTWARE,  EVEN  IF  ADVISED  OF THE *
 * POSSIBILITY OF SUCH DAMAGE.                                               *
 *                                                                           *
 * ========================================================================= *
 *                                                                           *
 * This software  consists of voluntary  contributions made  by many indivi- *
 * duals on behalf of the  Apache Software Foundation.  For more information *
 * on the Apache Software Foundation, please see <http://www.apache.org/>.   *
 *                                                                           *
 * ========================================================================= */

package org.apache.tester;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Application event listener for session events.  All events that occur
 * are logged appropriately to the static logger.  In addition, session
 * creation and destruction events are logged to the servlet context log.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.3 $ $Date: 2002/01/19 01:58:24 $
 */

public class SessionListener01
    implements HttpSessionListener, HttpSessionAttributeListener {


    public void attributeAdded(HttpSessionBindingEvent event) {
        StaticLogger.write("SessionListener01: attributeAdded(" +
                           event.getName() + "," + event.getValue() + ")");
        event.getSession().getServletContext().log
            ("SessionListener01: attributeAdded(" + event.getSession().getId()
             + "," + event.getName() + ")");
    }

    public void attributeRemoved(HttpSessionBindingEvent event) {
        StaticLogger.write("SessionListener01: attributeRemoved(" +
                           event.getName() + "," + event.getValue() + ")");
        event.getSession().getServletContext().log
            ("SessionListener01: attributeRemoved(" +
             event.getSession().getId() + "," + event.getName() + ")");
    }

    public void attributeReplaced(HttpSessionBindingEvent event) {
        StaticLogger.write("SessionListener01: attributeReplaced(" +
                           event.getName() + "," + event.getValue() + ")");
        event.getSession().getServletContext().log
            ("SessionListener01: attributeReplaced(" +
             event.getSession().getId() + "," + event.getName() + ")");
    }

    public void sessionCreated(HttpSessionEvent event) {
        StaticLogger.write("SessionListener01: sessionCreated()");
        HttpSession session = event.getSession();
        session.getServletContext().log("SessionListener01: sessionCreated(" +
                                        session.getId() + ")");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        StaticLogger.write("SessionListener01: sessionDestroyed()");
        HttpSession session = event.getSession();
        session.getServletContext().log("SessionListener01: sessionDestroyed("
                                        + session.getId() + ")");
    }


}
