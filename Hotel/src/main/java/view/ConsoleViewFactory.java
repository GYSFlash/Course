package view;

public class ConsoleViewFactory extends ViewFactory {
    @Override
    public BaseView createView(MenuType menuType) {
        switch (menuType) {
            case CLIENT: return new ClientView();
            case ROOM: return new RoomView();
            case BOOKING: return new BookingView();
            case SERVICE: return new ServiceView();
            case OTHER: return new OtherView();
            default: throw new IllegalArgumentException("Неизвестный menu type: " + menuType);
        }
    }
}
