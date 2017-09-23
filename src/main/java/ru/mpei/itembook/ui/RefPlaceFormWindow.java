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

import ru.mpei.itembook.model.RefPlace;
import ru.mpei.itembook.repository.RefPlaceRepository;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class RefPlaceFormWindow extends Window {
	private TextField id = new TextField("Id");
	private TextField code = new TextField("Code");
	private TextField name = new TextField("Name");

	RefPlaceRepository refPlaceRepository;
	RefPlace refPlace;
	Binder<RefPlace> binder = new Binder<>(RefPlace.class);
	
	Button saveButton = new Button("Save", VaadinIcons.CHECK);
	Button cancelButton = new Button("Cancel");
	Button deleteButton = new Button("Delete", VaadinIcons.TRASH);
	
	private boolean dataChanged = false;
	
	public RefPlaceFormWindow(RefPlaceRepository refPlaceRepository) {
		this.refPlaceRepository = refPlaceRepository;
		
		setCaption("Ref Place Form");
		center();
		
		id.setReadOnly(true);
		binder.forField(id).withConverter(Integer::valueOf, String::valueOf).bind(RefPlace::getId, RefPlace::setId);
		binder.forField(code).bind(RefPlace::getCode, RefPlace::setCode);
		binder.forField(name).bind(RefPlace::getName, RefPlace::setName);
		
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
	
	public void setRefPlace(RefPlace refPlace) {
		this.refPlace = refPlace;
		binder.setBean(refPlace);
	}
	
	private void save() {
		refPlaceRepository.save(refPlace);
		dataChanged = true;
		close();
	}
	
	private void cancel() {
		close();
	}
	
	private void delete() {
		refPlaceRepository.delete(refPlace);
		dataChanged = true;
		close();
	}
	
	public boolean isDataChanged() {
		return dataChanged;
	}
}
