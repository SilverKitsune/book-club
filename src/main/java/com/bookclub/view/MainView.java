package com.bookclub.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "main", layout = NavigationLayout.class)
@PageTitle("Книжный клуб")
@PermitAll
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        add(new H2("Добро пожаловать в книжный клуб"));
    }
}