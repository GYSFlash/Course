package Laptop;

public class Motherboard implements IProductPart, ILineStep{
    @Override
    public String getName(){
        return "Материнская плата";
    }
    @Override
    public IProductPart buildProductPart(){
        System.out.println("Сборка материнской платы");
        return new Motherboard();
    }
}
