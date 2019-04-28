package Model;

public class Cargo {
    private String name;
    private int weight;
    private int cost;

    public Cargo(String name, int weight,int cost){
        this.name=name;
        this.weight=weight;
        this.cost=cost;
    }

    public Cargo(String name,String weight,String cost){
        this.name=name;
        this.weight=Integer.valueOf(weight);
        this.cost=Integer.valueOf(cost);
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
