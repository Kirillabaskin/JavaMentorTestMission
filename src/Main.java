import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private int weight;
    private ArrayList<Cargo> cargos;
    private CargoLogistUnit max=new CargoLogistUnit();

    public static void main(String[] args){
        Main main= new Main();
        main.go();
    }

    private void go(){
        try {
            Scanner sc = new Scanner(System.in);
            weight = Integer.parseInt(sc.nextLine());
            String allCargos = sc.nextLine();
            String[] cargosStrings = allCargos.split(" ");
            cargos = new ArrayList<>();
            for (int i = 0; i < cargosStrings.length; i++) {
                String[] oneCargo = cargosStrings[i].split("/");
                cargos.add(new Cargo(oneCargo[0], oneCargo[1], oneCargo[2]));
            }
            CargoLogistUnit unit = new CargoLogistUnit();
            unit.nextLevel();
            unit.findMax();
            max.showPath();
        }catch (Exception e){
            System.out.print("Некорректно введены данные");
        }
    }

    private class CargoLogistUnit{
        private CargoLogistUnit prev;
        private ArrayList<CargoLogistUnit> next = new ArrayList<>();
        private Cargo unit;
        private boolean isEnd=false;
        public CargoLogistUnit(){
            prev=null;
        }
        private CargoLogistUnit(CargoLogistUnit parent,Cargo cargo){
            prev=parent;
            unit=cargo;
        }
        private boolean isContains(Cargo cargo){
            if(unit == cargo)
                return true;
            else if(prev==null)
                return false;
            else
                return prev.isContains(cargo);
        }

        private int weighThere(){
            if(prev!=null)
                return unit.getWeight()+prev.weighThere();
            else
                return 0;
        }

        private int costThere(){
            if(prev!=null)
                return unit.getCost()+prev.costThere();
            else
                return 0;
        }

        public void nextLevel(){
            if(weighThere()>weight){
                isEnd=true;
                return ;
            }
            int j=0;
            for(int i=0;i<cargos.size();i++){
                if(!isContains(cargos.get(i))) {
                    next.add(new CargoLogistUnit(this, cargos.get(i)));
                    next.get(j++).nextLevel();
                }
            }
        }

        public boolean isEndPoint() {
            for(CargoLogistUnit unit:next){
                if(!unit.isEnd)
                    return false;
            }
            return true;
        }

        public void findMax(){
            for(CargoLogistUnit unit:next){
                if(unit.weighThere()<=weight & unit.isEndPoint()) {
                    if (unit.costThere()> max.costThere())
                        max = unit;
                }
                else
                    unit.findMax();
            }
        }

        public void showPath(){
            if(prev!=null) {
                System.out.print(unit.getName()+" ");
                prev.showPath();
            }
            else{
                System.out.println(max.costThere());
            }
        }
    }
}
