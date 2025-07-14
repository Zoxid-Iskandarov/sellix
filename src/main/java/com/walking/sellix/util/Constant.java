package com.walking.sellix.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Constant {
    public static final List<String> CITIES = List.of(
            "Ташкент", "Самарканд", "Наманган", "Навои",
            "Каттакурган", "Кашкадарья", "Каракалпакстан",
            "Джизак", "Бухара", "Андижан"
    );

    public static final List<Integer> PAGE_SIZES = List.of(10, 20, 40, 80, 100);
}
