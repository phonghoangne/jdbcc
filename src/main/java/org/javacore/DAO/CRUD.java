package org.javacore.DAO;

import java.util.List;
// inter chua 4 tinh nang co ban
public interface CRUD<T> {
    Boolean insert(T t);
    Boolean update(T t);
    Boolean deleteById(Integer id);
    List<T> read();
}
