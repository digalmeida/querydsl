/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.sql.domain;

import com.mysema.query.annotations.QueryProjection;

/**
 * IAndName provides
 * 
 * @author tiwe
 * @version $Id$
 */
public class IdName {

    private int id;

    private String name;

    @QueryProjection
    public IdName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}