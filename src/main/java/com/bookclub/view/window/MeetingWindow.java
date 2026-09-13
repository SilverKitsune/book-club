package com.bookclub.view.window;

import com.bookclub.entity.Meeting;
import com.bookclub.service.MeetingService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

public class MeetingWindow extends Dialog {

    private final MeetingService meetingService;
    private final Meeting meeting;

    private final DateTimePicker dateTime = new DateTimePicker("Дата и время");
    private final TextField place = new TextField("Место");
    private final TextField status = new TextField("Статус");

    public MeetingWindow(Meeting meeting, MeetingService meetingService) {
        this.meetingService = meetingService;
        this.meeting = meeting != null ? meeting : new Meeting();

        setHeaderTitle(meeting == null ? "Новая встреча" : "Редактирование встречи");
        setWidth("500px");

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

        getFooter().add(cancel, save);
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
        close();
    }
}