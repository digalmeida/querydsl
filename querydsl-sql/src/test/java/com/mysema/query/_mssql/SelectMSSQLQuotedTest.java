/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query._mssql;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.mysema.query.Connections;
import com.mysema.query.SelectBaseTest;
import com.mysema.query.Target;
import com.mysema.query.sql.SQLServerTemplates;
import com.mysema.testutil.FilteringTestRunner;
import com.mysema.testutil.Label;
import com.mysema.testutil.ResourceCheck;

@RunWith(FilteringTestRunner.class)
@ResourceCheck("/sqlserver.run")
@Label(Target.SQLSERVER)
public class SelectMSSQLQuotedTest extends SelectBaseTest {

    @BeforeClass
    public static void setUp() throws Exception {
        Connections.initSQLServer();
    }

    @Before
    public void setUpForTest() {
        dialect = new SQLServerTemplates(true).newLineToSingleSpace();
    }
    
    @Override
    public void limitAndOffset2() throws SQLException {     
        
    }

    @Override
    public void serialization(){
        
    }
    
    @Override
    public void subQueries() throws SQLException {
        
    }
    
    
    
}