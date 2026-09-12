package com.bookclub.entity;


public enum BookStatusEnum {
    READ("Прочитана"),
    UNREAD("Не прочитана"),
    PLANNED("Запланирована");
    private final String name;

    BookStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
