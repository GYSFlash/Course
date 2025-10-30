
package FlowersShop;

public class Tulip extends Flowers{
    private String color;
    private String type;
    public Tulip(){
        super(130,"Тюльпан");
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Тюльпаны{" +
                "цвет:'" + color + '\'' +
                ", тип:'" + type + '\'' +
                ", количество: " + getCount() +
                '}';
    }
}

