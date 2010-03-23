/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.types;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ClassUtils;


/**
 * EConstructor represents a constructor invocation
 * 
 * @author tiwe
 * 
 * @param <D> Java type
 */
public class EConstructor<D> extends Expr<D> {
    
    private static final long serialVersionUID = -602747921848073175L;
    
    private static Class<?> normalize(Class<?> clazz){
        if (clazz.isPrimitive()){
            return ClassUtils.primitiveToWrapper(clazz);                    
        }else{
            return clazz;
        }
    }

    public static <D> EConstructor<D> create(Class<D> type, Expr<?>... args){
        for (Constructor<?> c : type.getConstructors()){
            Class<?>[] paramTypes = c.getParameterTypes();            
            if (paramTypes.length == args.length){
                boolean found = true;
                for (int i = 0; i < paramTypes.length; i++){                    
                    if (!normalize(paramTypes[i]).isAssignableFrom(args[i].getType())){
                        found = false;
                        break;
                    }
                }
                if (found){
                    return new EConstructor<D>(type, paramTypes, args);    
                }                
            }            
        }
        throw new ExprException("Got no matching constructor");        
    }

    private final List<Expr<?>> args;
    
    private final Class<?>[] parameterTypes;

    public EConstructor(Class<D> type, Class<?>[] paramTypes, Expr<?>... args) {
        super(type);
        this.parameterTypes = paramTypes.clone();
        this.args = Collections.unmodifiableList(Arrays.asList(args));
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);        
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }else if (obj instanceof EConstructor){
            EConstructor c = (EConstructor)obj;
            return Arrays.equals(parameterTypes, c.parameterTypes)
                && args.equals(c.args)
                && getType().equals(c.getType());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return getType().hashCode();
    }
    
    /**
     * Get the constructor invocation argument with the given index
     * 
     * @param index
     * @return
     */
    public final Expr<?> getArg(int index) {
        return args.get(index);
    }
    
    /**
     * Get the constructor invocation arguments
     * 
     * @return
     */
    public final List<Expr<?>> getArgs() {
        return args;
    }
    
    /**
     * Returns the "real" constructor that matches the Constructor expression
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public Constructor<D> getJavaConstructor() {
        try {
            return (Constructor<D>) getType().getConstructor(parameterTypes);
        } catch (SecurityException e) {
           throw new ExprException(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
           throw new ExprException(e.getMessage(), e);
        }
    }

}