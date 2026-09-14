package com.bookclub.view;

import com.bookclub.entity.Meeting;
import com.bookclub.service.MeetingService;
import com.bookclub.view.window.MeetingWindow;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "meeting", layout = NavigationLayout.class)
@PermitAll
public class MeetingView extends VerticalLayout {
    private final MeetingService meetingService;
    private final Grid<Meeting> grid = new Grid<>(Meeting.class, false);

    public MeetingView(MeetingService meetingService) {
        this.meetingService = meetingService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        grid.addColumn(Meeting::getDateTime).setHeader("Дата проведения");
        grid.addColumn(Meeting::getStatus).setHeader("Статус");
        grid.addColumn(Meeting::getPlace).setHeader("Место");

        grid.setItems(meetingService.findAll());
        grid.setSizeFull();
        grid.addItemClickListener(e -> openWindow(e.getItem()));
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        Button addNew = new Button("Запланировать встречу", e -> openWindow(null));
        addNew.setWidthFull();
        addNew.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        add(addNew, grid);
    }

    private void openWindow(Meeting meeting) {
        MeetingWindow window = new MeetingWindow(meeting, this.meetingService, this::refreshGrid);
        window.open();
        window.addDialogCloseActionListener(dialogCloseActionEvent -> {
            refreshGrid();
            window.close();
        });
    }

    private void refreshGrid() {
        grid.setItems(meetingService.findAll());
    }
}
