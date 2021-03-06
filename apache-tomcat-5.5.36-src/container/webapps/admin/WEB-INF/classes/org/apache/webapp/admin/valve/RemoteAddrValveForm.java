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

package org.apache.webapp.admin.valve;

import java.lang.IllegalArgumentException;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * Form bean for the remote addr valve page.
 *
 * @author Manveen Kaur
 * @version $Id: RemoteAddrValveForm.java 939536 2010-04-30 01:21:08Z kkolinko $
 */

public final class RemoteAddrValveForm extends ValveForm {
    
    // ----------------------------------------------------- Instance Variables
    
    
    /**
     * The text for the allow IP addresses.
     * A comma-separated list of regular expression patterns
     * that the remote client's IP address is compared to. 
     */
    private String allow = "";

    /**
     * The text for the deny IP addresses.
     */
    private String deny = "";
    
    /**
     * The set of <code>allow</code> regular expressions we will evaluate.
     */
    private Pattern allows[] = new Pattern[0];

    /**
     * The set of <code>deny</code> regular expressions we will evaluate.
     */
    private Pattern denies[] = new Pattern[0];


    // ------------------------------------------------------------- Properties

    /**
     * Return the allow hosts IP adddresses.
     */
    public String getAllow() {
        
        return this.allow;
        
    }
    
    /**
     * Set the allow hosts.
     */
    public void setAllow(String allow) {
        
        this.allow = allow;
        
    }
    
    /**
     * Return the deny hosts IP adddresses.
     */
    public String getDeny() {
        
        return this.deny;
        
    }
    
    /**
     * Set the deny hosts IP addresses.
     */
    public void setDeny(String deny) {
        
        this.deny = deny;
        
    }    

    // --------------------------------------------------------- Public Methods
    
    
    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
                
        super.reset(mapping, request);
        this.allow = null;
        this.deny = null;
        this.allows = null;
        this.denies = null;
        
    }
    
    /**
     * Render this object as a String.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("RemoteAddrValveForm[adminAction=");
        sb.append(getAdminAction());
        sb.append("',valveType=");
        sb.append(getValveType());
        sb.append(",allow=");
        sb.append(getAllow());
        sb.append(",deny=");
        sb.append(getDeny());        
        sb.append("',objectName='");
        sb.append(getObjectName());
        sb.append("]");
        return (sb.toString());

    }
    
    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    
    public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        // front end validation when save is clicked.        
        // validate allow/deny patterns
        if ((allow == null) || (allow.length() < 1)) {
            if ((deny == null) || (deny.length() < 1)) {
                errors.add("allow",
                new ActionMessage("error.allow.deny.required"));
            }
        }                
        
        try {
            allows = ValveUtil.precalculate(allow);            
        } catch (IllegalArgumentException e) {
            errors.add("allow", new ActionMessage("error.syntax"));
            return errors;
        }
         
        try {   
            denies = ValveUtil.precalculate(deny);
        } catch (IllegalArgumentException e) {
            errors.add("allow", new ActionMessage("error.syntax"));
            return errors;
        }
        
        String ip = request.getRemoteAddr();
        
        if (ip == null) {
            return errors;
        }
        
        for (int i = 0; i < denies.length; i++) {
            if (denies[i].matcher(ip).matches()) {
                if (allows.length < 1) {
                    errors.add("deny",
                        new ActionMessage("error.denyIP"));
                }
                for (int j = 0; j < allows.length; j++) {
                    if (!allows[j].matcher(ip).matches()) { 
                        errors.add("deny",
                        new ActionMessage("error.denyIP"));
                    }
                }
            }    
        }
        
        boolean allowMatch = true;
        if (allows.length > 0) {
            allowMatch = false;
        }
        for (int i = 0; i < allows.length; i++) {
            if (allows[i].matcher(ip).matches()) {
                allowMatch = true;       
            }
        }       
        if (!allowMatch) {
            errors.add("allow", new ActionMessage("error.allowIP"));
        }
        
        return errors;
    }
}
