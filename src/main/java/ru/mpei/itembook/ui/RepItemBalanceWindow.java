package ru.mpei.itembook.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Grid;
import com.vaadin.ui.Window;

import ru.mpei.itembook.model.RepItemBalance;
import ru.mpei.itembook.repository.RepItemBalanceRepository;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class RepItemBalanceWindow extends Window {
	
	public RepItemBalanceWindow(RepItemBalanceRepository repItemBalanceRepository) {
		setCaption("Rep Item Balance");
		center();
		
		Grid<RepItemBalance> grid = new Grid<>();
		grid.addColumn(e -> e.getRefPlace().getName()).setCaption("Place");
		grid.addColumn(e -> e.getRefItem().getName()).setCaption("Item");
		grid.addColumn(RepItemBalance::getQuantity).setCaption("Quantity");
		grid.setItems(repItemBalanceRepository.findAll());
		
		setContent(grid);
	}
	
}
