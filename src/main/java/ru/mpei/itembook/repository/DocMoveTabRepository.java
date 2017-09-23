package ru.mpei.itembook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mpei.itembook.model.DocMoveTab;
import ru.mpei.itembook.model.DocMove;

public interface DocMoveTabRepository extends JpaRepository<DocMoveTab, Integer> {
	List<DocMoveTab> findByDocMove(DocMove docMove);
}
