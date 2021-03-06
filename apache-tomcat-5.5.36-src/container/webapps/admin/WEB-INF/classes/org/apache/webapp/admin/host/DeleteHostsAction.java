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


package org.apache.webapp.admin.host;

import java.io.IOException;
import java.util.Locale;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import org.apache.webapp.admin.ApplicationServlet;
import org.apache.webapp.admin.TomcatTreeBuilder;
import org.apache.webapp.admin.TreeControl;
import org.apache.webapp.admin.TreeControlNode;


/**
 * The <code>Action</code> that completes <em>Delete Hosts</em>
 * transactions.
 *
 * @author Manveen Kaur
 * @version $Id: DeleteHostsAction.java 939536 2010-04-30 01:21:08Z kkolinko $
 */

public class DeleteHostsAction extends Action {


    /**
     * Signature for the <code>removeHost</code> operation.
     */
    private String removeHostTypes[] =
    { "java.lang.String",      // Object name
    };


    /**
     * The MBeanServer we will be interacting with.
     */
    private MBeanServer mBServer = null;
    

    // --------------------------------------------------------- Public Methods
    
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param actionForm The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws IOException, ServletException {
        
        
        // Look up the components we will be using as needed
        HttpSession session = request.getSession();
        Locale locale = getLocale(request);
        MessageResources resources = getResources(request);

        // Acquire a reference to the MBeanServer containing our MBeans
        try {
            mBServer = ((ApplicationServlet) getServlet()).getServer();
        } catch (Throwable t) {
            throw new ServletException
            ("Cannot acquire MBeanServer reference", t);
        }
               
        // Delete the specified Hosts
        String hosts[]  = ((HostsForm) form).getHosts();
        String values[] = new String[1];
        String operation = "removeHost";
        
        getServlet().log("enter DeleteHosts " + hosts);

        try {

            // Look up our tree control data structure
            TreeControl control = (TreeControl)
                session.getAttribute("treeControlTest");

            // Remove the specified hosts
            for (int i = 0; i < hosts.length; i++) {
                values[0] = hosts[i];
                getServlet().log("remove host " + hosts[i]);
                if (control != null) {
                    control.selectNode(null);
                    TreeControlNode node = control.findNode(hosts[i]);
                    ObjectName fname = TomcatTreeBuilder.getMBeanFactory();
                    mBServer.invoke(fname, operation,
                                values, removeHostTypes);
                    if (node != null) {
                        node.remove();
                    } else {
                        getServlet().log("Missing TreeControlNode for " +
                                         hosts[i]);
                    }
                } else {
                    getServlet().log("Missing TreeControl attribute");
                }
            }

        } catch (Exception e) {
            getServlet().log
                (resources.getMessage(locale, "users.error.invoke",
                                      operation), e);
            response.sendError
                (HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                 resources.getMessage(locale, "users.error.invoke",
                                      operation));
            return (null);

        }

        // Report successful completion of this transaction
        return (mapping.findForward("Save Successful"));

    }
    
}
