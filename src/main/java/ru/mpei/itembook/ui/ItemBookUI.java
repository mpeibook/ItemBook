package ru.mpei.itembook.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringUI
public class ItemBookUI extends UI {
	
	@Autowired
	ApplicationContext ctx;
	
	@Override
	protected void init(VaadinRequest request) {
		MenuBar menuBar = new MenuBar();
		MenuItem menuReferences = menuBar.addItem("References", null);
		menuReferences.addItem("Items", e -> showRefItemListWindow());
		menuReferences.addItem("Places", e -> showRefPlaceListWindow());
		MenuItem menuDocuments = menuBar.addItem("Documents", null);
		menuDocuments.addItem("Moves", e -> showDocMoveListWindow());
//		MenuItem menuRegisters = menuBar.addItem("Registers", null);
//		menuRegisters.addItem("Items", null);
		MenuItem menuReports = menuBar.addItem("Reports", null);
		menuReports.addItem("Balance", e -> showRepItemBalanceWindow());
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(menuBar);
		setContent(layout);
	}
	
	private void showRefItemListWindow() {
		UI.getCurrent().addWindow(ctx.getBean(RefItemListWindow.class));
	}
	
	private void showRefPlaceListWindow() {
		UI.getCurrent().addWindow(ctx.getBean(RefPlaceListWindow.class));
	}
	
	private void showDocMoveListWindow() {
		UI.getCurrent().addWindow(ctx.getBean(DocMoveListWindow.class));
	}
	
	private void showRepItemBalanceWindow() {
		UI.getCurrent().addWindow(ctx.getBean(RepItemBalanceWindow.class));
	}
}
