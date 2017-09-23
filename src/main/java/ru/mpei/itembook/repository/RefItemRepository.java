package ru.mpei.itembook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mpei.itembook.model.RefItem;

public interface RefItemRepository extends JpaRepository<RefItem, Integer> { }
