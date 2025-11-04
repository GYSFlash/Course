package Laptop;

public class LaptopAssemble implements IProduct {
    private IProductPart Monitor;
    private IProductPart Motherboard;
    private IProductPart Case;

    @Override
    public void installFirstPart(IProductPart part){
        this.Case = part;
        System.out.println("Собран корпус ноутбука");

    }
    @Override
    public void installSecondPart(IProductPart part){
        this.Motherboard = part;
        System.out.println("Установлена: " + part.getName());

    }
    @Override
    public void installThirdPart(IProductPart part){
        this.Monitor = part;
        System.out.println("Установлен: " + part.getName());
    }
    @Override
    public String toString(){
        return "Собранный ноутбук состоит из: " +
                Case.getName() + ", " +
                Motherboard.getName() + ", " +
                Monitor.getName();
    }
}
