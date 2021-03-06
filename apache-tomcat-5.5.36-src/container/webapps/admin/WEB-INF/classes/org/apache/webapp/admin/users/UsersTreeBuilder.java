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

package org.apache.webapp.admin.users;


import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.webapp.admin.ApplicationServlet;
import org.apache.webapp.admin.TreeBuilder;
import org.apache.webapp.admin.TreeControl;
import org.apache.webapp.admin.TreeControlNode;
import org.apache.webapp.admin.TomcatTreeBuilder;

/**
 * Implementation of <code>TreeBuilder</code> that adds the nodes required
 * for administering the user database.
 *
 * @author Craig R. McClanahan
 * @version $Id: UsersTreeBuilder.java 939536 2010-04-30 01:21:08Z kkolinko $
 * @since 4.1
 */

public class UsersTreeBuilder implements TreeBuilder {


    // ----------------------------------------------------- Instance Variables


    // ---------------------------------------------------- TreeBuilder Methods


    /**
     * Add the required nodes to the specified <code>treeControl</code>
     * instance.
     *
     * @param treeControl The <code>TreeControl</code> to which we should
     *  add our nodes
     * @param servlet The controller servlet for the admin application
     * @param request The servlet request we are processing
     */
    public void buildTree(TreeControl treeControl,
                          ApplicationServlet servlet,
                          HttpServletRequest request) {

        MessageResources resources = (MessageResources)
            servlet.getServletContext().getAttribute(Globals.MESSAGES_KEY);
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        addSubtree(treeControl.getRoot(), resources, locale);

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Add the subtree of nodes required for user administration.
     *
     * @param root The root node of our tree control
     * @param resources The MessageResources for our localized messages
     *  messages
     */
    protected void addSubtree(TreeControlNode root,
                              MessageResources resources, Locale locale) {

        try {
            String databaseName = URLEncoder.encode
                ("Users:type=UserDatabase,database=UserDatabase",TomcatTreeBuilder.URL_ENCODING);

            TreeControlNode subtree = new TreeControlNode
                ("Global User and Group Administration",
                 "folder_16_pad.gif",
                 resources.getMessage(locale, "users.treeBuilder.subtreeNode"),
                 null,
                 "content",
                 true, "Users");
            TreeControlNode groups = new TreeControlNode
                ("Global Administer Groups",
                 "Groups.gif",
                 resources.getMessage(locale, "users.treeBuilder.groupsNode"),
                 "users/listGroups.do?databaseName=" +
                 URLEncoder.encode(databaseName,TomcatTreeBuilder.URL_ENCODING) +
                 "&forward=" +
                 URLEncoder.encode("Groups List Setup",TomcatTreeBuilder.URL_ENCODING),
                 "content",
                 false, "Users");
            TreeControlNode roles = new TreeControlNode
                ("Global Administer Roles",
                 "Roles.gif",
                 resources.getMessage(locale, "users.treeBuilder.rolesNode"),
                 "users/listRoles.do?databaseName=" +
                 URLEncoder.encode(databaseName,TomcatTreeBuilder.URL_ENCODING) +
                 "&forward=" +
                 URLEncoder.encode("Roles List Setup",TomcatTreeBuilder.URL_ENCODING),
                 "content",
                 false, "Users");
            TreeControlNode users = new TreeControlNode
                ("Global Administer Users",
                 "Users.gif",
                 resources.getMessage(locale, "users.treeBuilder.usersNode"),
                 "users/listUsers.do?databaseName=" +
                 URLEncoder.encode(databaseName,TomcatTreeBuilder.URL_ENCODING) +
                 "&forward=" +
                 URLEncoder.encode("Users List Setup",TomcatTreeBuilder.URL_ENCODING),
                 "content",
                 false, "Users");

            root.addChild(subtree);
            subtree.addChild(users);
            subtree.addChild(groups);
            subtree.addChild(roles);
        } catch(UnsupportedEncodingException ueex) {
            // can't happen
        }

    }


}
