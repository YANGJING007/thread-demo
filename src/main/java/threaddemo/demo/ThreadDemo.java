package threaddemo.demo;

import java.util.ArrayList;
import java.util.List;

import threaddemo.demo.ThreadDemo.CarHouse.Car;

/**
 * @author ning
 * 创建于 2017年11月22日上午11:25:29
 * //TODO (没有加上同步机制)
 */
public class ThreadDemo{

    /**
     * @author ning
     * 创建于 2017年11月22日上午11:27:53
     * //TODO 卖车的当做生产者线程
     */
    public static class CarSeller implements Runnable {

        private CarHouse carHouse;

        public CarSeller(CarHouse carHouse){
            this.carHouse = carHouse;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {// 当做生产者线程，往仓库里边增加汽车，其实是触发增加汽车
                carHouse.put(i);
            }
        }

    }

    /**
     * @author ning
     * 创建于 2017年11月22日上午11:29:00
     * //TODO 购买者当做消费者线程
     */
    public static class Consumer implements Runnable {

        private CarHouse carHouse;

        public Consumer(CarHouse carHouse){
            this.carHouse = carHouse;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {// 当做消费者线程，从仓库里边提取汽车，其实是触发，从仓库里边提取一辆汽车出来
                carHouse.get(i);
            }
        }
    }

    /**
     * @author ning
     * 创建于 2017年11月22日上午11:30:22
     * //TODO 汽车仓库
     */
    public static class CarHouse {
        /**
         * 车库中汽车数量
         */
        public Integer carNums = 0;

        public Integer getCarNums() {
            return carNums;
        }

        public void setCarNums(Integer carNums) {
            this.carNums = carNums;
        }

        public List<Car> getCarList() {
            return carList;
        }

        public void setCarList(List<Car> carList) {
            this.carList = carList;
        }

        /**
         * 存放汽车的集合
         */
        public List<Car> carList = new ArrayList<>();

        public int put(int i){
            Car car = CarFactory.makeNewCar();
            carList.add(car);
            carNums++;
            System.out.println("生产汽车-" + i + "->车库汽车数量---count = " + carList.size());
            return carList.size();
        }

        public int get(int i){
            Car car = null;
            if(carList.size() > 0){
                car = carList.get(carList.size() - 1);
                carList.remove(car);
                carNums--;
            }
            System.out.println("消费汽车-" + i + "->车库汽车数量---count = " + carList.size());
            return carList.size();
        }

        public static class Car {
            public String carName;
            public double carPrice;

            public  Car(){}

            public  Car(String carName, Double carPrice){
                this.carName = carName;
                this.carPrice = carPrice;
            }

            public String getCarName() {
                return carName;
            }
            public void setCarName(String carName) {
                this.carName = carName;
            }
            public double getCarPrice() {
                return carPrice;
            }
            public void setCarPrice(double carPrice) {
                this.carPrice = carPrice;
            }
        }
    }

    /**
     * 采用静态工厂方式创建car对象，这个只是简单模拟，不做设计模式上的过多考究
     */
    public static class CarFactory {

        private CarFactory(){

        }

        public static Car makeNewCar(String carName, Double carPrice){
            return new Car(carName, carPrice);
        }

        public static Car makeNewCar() {
            return new Car();
        }
    }

    /**
     * 第一个版本的生产者和消费者线程，没有加上同步机制的演示例子
     *
     * @param args
     */
    public static void main(String[] args) {
        CarHouse bigHouse = new CarHouse();
        new Thread(new CarSeller(bigHouse)).start();
        new Thread(new Consumer(bigHouse)).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(bigHouse.getCarNums());
        System.out.println(bigHouse.getCarList().size());
    }
}
