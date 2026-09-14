package com.bookclub.view.window;

import com.bookclub.entity.Book;
import com.bookclub.entity.BookStatusEnum;
import com.bookclub.entity.User;
import com.bookclub.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class BookWindow extends Dialog {

    private final BookService bookService;
    private Book book;
    private final User currentUser;
    private final Runnable onClose;

    private final TextField name = new TextField("Название");
    private final TextField author = new TextField("Автор");
    private final TextField genre = new TextField("Жанр");
    private final TextArea annotation = new TextArea("Аннотация");
    private final ComboBox<BookStatusEnum> status = new ComboBox<>("Статус");

    public BookWindow(Book book, User currentUser, BookService bookService, Runnable onClose) {
        this.bookService = bookService;
        this.book = book;
        this.currentUser = currentUser;
        this.onClose = onClose;

        setHeaderTitle(book == null ? "Новая книга" : "Редактирование книги");
        setWidth("500px");

        Button closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> this.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        getHeader().add(closeButton);

        name.setRequired(true);
        name.setWidthFull();
        author.setWidthFull();
        genre.setWidthFull();
        annotation.setWidthFull();
        annotation.setHeight("100px");
        status.setItems(BookStatusEnum.values());
        status.setItemLabelGenerator(BookStatusEnum::getName);
        status.setWidthFull();

        if (book != null) {
            name.setValue(book.getName() != null ? book.getName() : "");
            author.setValue(book.getAuthor() != null ? book.getAuthor() : "");
            genre.setValue(book.getGenre() != null ? book.getGenre() : "");
            annotation.setValue(book.getAnnotation() != null ? book.getAnnotation() : "");
            status.setValue(book.getStatus());
        }

        FormLayout form = new FormLayout(name, author, genre, status, annotation);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("400px", 2)
        );
        form.setColspan(annotation, 2);

        add(form);

        Button cancel = new Button("Отмена", e -> close());
        Button save = new Button("Сохранить", e -> save());
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        if (book != null) {
            Button delete = new Button("Удалить", e -> deleteBook());
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            getFooter().add(delete, cancel, save);
        } else {
            getFooter().add(cancel, save);
        }

    }

    private void deleteBook() {
        bookService.deleteById(book.getId());
        onClose.run();
        close();
    }

    private void save() {
        if (name.isEmpty()) {
            name.setInvalid(true);
            name.setErrorMessage("Название обязательно");
            return;
        }
        if (book == null) {
            book = new Book();
            book.setUser(currentUser);
        }
        book.setName(name.getValue());
        book.setAuthor(author.getValue());
        book.setGenre(genre.getValue());
        book.setAnnotation(annotation.getValue());
        book.setStatus(status.getValue());

        bookService.save(book);
        onClose.run();
        close();
    }
}