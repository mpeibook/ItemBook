package ru.mpei.itembook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import ru.mpei.itembook.model.DocMove;
import ru.mpei.itembook.model.DocMoveTab;
import ru.mpei.itembook.model.RefItem;
import ru.mpei.itembook.repository.DocMoveRepository;
import ru.mpei.itembook.repository.RefItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemBookApplicationTests {
	
	@Autowired
	private RefItemRepository refItemRepository;
	
	@Autowired
	private DocMoveRepository docMoveRepository;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void docMoveTabQuantityMustBeGreaterThan0() {
		DocMove docMove = new DocMove(LocalDate.of(2017, 2, 1), "D4");
		RefItem refItem = refItemRepository.findByCode("I1");
		List<DocMoveTab> docMoveTabList = new ArrayList<>();
		docMoveTabList.add(new DocMoveTab(docMove, refItem, -1));
		docMove.setDocMoveTabList(docMoveTabList);
		
		Throwable thrown = catchThrowable(() -> docMoveRepository.save(docMove));
		assertThat(thrown).isInstanceOf(DataIntegrityViolationException.class).hasMessageContaining("QUANTITY > 0");
	}
	
	@Test
	public void databaseTransactionMustBeRollbacked() {
		DocMove docMove = new DocMove(LocalDate.of(2017, 2, 1), "D4");
		List<DocMoveTab> docMoveTabList = new ArrayList<>();
		RefItem refItem = refItemRepository.findByCode("I1");
		docMoveTabList.add(new DocMoveTab(docMove, refItem, 1));
		docMoveTabList.add(new DocMoveTab(docMove, refItem, 1));
		docMoveTabList.add(new DocMoveTab(docMove, refItem, -1));
		docMove.setDocMoveTabList(docMoveTabList);
		
		try {
			docMoveRepository.save(docMove);
		} catch (DataIntegrityViolationException ex) { }
		
		DocMove docMoveD4 = docMoveRepository.findByDocNum("D4");
		assertThat(docMoveD4).isNull();
	}

}
