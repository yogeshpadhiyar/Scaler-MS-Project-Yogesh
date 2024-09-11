package com.yogesh.scalermsprojectyogesh.service;

public abstract interface CrudService<T> {
    
    T create(T t) throws Exception;
    T readById(Long id) throws Exception;
    T update(T t) throws Exception;
    void deleteById(Long id) throws Exception;
}
