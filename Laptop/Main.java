package Laptop;

public class Main {

    public static void main(String[] args) {

        Laptop laptop = new Laptop(new Monitor(), new Motherboard(), new Case());

        IProduct laptopAssembly = new LaptopAssemble();

        IProduct laptopProduct = laptop.assembleProduct(laptopAssembly);
        System.out.println("Результат сборки ноутбука: " + laptopProduct.toString());
    }
}
