package com.westernyey.Flopy.ui.cardModel;

public class CardModel {
    private String title;
    private String description;


        public CardModel(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }


