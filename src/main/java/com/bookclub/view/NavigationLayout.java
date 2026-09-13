package com.bookclub.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


@PermitAll
public class NavigationLayout extends AppLayout implements RouterLayout {

    public NavigationLayout() {
        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("Книжный клуб");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(toggle, title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.setPadding(true);
        addToNavbar(header);

        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Встречи", MeetingView.class));
        nav.addItem(new SideNavItem("Книги", BookView.class));


        Button logout = new Button("Выйти", e -> logout());
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR);
        logout.setWidthFull();

        VerticalLayout drawerContent = new VerticalLayout(nav, logout);
        drawerContent.setSizeFull();
        drawerContent.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        drawerContent.setPadding(false);
        drawerContent.setSpacing(false);

        addToDrawer(drawerContent);
    }

    private void logout() {
        HttpServletRequest request = VaadinServletRequest.getCurrent().getHttpServletRequest();
        HttpServletResponse response = VaadinServletResponse.getCurrent().getHttpServletResponse();
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }
}