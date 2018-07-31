package com.squirrel.nzhulkov.squirrel;

// если мы хотим манипулировать вьюшками в другом приложении, или Java например, нам нуэжен интерфейс морды
// Чтобы легко переносить программу из одного окружение в другое
// разделяем логику, вьюшку, контроллер
public interface ICanvsView {
    void drawCircle(SimpleCircle simpleCircle);
    void redraw();

    void showMessage(String text);
}
