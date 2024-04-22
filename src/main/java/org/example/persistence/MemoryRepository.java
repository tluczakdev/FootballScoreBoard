package org.example.persistence;

import java.util.List;

public interface MemoryRepository<E, ID> {

    E save(E object);

    E fetch(ID id);

    List<E> findAll();
}
