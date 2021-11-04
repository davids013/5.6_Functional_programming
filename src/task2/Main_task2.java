package task2;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;

public class Main_task2 {
    private static final int TOTAL_DEALS = 25;

    public static void main(String[] args) {
        printLine("\n\tЗадача 2. Кадастровый помощник\n", System.out);

        final double marketMeterPrice = inputMeterPrice(System.in, Double::parseDouble);
        final Function<Deal, Boolean> legalizer =
                (deal -> deal.getMeterPrice() > deal.getMarketMeterPrice());
        final List<Deal> deals = getRandomDealList(TOTAL_DEALS, marketMeterPrice, legalizer,
                (d1, d2) -> Double.compare(d2.getMeterPrice(), d1.getMeterPrice()));
        deals.forEach(System.out::println);
    }

    private static void printLine(Object source, PrintStream out) {
        if (source instanceof String) {
            out.println(source);
        } else if (source instanceof Collection) {
            ((Collection) source).forEach(out::println);
        } else Set.of(source).forEach(out::println);
    }

    private static double inputMeterPrice(InputStream in, Function<String, Double> converter) {
        try (Scanner sc = new Scanner(in)) {
            while (true) {
                printLine("Введите минимально допустимую стоимость 1м2 в регионе: ", System.out);
                final String line = sc.nextLine().trim().replaceAll("[&&[^^0-9.]]", "");
                try {
                    final double price = converter.apply(line);
                    return price;
                } catch (NumberFormatException e) {
                    printLine("Ошибка. Повторите ввод!", System.out);
                }
            }
        }
    }

    private static List<Deal> getRandomDealList(int size, double marketMeterPrice,
                                                Function<Deal, Boolean> legalizer, Comparator<Deal> comp) {
        final int MAX_WIDTH = 5;
        final int MAX_LENGTH = 20;
        final List<Deal> dealList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final double width = Math.random() * MAX_WIDTH;
            final double length = Math.random() * MAX_LENGTH;
            final double cost = width * length * (Math.random() * marketMeterPrice * 2);
            dealList.add(new Deal(width, length, cost, marketMeterPrice, legalizer));
        }
        dealList.sort(comp);
        return dealList;
    }
}

final class Deal {
    private final double
            width,
            length,
            square,
            cost,
            marketMeterPrice,
            meterPrice;
    private final boolean isLegal;

    public Deal(double width, double length, double cost, double marketMeterPrice,
                Function<Deal, Boolean> legalizer) {
        this.width = width;
        this.length = length;
        this.cost = cost;
        this.marketMeterPrice = marketMeterPrice;
        square = width * length;
        meterPrice = cost / square;
        isLegal = legalizer.apply(this);
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getSquare() {
        return square;
    }

    public double getCost() {
        return cost;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public double getMarketMeterPrice() {
        return marketMeterPrice;
    }

    public double getMeterPrice() {
        return meterPrice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb
                .append("Deal{")
                .append("width=").append(String.format("%.2f", width)).append("m,\t")
                .append("length=").append(String.format("%.2f", length)).append("m,\t")
                .append("square=").append(String.format("%.2f", square)).append("m2,\t")
                .append("cost=").append(String.format("%.2f", cost)).append("$,\t")
                .append("marketMeterPrice=").append(String.format("%.2f", marketMeterPrice)).append("$/m2,\t")
                .append("meterPrice=").append(String.format("%.2f", meterPrice)).append("$/m2,\t")
                .append("isLegal=").append(isLegal).append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deal deal = (Deal) o;
        return Double.compare(deal.width, width) == 0 &&
                Double.compare(deal.length, length) == 0 &&
                Double.compare(deal.square, square) == 0 &&
                Double.compare(deal.cost, cost) == 0 &&
                isLegal == deal.isLegal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length, square, cost, isLegal);
    }
}