/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query.domain;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;

import com.mysema.query.annotations.QueryEntity;
import com.mysema.query.annotations.Config;
import com.mysema.query.domain.rel.RelationType2;
import com.mysema.query.types.path.CollectionPath;
import com.mysema.query.types.path.EnumPath;
import com.mysema.query.types.path.ListPath;
import com.mysema.query.types.path.MapPath;
import com.mysema.query.types.path.SetPath;

public class RelationTest extends AbstractTest{

    public enum MyEnum {
        VAR1, VAR2
    }

    @QueryEntity
    public static class Reference {

    }

    @QueryEntity
    public static class GenericRelations{
        Collection<Collection<Reference>> col1;
        Collection<List<Reference>> col2;
        Collection<Collection<? extends Reference>> col3;
        Collection<List<? extends Reference>> col4;

        Set<List<Reference>> set1;
        Set<Collection<Reference>> set2;
        Set<List<? extends Reference>> set3;
        Set<Collection<? extends Reference>> set4;

        Map<String,List<String>> map1;
        Map<List<String>,String> map2;
        Map<String,List<? extends String>> map3;
        Map<List<? extends String>,String> map4;
    }

    @QueryEntity
    @Config(listAccessors=true, mapAccessors=true)
    public static class RelationType {

        MyEnum enumProperty;
        List<MyEnum> enumList;
        Map<String, MyEnum> enumMap1;
        Map<MyEnum, String> enumMap;

        // list
        List<RelationType> list;
        List<? extends RelationType> list2;
        List<String> list3;
        List<RelationType2<?>> list4;
        List<Reference> list5;

        List<List<Reference>> list6;
        List<Collection<Reference>> list7;

        // set
        Set<RelationType> set;
        SortedSet<RelationType> sortedSet;
        Set<String> set2;
        Set<RelationType2<?>> set3;
        Set<Reference> set4;

        // .. of Object
        List<Object> listOfObjects;
        Set<Object> setOfObjects;
        Set<Reference> setOfObjects2;

        // collection
        Collection<RelationType> collection;
        Collection<RelationType2<?>> collection2;
        Collection<String> collection3;
        Collection<Reference> collection4;

        // map
        Map<String, RelationType> map;
        Map<RelationType, RelationType> map2;
        Map<RelationType, String> map3;

        Map<String, RelationType2<?>> map4;
        Map<RelationType2<?>, RelationType2<?>> map5;
        Map<RelationType2<?>, String> map6;

        Map<String, Reference> map7;
        Map<Reference, Reference> map8;
        Map<Reference, String> map9;

    }

    @Test
    public void test() throws SecurityException, NoSuchFieldException{
        cl = QRelationTest_RelationType.class;
        match(EnumPath.class, "enumProperty");
        match(ListPath.class, "enumList");
        match(MapPath.class, "enumMap1");
        match(MapPath.class, "enumMap");

        match(ListPath.class, "list");
        match(ListPath.class, "list2");
        match(ListPath.class, "list3");
        match(ListPath.class, "list4");
        match(ListPath.class, "list5");

        match(SetPath.class, "set");
        match(SetPath.class, "sortedSet");
        match(SetPath.class, "set2");
        match(SetPath.class, "set3");
        match(SetPath.class, "set4");

        match(ListPath.class, "listOfObjects");
        match(SetPath.class, "setOfObjects");
        match(SetPath.class, "setOfObjects2");

        match(CollectionPath.class, "collection");
        match(CollectionPath.class, "collection2");
        match(CollectionPath.class, "collection3");
        match(CollectionPath.class, "collection4");

        match(MapPath.class, "map");
        match(MapPath.class, "map2");
        match(MapPath.class, "map3");
        match(MapPath.class, "map4");
        match(MapPath.class, "map5");
        match(MapPath.class, "map6");
        match(MapPath.class, "map7");
        match(MapPath.class, "map8");
        match(MapPath.class, "map9");
    }

    @Test
    public void List_Usage(){
        String expected = "relationType.list.get(0).set";
        assertEquals(expected, QRelationTest_RelationType.relationType.list.get(0).set.toString());
        assertEquals(expected, QRelationTest_RelationType.relationType.list(0).set.toString());

        assertEquals(List.class, QRelationTest_RelationType.relationType.list.getType());
        assertEquals(Set.class,  QRelationTest_RelationType.relationType.set.getType());
    }

}
