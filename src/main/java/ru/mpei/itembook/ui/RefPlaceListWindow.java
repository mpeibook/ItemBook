package ru.mpei.itembook.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;

import ru.mpei.itembook.model.RefPlace;
import ru.mpei.itembook.repository.RefPlaceRepository;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class RefPlaceListWindow extends Window {
	
	@Autowired
	private ApplicationContext ctx;
	
	RefPlaceRepository refPlaceRepository;
	Grid<RefPlace> grid;
	
	public RefPlaceListWindow(RefPlaceRepository refPlaceRepository) {
		this.refPlaceRepository = refPlaceRepository;
		
		setCaption("Ref Place List");
		center();
		
		Button add = new Button("Add", VaadinIcons.PLUS);
		add.addClickListener(e -> showRefPlaceFormWindow(new RefPlace()));
		
		grid = new Grid<>();
		grid.addColumn(e -> "Edit", new ButtonRenderer<>(e -> showRefPlaceFormWindow(e.getItem())));
		grid.addColumn(RefPlace::getId).setCaption("Id");
		grid.addColumn(RefPlace::getCode).setCaption("Code");
		grid.addColumn(RefPlace::getName).setCaption("Name");
		grid.setItems(refPlaceRepository.findAll());
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(add, grid);
		setContent(layout);
	}
	
	private void showRefPlaceFormWindow(RefPlace refPlace) {
		RefPlaceFormWindow refPlaceFormWindow = ctx.getBean(RefPlaceFormWindow.class);
		refPlaceFormWindow.setRefPlace(refPlace);
		refPlaceFormWindow.addCloseListener(e -> {
			if (((RefPlaceFormWindow) e.getWindow()).isDataChanged()) {
				grid.setItems(refPlaceRepository.findAll());
			}
		});
		UI.getCurrent().addWindow(refPlaceFormWindow);
	}
}
