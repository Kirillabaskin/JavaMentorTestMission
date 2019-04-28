import Logist.LogistUtils;
import Model.Cargo;
import Model.CargoTree;
import Model.Truck;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Truck truck;
        String allCargos;
        ArrayList<Cargo> cargos;
        try {
            Scanner sc = new Scanner(System.in); // читаем из консоли и единственные проблемы могут возникнуть только при непрвильном вводе
            truck=new Truck(Integer.parseInt(sc.nextLine()));
            allCargos = sc.nextLine();
            String[] cargosStrings = allCargos.split(" ");
            cargos = new ArrayList<>();
            for (int i = 0; i < cargosStrings.length; i++) {
                String[] oneCargo = cargosStrings[i].split("/");
                String cargoName = oneCargo[0];
                int cargoWeight = Integer.valueOf(oneCargo[1]);
                int cargoCost = Integer.valueOf(oneCargo[2]);
                cargos.add(new Cargo(cargoName, cargoWeight, cargoCost));
            }
            CargoTree tree=new CargoTree();
            LogistUtils.makeTree(tree.getFirst(), truck.getMaxWeight(),cargos);

            LogistUtils.loadMaxCostTruck(truck,tree);
            System.out.print(truck.showCargos());
        }catch (Exception e){
            e.printStackTrace();
            System.out.print("Некорректно введены данные"); // поэтому такой блок catch
        }
    }
}
