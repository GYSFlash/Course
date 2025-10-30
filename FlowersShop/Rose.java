package FlowersShop;

public class Rose extends Flowers{
    private String color;
    private boolean thorn;
    public Rose(){
        super(300, "Роза");
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isThorn() {
        return thorn;
    }

    public void setThorn(boolean thorn) {
        this.thorn = thorn;
    }
    public String Thorn(){
        if(isThorn()){
            return "Роза с колючками";
        }else{
            return "Роза без колючек";
        }
    }

    @Override
    public String toString() {
        return "Розы{" +
                "Цвет:'" + color + '\'' +
                ", " +  Thorn() +
                ", количество: " + getCount() +
                '}';
    }
}