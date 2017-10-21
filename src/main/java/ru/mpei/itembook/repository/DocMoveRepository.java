package ru.mpei.itembook.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import ru.mpei.itembook.model.DocMove;

public interface DocMoveRepository extends JpaRepository<DocMove, Integer> {
	public DocMove findByDocNum(String docNum);

	@Override
	@EntityGraph(value = "DocMove.docMoveTabList", type = EntityGraphType.LOAD)
	public DocMove findOne(Integer id);
}
