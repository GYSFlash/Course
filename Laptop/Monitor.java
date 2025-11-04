package Laptop;

public class Monitor implements ILineStep, IProductPart{
    @Override
    public String getName(){
        return "Монитор";
    }
    @Override
    public IProductPart buildProductPart(){
        System.out.println("Сборка монитора");
        return new Monitor();
    }
}
