package FlowersShop;

public class Pion extends Flowers{
    private String color;
    private String type;
    public Pion(){
        super(200,"Пион");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Пионы{" +
                "цвет: '" + color + '\'' +
                ", тип: '" + type + '\'' +
                ", количество: " + getCount() +
                '}';
    }
}
