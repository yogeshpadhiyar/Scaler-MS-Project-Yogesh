package com.yogesh.scalermsprojectyogesh.service;

public abstract interface CrudService<T> {
    
    T create(T t);
    T readById(Long id);
    T update(T t);
    void deleteById(Long id);
}
