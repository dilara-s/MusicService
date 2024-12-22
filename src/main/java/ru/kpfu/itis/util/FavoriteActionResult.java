package ru.kpfu.itis.util;

public class FavoriteActionResult {
    private boolean success;
    private boolean isFavourite; // Новый статус избранного

    public FavoriteActionResult(boolean success, boolean isFavourite) {
        this.success = success;
        this.isFavourite = isFavourite;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
}
