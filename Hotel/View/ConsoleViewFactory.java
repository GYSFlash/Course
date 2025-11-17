package Hotel.View;

public class ConsoleViewFactory extends ViewFactory {
    @Override
    public BaseView createView(MenuType menuType) {
        switch (menuType) {
            case CLIENT: return ClientView.getInstance();
            case ROOM: return RoomView.getInstance();
            case BOOKING: return BookingView.getInstance();
            case SERVICE: return ServiceView.getInstance();
            case OTHER: return OtherView.getInstance();
            default: throw new IllegalArgumentException("Неизвестный menu type: " + menuType);
        }
    }
}
