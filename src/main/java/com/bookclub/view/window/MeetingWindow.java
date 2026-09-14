package com.bookclub.view.window;

import com.bookclub.entity.Meeting;
import com.bookclub.service.MeetingService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextField;

public class MeetingWindow extends Dialog {

    private final MeetingService meetingService;
    private final Meeting meeting;
    private final Runnable onClose;

    private final DateTimePicker dateTime = new DateTimePicker("Дата и время");
    private final TextField place = new TextField("Место");
    private final TextField status = new TextField("Статус");

    public MeetingWindow(Meeting meeting, MeetingService meetingService, Runnable onClose) {
        this.meetingService = meetingService;
        this.meeting = meeting != null ? meeting : new Meeting();
        this.onClose = onClose;

        setHeaderTitle(meeting == null ? "Новая встреча" : "Редактирование встречи");
        setWidth("500px");

        Button closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> this.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        getHeader().add(closeButton);

        dateTime.setRequiredIndicatorVisible(true);
        dateTime.setWidthFull();
        place.setWidthFull();
        status.setWidthFull();

        if (meeting != null) {
            dateTime.setValue(meeting.getDateTime());
            place.setValue(meeting.getPlace() != null ? meeting.getPlace() : "");
            status.setValue(meeting.getStatus() != null ? meeting.getStatus() : "");
        }

        FormLayout form = new FormLayout(dateTime, place, status);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        add(form);

        Button cancel = new Button("Отмена", e -> close());
        Button save = new Button("Сохранить", e -> save());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        if (meeting != null) {
            Button delete = new Button("Удалить", e -> deleteMeeting());
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            getFooter().add(delete, cancel, save);
        } else {
            getFooter().add(cancel, save);
        }
    }

    private void deleteMeeting() {
        meetingService.deleteById(meeting.getId());
        onClose.run();
        close();
    }

    private void save() {
        if (dateTime.isEmpty()) {
            dateTime.setInvalid(true);
            dateTime.setErrorMessage("Дата обязательна");
            return;
        }

        meeting.setDateTime(dateTime.getValue());
        meeting.setPlace(place.getValue());
        meeting.setStatus(status.getValue());

        meetingService.save(meeting);
        onClose.run();
        close();
    }
}