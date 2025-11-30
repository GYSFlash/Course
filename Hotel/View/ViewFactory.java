package Hotel.View;



public abstract class ViewFactory {
    public abstract BaseView createView(MenuType menuType);

    public static ViewFactory getFactory(String type) {
        if ("console".equalsIgnoreCase(type)) {
            return new ConsoleViewFactory();
        }
        throw new IllegalArgumentException("Unknown factory: " + type);
    }
}
