import java.util.*;
import java.io.*;

public class twopassmacro {
    public static void main(String args[]) throws FileNotFoundException {
        List<String> mdt = new ArrayList<>();
        List<String> mnt = new ArrayList<>();
        List<String> ala = new ArrayList<>();
        boolean check = true;

        File f1 = new File("twopassmacro.txt");
        Scanner sc = new Scanner(f1);
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            String arrStr[] = data.split(" ");

            if (data.equals("MEND")) {
                mdt.add(data);
                break;
            } else if (data.equals("MACRO"))
                continue;

            else
                for (int i = 0; i < arrStr.length; i++) {
                    if (arrStr[i].charAt(0) == '&' && ala.contains(arrStr[i])) {
                        String indx = "#" + (ala.indexOf(arrStr[i]) + 1);
                        data = data.replace(arrStr[i], indx);
                        check = false;
                    } else if (arrStr[i].charAt(0) == '&') {
                        ala.add(arrStr[i]);
                    }

                }
            if (check == true) {
                for (int i = 0; i < arrStr.length; i++) {
                    if (arrStr[i].charAt(0) != '&' && arrStr[i].charAt(0) != ',') {
                        mnt.add(arrStr[i]);
                    }
                }
            }
            mdt.add(data);
            // System.out.println(data);
        }

        // Print MDT (Macro Definition Table)
        System.out.println("MDT\n");

        System.out.println("Index\t\tCard");
        for (int i = 0; i < mdt.size(); i++) {
            System.out.println((i + 1) + "\t" + mdt.get(i));
        }
        System.out.println("----------------------------------------------\n");

        System.out.println("ALA\n");

        System.out.println("Index\tArgument");
        for (int i = 0; i < ala.size(); i++) {
            System.out.println((i + 1) + "\t" + ala.get(i));
        }
        System.out.println("----------------------------------------------");

        System.out.println("MNT\n");

        System.out.println("Index\tMacro Name\tMDT Index");
        for (int i = 0; i < mnt.size(); i++) {
            System.out.println((i + 1) + "\t" + mnt.get(i) + "\t\t" + i + 1);
        }
        System.out.println("----------------------------------------------\n\n");

        System.out.println("Expanded Code\n");
        while (sc.hasNextLine()) {
            String code = sc.nextLine();

            for (int i = 0; i < mnt.size(); i++) {
                if (code.equals("START")) {
                    System.out.println(code);

                } else if (code.contains(mnt.get(i))) {
                    ArrayList<String> tempStr = new ArrayList<String>(Arrays.asList(code.split(" ")));
                    tempStr.remove(mnt.get(i));
                    tempStr.removeAll(Collections.singletonList(","));

                    for (int k = 1; k < mdt.size(); k++) {
                        String temp = mdt.get(k);
                        for (int p = 0; p < tempStr.size(); p++) {
                            String index = "#" + (p + 1);
                            temp = temp.replace(index, tempStr.get(p));
                        }
                        if (mdt.get(k).equals("MEND")) {
                            break;
                        }
                        System.out.println(temp);
                    }
                } else {
                    System.out.println(code);
                }
            }

        }
        sc.close();
    }
}