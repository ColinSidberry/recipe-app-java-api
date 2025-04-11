package app;

import controller.RecipeController;

public class App {
  public static void main(String[] args) {
    RecipeController controller = new RecipeController();
    controller.start();
  }
}