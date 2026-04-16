package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic CRUD interface
 *
 * @param <T>        The response DTO type returned by all operations.
 * @param <CreateIn> The request DTO type used for creation.
 * @param <UpdateIn> The request DTO type used for updates.
 * @param <ID>       The entity identifier type.
 */
public interface BaseCrudService<T, CreateIn, UpdateIn, ID> 
        extends BaseReadService<T, ID>,
                BaseCreateService<T, CreateIn, ID>,
                BaseUpdateService<T, UpdateIn, ID>,
                BaseDeleteService<ID> {
}

interface BaseReadService<T, ID> {
    List<T> findAll();

    Page<T> findAllPageable(Pageable pageable);

    T findById(ID id);
}

interface BaseCreateService<T, CreateIn, ID> {
    T create(CreateIn dto);

    default T createFromDto(CreateIn dto) {
        return create(dto);
    }
}

interface BaseUpdateService<T, UpdateIn, ID> {
    T update(ID id, UpdateIn dto);

    default T updateFromDto(ID id, UpdateIn dto) {
        return update(id, dto);
    }
}

interface BaseDeleteService<ID> {
    void deleteById(ID id);
}
