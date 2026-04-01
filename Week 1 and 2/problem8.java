import java.util.*;

public class problem8 {
    enum Status { EMPTY, OCCUPIED, DELETED }

    static class Spot {
        String licensePlate;
        long entryTime;
        Status status = Status.EMPTY;

        Spot(String lp) {
            this.licensePlate = lp;
            this.entryTime = System.currentTimeMillis();
            this.status = Status.OCCUPIED;
        }
    }

    private final int CAPACITY = 500;
    private Spot[] spots = new Spot[CAPACITY];
    private int totalProbes = 0;
    private int parkingActions = 0;

    private int hash(String lp) {
        return Math.abs(lp.hashCode()) % CAPACITY;
    }

    public String parkVehicle(String lp) {
        int preferredSpot = hash(lp);
        int probes = 0;

        for (int i = 0; i < CAPACITY; i++) {
            int current = (preferredSpot + i) % CAPACITY;
            if (spots[current] == null || spots[current].status != Status.OCCUPIED) {
                spots[current] = new Spot(lp);
                totalProbes += probes;
                parkingActions++;
                return "Assigned spot #" + current + " (" + probes + " probes)";
            }
            probes++;
        }
        return "Parking Lot Full";
    }

    public String exitVehicle(String lp) {
        int preferredSpot = hash(lp);
        for (int i = 0; i < CAPACITY; i++) {
            int current = (preferredSpot + i) % CAPACITY;
            if (spots[current] == null) break;
            if (spots[current].status == Status.OCCUPIED && spots[current].licensePlate.equals(lp)) {
                long durationMs = System.currentTimeMillis() - spots[current].entryTime;
                spots[current].status = Status.DELETED;
                double fee = (durationMs / 1000.0) * 5.0;
                return "Spot #" + current + " freed, Fee: $" + String.format("%.2f", fee);
            }
        }
        return "Vehicle not found";
    }

    public static void main(String[] args) {
        problem8 lot = new problem8();
        System.out.println(lot.parkVehicle("ABC-1234"));
        System.out.println(lot.parkVehicle("ABC-1235"));
        System.out.println(lot.exitVehicle("ABC-1234"));
    }
}
