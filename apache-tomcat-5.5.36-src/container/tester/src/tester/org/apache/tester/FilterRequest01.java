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
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Positive test for being able to filter input.  The input will be echoed
 * back in the response, after having been converted to upper case by the
 * associated filter.  Use request parameter <code>type</code> to determine
 * whether to call getReader() ("reader") or getInputStream() ("stream").
 *
 * @author Craig R. McClanahan
 * @version $Id: FilterRequest01.java 939535 2010-04-30 01:11:10Z kkolinko $
 */

public class FilterRequest01 extends HttpServlet {

    public void service(HttpServletRequest request,
                        HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        StringBuffer sb = new StringBuffer();

        String type = request.getParameter("type");
        if (type == null)
            type = "reader";
        if (type.equalsIgnoreCase("reader")) {
            BufferedReader br = request.getReader();
            while (true) {
                int c = br.read();
                if (c < 0)
                    break;
                sb.append((char) c);
            }
            br.close();
        } else {
            ServletInputStream sis = request.getInputStream();
            while (true) {
                int c = sis.read();
                if (c < 0)
                    break;
                sb.append((char) c);
            }
            sis.close();
        }

        writer.println(sb.toString());
        while (true) {
            String message = StaticLogger.read();
            if (message == null)
                break;
            writer.println(message);
        }
        StaticLogger.reset();

    }

}
