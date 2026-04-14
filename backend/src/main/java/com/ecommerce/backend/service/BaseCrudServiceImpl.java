package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.service.interfaces.BaseCrudService;

/**
 * @param <Entity>   The JPA entity type.
 * @param <T>        The response DTO type.
 * @param <CreateIn> The request DTO type for creation.
 * @param <UpdateIn> The request DTO type for updates.
 */
public abstract class BaseCrudServiceImpl<Entity, T, CreateIn, UpdateIn> implements BaseCrudService<T, CreateIn, UpdateIn, Long> {

    protected final JpaRepository<Entity, Long> repository;

    protected BaseCrudServiceImpl(JpaRepository<Entity, Long> repository) {
        this.repository = repository;
    }

    protected abstract T toDto(Entity entity);

    protected abstract List<T> toDtoList(List<Entity> entities);

    protected abstract Entity toEntity(CreateIn dto);

    protected abstract void updateEntity(UpdateIn dto, Entity target);

    protected abstract void updateEntityFromCreate(CreateIn dto, Entity target);

    protected abstract Entity newEntity();

    @Override
    public List<T> findAll() {
        return toDtoList(repository.findAll());
    }

    @Override
    public Page<T> findAllPageable(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    @Override
    public T findById(Long id) {
        Entity entity = repository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id));
        return toDto(entity);
    }

    @Override
    public T create(CreateIn dto) {
        Entity entity = newEntity();
        beforeCreate(dto, entity);
        updateEntityFromCreate(dto, entity);
        afterCreate(dto, entity);
        Entity saved = repository.save(entity);
        return toDto(saved);
    }

    @Override
    public T update(Long id, UpdateIn dto) {
        Entity entity = repository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id));
        beforeUpdate(dto, entity);
        updateEntity(dto, entity);
        afterUpdate(dto, entity);
        Entity saved = repository.save(entity);
        return toDto(saved);
    }

    // Hooks 

    protected void beforeCreate(CreateIn dto, Entity entity) {
    }

    protected void afterCreate(CreateIn dto, Entity entity) {
    }

    protected void beforeUpdate(UpdateIn dto, Entity entity) {
    }

    protected void afterUpdate(UpdateIn dto, Entity entity) {
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw entityNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // Error message 

    protected RuntimeException entityNotFoundException(Long id) {
        String entityName = getEntityName();
        return new RuntimeException(entityName + " not found with id: " + id);
    }

    protected String getEntityName() {
        return "Entity";
    }

    public Entity findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id));
    }
}
