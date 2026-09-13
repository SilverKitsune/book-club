package com.bookclub.view;

import com.bookclub.entity.Book;
import com.bookclub.service.BookService;
import com.bookclub.view.window.BookWindow;
import com.vaadin.flow.component.grid.Grid;
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
        grid.addColumn(Book::getStatus).setHeader("Статус");

        grid.setItems(bookService.findAll());
        grid.setSizeFull();
        grid.addItemClickListener(bookItemClickEvent -> {
            BookWindow window = new BookWindow(bookItemClickEvent.getItem(), this.bookService);
            window.open();
            window.addDialogCloseActionListener(dialogCloseActionEvent -> {
                refreshGrid();
                window.close();
            });
        });

        add(grid);
    }

    private void refreshGrid() {
        grid.setItems(bookService.findAll());
    }
}
