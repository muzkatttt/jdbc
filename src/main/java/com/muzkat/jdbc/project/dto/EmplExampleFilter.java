package com.muzkat.jdbc.project.dto;

public record EmplExampleFilter(int limit, int offset, String firstName, String secondName) {
    // record позволяет создать объект с полями, которые перечислены в круглых скобках
    // при этом сразу же создает и конструктор и геттеры, сеттеров нет, так как объекты
    // типа record являются immutable
}
