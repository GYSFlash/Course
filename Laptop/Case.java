package Laptop;

public class Case implements IProductPart,ILineStep{
    @Override
    public String getName(){
        return "Корпус";
    }
    @Override
    public IProductPart buildProductPart(){
        System.out.println("Сборка корпуса");
        return new Case();
    }

}
