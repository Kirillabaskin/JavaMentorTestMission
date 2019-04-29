package model;

import java.util.ArrayList;

public class Truck  {
    private int maxWeight;
    private ArrayList<Cargo> cargos;

    public Truck(int weight){
        maxWeight = weight;
    }

    public void setCargos(ArrayList<Cargo> cargos){
        this.cargos = cargos;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public String showCargos(){
        StringBuilder builder=new StringBuilder();
        int weight=0;
        for(Cargo cargo:cargos){
            builder.append(cargo.getName()+" ");
            weight = weight + cargo.getCost();
        }
        builder.append(weight);
        return  builder.toString();
    }
}
