package com.fridge.core.domain

enum class Category(val displayName: String) {
    NONE("Pick category"),
    FOOD("Food"),
    DRINK("Drink"),
    FRUIT("Fruit"),
    VEGETABLE("Vegetable"),
    MEAT("Meat"),
    DAIRY("Dairy"),
    SEAFOOD("Seafood"),
    FROZEN("Frozen"),
    SNACK("Snack"),
    DESSERT("Dessert"),
    SAUCE("Sauce"),
    GRAIN("Grain"),
    SPICE("Spice"),
    CONDIMENT("Condiment"),
    OTHER("Other");
}