package com.bookclub.view.window;

import com.bookclub.entity.Book;
import com.bookclub.entity.BookStatusEnum;
import com.bookclub.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class BookWindow extends Dialog {

    private final BookService bookService;
    private final Book book;

    private final TextField name = new TextField("Название");
    private final TextField author = new TextField("Автор");
    private final TextField genre = new TextField("Жанр");
    private final TextArea annotation = new TextArea("Аннотация");
    private final ComboBox<BookStatusEnum> status = new ComboBox<>("Статус");
    private final IntegerField rating = new IntegerField("Оценка");

    public BookWindow(Book book, BookService bookService) {
        this.bookService = bookService;
        this.book = book != null ? book : new Book();

        setHeaderTitle(book == null ? "Новая книга" : "Редактирование книги");
        setWidth("500px");

        // Заполняем поля
        name.setRequired(true);
        name.setWidthFull();
        author.setWidthFull();
        genre.setWidthFull();
        annotation.setWidthFull();
        annotation.setHeight("100px");
        status.setItems(BookStatusEnum.values());
        status.setItemLabelGenerator(BookStatusEnum::getName);
        status.setWidthFull();
        rating.setMin(1);
        rating.setMax(5);
        rating.setWidthFull();

        // Если редактируем — заполняем текущими данными
        if (book != null) {
            name.setValue(book.getName() != null ? book.getName() : "");
            author.setValue(book.getAuthor() != null ? book.getAuthor() : "");
            genre.setValue(book.getGenre() != null ? book.getGenre() : "");
            annotation.setValue(book.getAnnotation() != null ? book.getAnnotation() : "");
            status.setValue(book.getStatus());
        }

        FormLayout form = new FormLayout(name, author, genre, status, rating, annotation);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("400px", 2)
        );
        form.setColspan(annotation, 2);

        add(form);

        // Кнопки
        Button cancel = new Button("Отмена", e -> close());
        Button save = new Button("Сохранить", e -> save());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        getFooter().add(cancel, save);
    }

    private void save() {
        if (name.isEmpty()) {
            name.setInvalid(true);
            name.setErrorMessage("Название обязательно");
            return;
        }

        book.setName(name.getValue());
        book.setAuthor(author.getValue());
        book.setGenre(genre.getValue());
        book.setAnnotation(annotation.getValue());
        book.setStatus(status.getValue());
        // rating не входит в сущность Book, но если добавишь — раскомментируй
        // book.setRating(rating.getValue());

        bookService.save(book);
        close();
    }
}