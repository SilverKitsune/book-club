package com.bookclub.entity;

public enum UserRoleEnum {

    ADMIN("Администратор"),
    USER("Участник");

    private final String name;

    UserRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
