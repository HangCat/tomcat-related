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
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Part 3 of the ErrorPage Tests.  Throws an exception of a specified type
 * (wrapped in a servlet exception as required by the throws clause), which
 * is mapped to the ErrorPage04 servlet in the deployment descriptor.
 *
 * @author Craig R. McClanahan
 * @version $Id: ErrorPage03.java 939535 2010-04-30 01:11:10Z kkolinko $
 */

public class ErrorPage03 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();

        // Write a FAILED message that should get replaced by the error text
        writer.println("ErrorPage03 FAILED - Original response returned");

        // Throw the specified exception
        throw new ServletException
            (new TesterException("ErrorPage03 Threw Exception"));

    }

}
