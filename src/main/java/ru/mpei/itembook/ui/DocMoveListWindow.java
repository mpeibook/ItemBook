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

import ru.mpei.itembook.model.DocMove;
import ru.mpei.itembook.repository.DocMoveRepository;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class DocMoveListWindow extends Window {
	
	@Autowired
	ApplicationContext ctx;
	
	DocMoveRepository docMoveRepository;
	Grid<DocMove> grid;
	
	public DocMoveListWindow(DocMoveRepository docMoveRepository) {
		this.docMoveRepository = docMoveRepository;
		setCaption("Doc Move List");
		center();
		
		grid = new Grid<>();
		grid.setItems(docMoveRepository.findAll());
		grid.addColumn(e -> "Edit", new ButtonRenderer<>(e -> showDocMoveFormWindow(e.getItem())));
		grid.addColumn(DocMove::getId).setCaption("Id");
		grid.addColumn(DocMove::getDocDate).setCaption("Date");
		grid.addColumn(DocMove::getDocNum).setCaption("Number");
		grid.addColumn(place -> (place.getRefPlaceFrom() != null ? place.getRefPlaceFrom().getName() : null)).setCaption("Place from");
		grid.addColumn(place -> (place.getRefPlaceTo() != null ? place.getRefPlaceTo().getName() : null)).setCaption("Place to");
		
		Button add = new Button("Add", VaadinIcons.PLUS);
		add.addClickListener(e -> showDocMoveFormWindow(new DocMove()));
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(add, grid);
		setContent(layout);
	}
	
	private void showDocMoveFormWindow(DocMove docMove) {
		DocMoveFormWindow docMoveFormWindow = ctx.getBean(DocMoveFormWindow.class);
		docMoveFormWindow.setDocument(docMove);
		docMoveFormWindow.addCloseListener(e -> {
			if (((DocMoveFormWindow) e.getWindow()).isDataChanged()) {
				grid.setItems(docMoveRepository.findAll());
			}
		});
		UI.getCurrent().addWindow(docMoveFormWindow);
	}
}
