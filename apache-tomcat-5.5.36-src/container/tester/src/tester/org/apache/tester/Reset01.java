/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tester;


import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Positive test for ServletResponse.reset().
 *
 * @author Craig R. McClanahan
 * @version $Id: Reset01.java 939535 2010-04-30 01:11:10Z kkolinko $
 */

public class Reset01 extends GenericServlet {


    // --------------------------------------------------------- Public Methods


    /**
     * Process a servlet request and create the corresponding response.
     *
     * @param request The request we are processing
     * @param response The response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void service(ServletRequest request, ServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        try {
            writer.println("Reset01 FAILED - Did not reset buffer");
            response.reset();
            response.setContentType("text/plain");
            writer.println("Reset01 PASSED");
        } catch (IllegalStateException e) {
            writer.println("Reset01 FAILED - Threw IllegaStateException");
            e.printStackTrace(writer);
        }
        while (true) {
            String message = StaticLogger.read();
            if (message == null)
                break;
            writer.println(message);
        }
        StaticLogger.reset();

    }


}
