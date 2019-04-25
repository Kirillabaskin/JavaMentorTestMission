import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private int weight; // глобальная переменная веса машины
    private ArrayList<Cargo> cargos; // глобальныйы лист грузов
    private CargoLogistUnit max=new CargoLogistUnit(); // максимальный элемент в графе из грузов

    public static void main(String[] args){
        Main main= new Main();
        main.go();
    }

    private void go(){
        try {
            Scanner sc = new Scanner(System.in); // читаем из консоли и единственные проблемы могут возникнуть только при непрвильном вводе
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
            System.out.print("Некорректно введены данные"); // поэтому такой блок catch
        }
    }

    private class CargoLogistUnit{ // по сути это внутренний класс для вершины графа-дерева
        private CargoLogistUnit prev;  // ссылка на вершину родителя. Для корневой вершины равен null
        private ArrayList<CargoLogistUnit> next = new ArrayList<>(); // лист потомков
        private Cargo unit;// сам груз
        private boolean isEnd=false;// переменная указывающая что эта вершина не действительна из-за перегруза или перебора всех возможных грузов
        public CargoLogistUnit(){
            prev=null;
        }// единственный публичный коструктор для инициализации дерева
        private CargoLogistUnit(CargoLogistUnit parent,Cargo cargo){ // внутренний конструктор
            prev=parent;
            unit=cargo;
        }
        private boolean isContains(Cargo cargo){ // метод для прохода по ветке до встречи соотвествия
            if(unit == cargo)
                return true;
            else if(prev==null)
                return false;
            else
                return prev.isContains(cargo);
        }

        private int weighThere(){ // сумма массы всех грузов от начала дерева до данной вершины
            if(prev!=null)
                return unit.getWeight()+prev.weighThere();
            else
                return 0;
        }

        private int costThere(){ // тоже самое но только для цены
            if(prev!=null)
                return unit.getCost()+prev.costThere();
            else
                return 0;
        }

        public void nextLevel(){ // создание следующего поколения вершин
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

        public boolean isEndPoint() { // метод для того что бы узнать есть ли потомки у данной вершины
            for(CargoLogistUnit unit:next){
                if(!unit.isEnd)
                    return false;
            }
            return true;
        }

        public void findMax(){ // метод который находит максимальную вершину и заменяет глобальную переменную на нее
            for(CargoLogistUnit unit:next){
                if(unit.weighThere()<=weight & unit.isEndPoint()) {
                    if (unit.costThere()> max.costThere())
                        max = unit;
                }
                else
                    unit.findMax();
            }
        }

        public void showPath(){ // просто показывает грузы в данной ветке и сумму их стоимости
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
