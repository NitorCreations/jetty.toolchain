package org.eclipse.jetty.toolchain.enforcer.rules;

import java.io.IOException;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.util.FileUtils;

//========================================================================
//Copyright (c) Webtide LLC
//------------------------------------------------------------------------
//All rights reserved. This program and the accompanying materials
//are made available under the terms of the Eclipse Public License v1.0
//and Apache License v2.0 which accompanies this distribution.
//
//The Eclipse Public License is available at
//http://www.eclipse.org/legal/epl-v10.html
//
//The Apache License v2.0 is available at
//http://www.apache.org/licenses/LICENSE-2.0.txt
//
//You may elect to redistribute this code under either of these licenses.
//========================================================================

public class VersionRule implements EnforcerRule
{

    /**
     * Simple param. This rule will fail if the value is true.
     */
    private boolean shouldIfail = false;
    
    public void execute( EnforcerRuleHelper helper )
        throws EnforcerRuleException
    {
        try
        {        
            String artifactId = (String) helper.evaluate( "${project.artifactId}" );
            String version = (String) helper.evaluate( "${project.version}" );
            
            if ( "jetty-project".equals(  artifactId ) && !version.contains( "SNAPSHOT" ) )
            {            
                String versionTxt = FileUtils.fileRead( "VERSION.txt" );
                
                if ( versionTxt.contains( "SNAPSHOT" ) ) 
                {
                    shouldIfail = true;
                }
            }
            
           
            if ( this.shouldIfail )
            {
                throw new EnforcerRuleException( "When version is a non-snapshot, VERSION.txt should not have a SNAPSHOT version in it." );
            }
        }
        catch ( ExpressionEvaluationException e )
        {
            throw new EnforcerRuleException( "Unable to lookup an expression " + e.getLocalizedMessage(), e );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String getCacheId()
    {
        return "" + shouldIfail;
    }

    public boolean isCacheable()
    {
        return true;
    }

    public boolean isResultValid( EnforcerRule arg0 )
    {
        return true;
    }
    
}