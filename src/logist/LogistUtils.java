package logist;

import model.*;

import java.util.ArrayList;

public class LogistUtils {
    public static boolean isContains(Cargo cargo, CargoLogistUnit end) {
        if (end.getUnit() == cargo) {
            return true;
        } else if (end.getPrev() == null) {
            return false;
        } else {
            return isContains(cargo, end.getPrev());
        }
    }

    public static int weighThere(CargoLogistUnit unit) { // сумма массы всех грузов от начала дерева до данной вершины
        if (unit.getPrev() != null) {
            return unit.getUnit().getWeight() + weighThere(unit.getPrev());
        } else {
            return 0;
        }
    }

    public static int costThere(CargoLogistUnit unit) { // тоже самое но только для цены
        if (unit.getPrev() != null) {
            return unit.getUnit().getCost() + costThere(unit.getPrev());
        } else {
            return 0;
        }
    }

    public static void loadMaxCostTruck(Truck truck, CargoTree tree) {
        ArrayList<Cargo> cargos = new ArrayList<>();
        TopGraph max = new TopGraph();
        max.setTop(tree.getFirst());
        findMaxEnd(tree.getFirst(),max);
        truck.setCargos(cargoToList(max.getTop()));
    }

    public static ArrayList<Cargo> cargoToList(CargoLogistUnit unit){
        ArrayList<Cargo> list=new ArrayList<>();
        if(unit.getUnit() != null){
            list.add(unit.getUnit());
            list.addAll(cargoToList(unit.getPrev()));
        }
        return list;
    }
    public static void makeTree(CargoLogistUnit parent, int weight, ArrayList<Cargo> allCargo) {
        for (Cargo unit : allCargo) {
            if (!isContains(unit, parent))
                parent.getNext().add(new CargoLogistUnit(parent, unit));
        }
        for (int i = 0; i < parent.getNext().size(); i++) {
            if (weighThere(parent.getNext().get(i)) <= weight) {
                makeTree(parent.getNext().get(i), weight, allCargo);
            } else {
                parent.getNext().remove(i--);
            }
        }
    }

    public static void findMaxEnd(CargoLogistUnit parent, TopGraph maxEnd) {
        for (CargoLogistUnit unit : parent.getNext()) {
            if (unit.getNext().size() > 0) {
                findMaxEnd(unit, maxEnd);
            } else if (costThere(unit) > costThere(maxEnd.getTop())) {
                maxEnd.setTop(unit);
            }
        }
    }
}
