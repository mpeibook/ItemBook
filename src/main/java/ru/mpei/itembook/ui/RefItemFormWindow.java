package ru.mpei.itembook.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import ru.mpei.itembook.model.RefItem;
import ru.mpei.itembook.repository.RefItemRepository;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class RefItemFormWindow extends Window {
	private TextField id = new TextField("Id");
	private TextField code = new TextField("Code");
	private TextField name = new TextField("Name");

	RefItemRepository refItemRepository;
	RefItem refItem;
	Binder<RefItem> binder = new Binder<>(RefItem.class);
	
	Button saveButton = new Button("Save", VaadinIcons.CHECK);
	Button cancelButton = new Button("Cancel");
	Button deleteButton = new Button("Delete", VaadinIcons.TRASH);
	
	private boolean dataChanged = false;
	
	public RefItemFormWindow(RefItemRepository refItemRepository) {
		this.refItemRepository = refItemRepository;
		
		setCaption("Ref Item Form");
		center();
		
		id.setReadOnly(true);
		binder.forField(id).withConverter(Integer::valueOf, String::valueOf).bind(RefItem::getId, RefItem::setId);
		binder.forField(code).bind(RefItem::getCode, RefItem::setCode);
		binder.forField(name).bind(RefItem::getName, RefItem::setName);
		
		CssLayout actions = new CssLayout(saveButton, cancelButton, deleteButton);
		
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		saveButton.addClickListener(e -> save());
		cancelButton.addClickListener(e -> cancel());
		deleteButton.addClickListener(e -> delete());
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.addComponents(id, code, name, actions);
		setContent(layout);
	}
	
	public void setRefItem(RefItem refItem) {
		this.refItem = refItem;
		binder.setBean(refItem);
	}
	
	private void save() {
		refItemRepository.save(refItem);
		dataChanged = true;
		close();
	}
	
	private void cancel() {
		close();
	}
	
	private void delete() {
		refItemRepository.delete(refItem);
		dataChanged = true;
		close();
	}
	
	public boolean isDataChanged() {
		return dataChanged;
	}
}
