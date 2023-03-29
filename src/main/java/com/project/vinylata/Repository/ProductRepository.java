package com.project.vinylata.Repository;


import com.project.vinylata.Model.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(Long productId);

    @Override
    void flush();

    @Override
    <S extends Product> S saveAndFlush(S entity);

    @Override
    <S extends Product> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    default void deleteInBatch(Iterable<Product> entities) {
        JpaRepository.super.deleteInBatch(entities);
    }

    @Override
    void deleteAllInBatch(Iterable<Product> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Integer> integers);

    @Override
    void deleteAllInBatch();

    @Override
    Product getOne(Integer integer);

    @Override
    Product getById(Integer integer);

    @Override
    Product getReferenceById(Integer integer);

    @Override
    <S extends Product> List<S> findAll(Example<S> example);

    @Override
    <S extends Product> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Product> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Product> findAll();

    @Override
    List<Product> findAllById(Iterable<Integer> integers);

    @Override
    <S extends Product> S save(S entity);

    @Override
    Optional<Product> findById(Integer integer);

    @Override
    boolean existsById(Integer integer);

    @Override
    long count();

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(Product entity);

    @Override
    void deleteAllById(Iterable<? extends Integer> integers);

    @Override
    void deleteAll(Iterable<? extends Product> entities);

    @Override
    void deleteAll();
}
