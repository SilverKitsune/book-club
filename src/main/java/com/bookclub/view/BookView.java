package com.bookclub.view;

import com.bookclub.entity.Book;
import com.bookclub.service.BookService;
import com.bookclub.view.window.BookWindow;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "book", layout = NavigationLayout.class)
@PermitAll
public class BookView extends VerticalLayout
{
    private final BookService bookService;
    private final Grid<Book> grid = new Grid<>(Book.class, false);

    public BookView(BookService bookService) {
        this.bookService = bookService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        grid.addColumn(Book::getName).setHeader("Название");
        grid.addColumn(Book::getAuthor).setHeader("Автор");
        grid.addColumn(Book::getGenre).setHeader("Жанр");
        grid.addColumn(book -> book.getStatus().getName()).setHeader("Статус");
        grid.addColumn(book -> book.getUser().getLogin()).setHeader("Добавил");

        grid.setItems(bookService.findAllWithUser());
        grid.setSizeFull();
        grid.addItemClickListener(bookItemClickEvent -> openWindow(bookItemClickEvent.getItem()));
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        Button addNew = new Button("Добавить книгу", e -> openWindow(null));
        addNew.setWidthFull();
        addNew.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        add(addNew, grid);
    }

    private void openWindow(Book book) {
        BookWindow window = new BookWindow(book, this.bookService, this::refreshGrid);
        window.open();
    }

    private void refreshGrid() {
        grid.setItems(bookService.findAllWithUser());
    }
}
