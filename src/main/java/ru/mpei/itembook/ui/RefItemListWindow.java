package ru.mpei.itembook.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.UI;

import ru.mpei.itembook.model.RefItem;
import ru.mpei.itembook.repository.RefItemRepository;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class RefItemListWindow extends Window {
	
	@Autowired
	private ApplicationContext ctx;
	
	RefItemRepository refItemRepository;
	Grid<RefItem> grid;
	
	public RefItemListWindow(RefItemRepository refItemRepository) {
		this.refItemRepository = refItemRepository;
		
		setCaption("Ref Item List");
		center();
		
		Button add = new Button("Add", VaadinIcons.PLUS);
		add.addClickListener(e -> showRefItemFormWindow(new RefItem()));
		
		grid = new Grid<>();
		grid.addColumn(e -> "Edit", new ButtonRenderer<>(e -> showRefItemFormWindow(e.getItem())));
		grid.addColumn(RefItem::getId).setCaption("Id");
		grid.addColumn(RefItem::getCode).setCaption("Code");
		grid.addColumn(RefItem::getName).setCaption("Name");
		grid.setItems(refItemRepository.findAll());
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(add, grid);
		setContent(layout);
	}
	
	private void showRefItemFormWindow(RefItem refItem) {
		RefItemFormWindow refItemFormWindow = ctx.getBean(RefItemFormWindow.class);
		refItemFormWindow.setRefItem(refItem);
		refItemFormWindow.addCloseListener(e -> {
			if (((RefItemFormWindow) e.getWindow()).isDataChanged()) {
				grid.setItems(refItemRepository.findAll());
			}
		});
		UI.getCurrent().addWindow(refItemFormWindow);
	}
}
