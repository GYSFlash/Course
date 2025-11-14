package Laptop;

public class Laptop implements IAssemblyLine{
    private ILineStep Monitor;
    private ILineStep Motherboard;
    private ILineStep Case;

    public Laptop(ILineStep monitorStep,ILineStep motherboardStep, ILineStep caseStep){
        this.Monitor = monitorStep;
        this.Motherboard = motherboardStep;
        this.Case = caseStep;
    }
    @Override
    public IProduct assembleProduct(IProduct product){
        System.out.println("Начало сборки ноутбука");

        System.out.println("Первый шаг сборки");
        IProductPart casePart = Case.buildProductPart();
        product.installFirstPart(casePart);

        System.out.println("Второй шаг сборки");
        IProductPart motherboardPart = Motherboard.buildProductPart();
        product.installSecondPart(motherboardPart);

        System.out.println("Третий шаг сборки");
        IProductPart monitorPart = Monitor.buildProductPart();
        product.installThirdPart(monitorPart);

        System.out.println("Сборка ноутбука завершена");
        return product;
    }

}
