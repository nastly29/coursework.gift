package main;

import menu.menuForGift.*;
import menu.menuForSweetsBase.*;
import menu.*;

public class Main{
    public static void main(String[] args) {
        MenuItem mainMenu = new MenuGroup("Головне меню", true)
                .addItem(new CreateGift())
                .addItem(new MenuGroup("Меню дій з подарунком")
                        .addItem(new AddSweetToGift())
                        .addItem(new DisplayGiftInfo())
                        .addItem(new FindSweetBySugarInGift())
                        .addItem(new RemoveSweetFromGift())
                        .addItem(new SortSweetByWeightInGift()))
                .addItem(new MenuGroup("Меню для керування базою даних солодощів")
                        .addItem(new AddNewSweetToBase())
                        .addItem(new DeleteSweetFromBase())
                        .addItem(new DisplaySweetsBase())
                        .addItem(new FindSweetByPrice()));
        mainMenu.execute();
    }
}