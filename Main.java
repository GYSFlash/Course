public class Main {
    public static void main(String[] args) {
        int sum =0;
        for (int i = 0;i <3;i++) {
            int random = (new java.util.Random()).nextInt(999);
            while (random < 100){
                random = (new java.util.Random()).nextInt(999);
            }
            sum += (random /100);
            System.out.println("Число номер " + i + " :" + random);
        }
        System.out.println("Сумма первых трех чисел: "+ sum);
    }
}