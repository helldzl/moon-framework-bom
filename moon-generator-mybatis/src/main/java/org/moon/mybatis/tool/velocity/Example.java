package org.moon.mybatis.tool.velocity;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

/**
 * This class is a simple demonstration of how the Velocity Template Engine
 * can be used in a standalone application using the Velocity utility class.
 * <p/>
 * It demonstrates two of the 'helper' methods found in the org.apache.velocity.util.Velocity
 * class, mergeTemplate() and evaluate().
 *
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: Example2.java 463298 2006-10-12 16:10:32Z henning $
 */

public class Example {

    public void init() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("velocity.properties");
            Properties prop = new Properties();
            prop.load(is);
            Velocity.init(prop);
        } catch (Exception e) {
            System.out.println("Problem initializing Velocity : " + e);
            return;
        }


        /* lets make a Context and put data into it */
        VelocityContext context = new VelocityContext();
        context.put("name", "Velocity");
        context.put("project", "Jakarta");


        /* lets render a template */
        StringWriter w = new StringWriter();
        try {
            Velocity.mergeTemplate("/vm/example2.vm", "UTF-8", context, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" template : " + w);


        String s = "We are using $project $name to render this.";
        // w = new StringWriter();
    }

    public void a2(){

    }

    public static void main(String args[]) {
        Example example = new Example();
        example.init();
    }
}

