package ru.mpei.itembook.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import ru.mpei.itembook.model.DocMove;
import ru.mpei.itembook.model.RefPlace;
import ru.mpei.itembook.model.DocMoveTab;
import ru.mpei.itembook.model.RefItem;
import ru.mpei.itembook.repository.DocMoveRepository;
import ru.mpei.itembook.repository.DocMoveTabRepository;
import ru.mpei.itembook.repository.RefItemRepository;
import ru.mpei.itembook.repository.RefPlaceRepository;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class DocMoveFormWindow extends Window {
	private TextField id = new TextField("Id");
	private DateField date = new DateField("Date");
	private TextField number = new TextField("Number");
	private ComboBox<RefPlace> placeFrom = new ComboBox<>("Place from");
	private ComboBox<RefPlace> placeTo = new ComboBox<>("Place to");
	private Button addRowButton = new Button("Add");
	private Grid<DocMoveTab> docMoveTabGrid = new Grid<>();
	private ComboBox<RefItem> refItem = new ComboBox<>();

	DocMoveRepository docMoveRepository;
	DocMove docMove;
	Binder<DocMove> binder = new Binder<>(DocMove.class);
	List<DocMoveTab> docMoveTabData = new ArrayList<>();
	
	Button saveButton = new Button("Save", VaadinIcons.CHECK);
	Button cancelButton = new Button("Cancel");
	Button deleteButton = new Button("Delete", VaadinIcons.TRASH);
	
	private boolean dataChanged = false;
	
	@Autowired
	private RefPlaceRepository placeRepository;
	
	@Autowired
	private DocMoveTabRepository docMoveTabRepository;
	
	@Autowired
	private RefItemRepository refItemRepository;
	
	public DocMoveFormWindow(DocMoveRepository repository) {
		this.docMoveRepository = repository;
		
		id.setReadOnly(true);
		binder.forField(id).withConverter(Integer::valueOf, String::valueOf).bind(DocMove::getId, DocMove::setId);
		binder.forField(date).bind(DocMove::getDocDate, DocMove::setDocDate);
		binder.forField(number).bind(DocMove::getDocNum, DocMove::setDocNum);
		binder.forField(placeFrom).bind(DocMove::getRefPlaceFrom, DocMove::setRefPlaceFrom);
		binder.forField(placeTo).bind(DocMove::getRefPlaceTo, DocMove::setRefPlaceTo);
		
		setCaption("Doc Move Form");
		setWidth("50%");
		setHeight("50%");
		center();
		
		placeFrom.setItemCaptionGenerator(RefPlace::getName);
		placeTo.setItemCaptionGenerator(RefPlace::getName);
		
		docMoveTabGrid.setSizeFull();
		docMoveTabGrid.addColumn(DocMoveTab::getId).setCaption("Id");
		
		Binder<DocMoveTab> rowBinder = docMoveTabGrid.getEditor().getBinder();
		
		refItem.setItemCaptionGenerator(RefItem::getName);
		Binding<DocMoveTab, RefItem> rowItemBinding = rowBinder.forField(refItem).bind(DocMoveTab::getRefItem, DocMoveTab::setRefItem);
		docMoveTabGrid.addColumn(c -> (c.getRefItem() != null ? c.getRefItem().getName() : null)).setCaption("Item").setEditorBinding(rowItemBinding);
		
		TextField rowQuantity = new TextField();
		Binding<DocMoveTab, Integer> rowQuantityBinding = rowBinder.forField(rowQuantity).withConverter(Integer::valueOf, String::valueOf).bind(DocMoveTab::getQuantity, DocMoveTab::setQuantity);
		docMoveTabGrid.addColumn(DocMoveTab::getQuantity).setCaption("Quntity").setEditorBinding(rowQuantityBinding);
		
		docMoveTabGrid.addColumn(i -> "Delete", new ButtonRenderer<>(e -> {
			docMoveTabData.remove(e.getItem());
			docMoveTabGrid.setItems(docMoveTabData);
		}));
		
		docMoveTabGrid.getEditor().setEnabled(true);
		
		addRowButton.addClickListener(e -> {
			docMoveTabData.add(new DocMoveTab(docMove, null, 1));
			docMoveTabGrid.setItems(docMoveTabData);
		});
		
		CssLayout actions = new CssLayout(saveButton, cancelButton, deleteButton);
		
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		saveButton.addClickListener(e -> save());
		cancelButton.addClickListener(e -> cancel());
		deleteButton.addClickListener(e -> delete());
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		
		FormLayout formLayout = new FormLayout();
		formLayout.addComponents(id, date, number, placeFrom, placeTo);
		
		layout.addComponents(formLayout, addRowButton, docMoveTabGrid, actions);
		layout.setExpandRatio(docMoveTabGrid, 1.0f);
		setContent(layout);
	}
	
	public void setDocument(DocMove docMove) {
		this.docMove = docMove;
		placeFrom.setItems(placeRepository.findAll());
		placeTo.setItems(placeRepository.findAll());
		
		if (docMove.getId() != null) {
			docMoveTabData.addAll(docMoveTabRepository.findByDocMove(docMove));
		}
		
		docMoveTabGrid.setItems(docMoveTabData);
		refItem.setItems(refItemRepository.findAll());
		binder.setBean(docMove);
	}
	
	private void save() {
		docMoveRepository.save(docMove);
		
		List<DocMoveTab> oldList = docMoveTabRepository.findByDocMove(docMove);
		List<DocMoveTab> newList = docMoveTabData;
		List<DocMoveTab> delList = new ArrayList<>();
		
		// Find deleted rows
		for (int i = 0; i < oldList.size(); i++) {
			boolean isDeleted = true;
			for (int j = 0; j < newList.size(); j++) {
				if (oldList.get(i).getId() == newList.get(j).getId()) {
					isDeleted = false;
					break;
				}
			}
			if (isDeleted) {
				delList.add(oldList.get(i));
			}
		}
		
		docMoveTabRepository.delete(delList);
		docMoveTabRepository.save(newList);
		
		dataChanged = true;
		close();
	}
	
	private void cancel() {
		close();
	}
	
	private void delete() {
		docMoveTabRepository.delete(docMoveTabRepository.findByDocMove(docMove));
		docMoveRepository.delete(docMove);
		dataChanged = true;
		close();
	}
	
	public boolean isDataChanged() {
		return dataChanged;
	}
}
