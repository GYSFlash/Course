package FlowersShop;

public class Main {
    public static void main(String[] args) {
        Rose rose = new Rose();
        Pion pion = new Pion();
        Tulip tulip = new Tulip();
        rose.setCount(5);
        rose.setColor("Красный");
        rose.setThorn(false);
        pion.setCount(4);
        pion.setColor("Оранжевый");
        pion.setType("Японские");
        tulip.setCount(4);
        tulip.setColor("Белый");
        tulip.setType("Махровые");
        double sum =0;
        Flowers[] flowers = new Flowers[]{rose,pion,tulip};
        for( Flowers flower : flowers){
            sum+=flower.getPrice()*flower.getCount();
        }

        System.out.println("Состав букета:");
        for(Flowers flower : flowers){
            System.out.println(flower.toString());
        }
        System.out.println("Стоимость букета: " + sum);
    }
}
