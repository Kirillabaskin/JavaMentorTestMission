package model;

import java.util.ArrayList;

public class CargoLogistUnit {
    CargoLogistUnit prev;  // ссылка на вершину родителя. Для корневой вершины равен null
    ArrayList<CargoLogistUnit> next = new ArrayList<>(); // лист потомков
    Cargo unit;// сам груз

    public CargoLogistUnit getPrev() {
        return prev;
    }

    public ArrayList<CargoLogistUnit> getNext() {
        return next;
    }

    public Cargo getUnit() {
        return unit;
    }

    CargoLogistUnit(){
        prev=null; }// единственный публичный коструктор для инициализации дерева
    public CargoLogistUnit(CargoLogistUnit parent, Cargo cargo){ // внутренний конструктор
        prev = parent;
        unit = cargo;
    }
}
