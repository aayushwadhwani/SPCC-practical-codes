import java.util.*;
class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the expression: "); //b*t1-c+b*-c
		System.out.println("The expression should be in the format: (output = exp op exp : where exp can be variable or constant and op is operator)");
		String expp = sc.nextLine();
		StringBuilder exp = new StringBuilder(expp);
		int curr = 1;
		ArrayList<String> breakage = new ArrayList<>();
		HashMap<String, String> address = new HashMap<>(); 
		for(int i=0; i<exp.length(); i++) {
			if(exp.charAt(i) == '+' || exp.charAt(i) == '-' ||exp.charAt(i) == '*' ||exp.charAt(i) == '/' ) {
				if(exp.charAt(i+1) == '-') {
					breakage.add(exp.substring(i, i+1));
					breakage.add("t"+curr);
					address.put("t"+curr, exp.substring(i+1, i+3));
					exp.replace(i+1, i+3,"t"+curr);
					i+=2;
					curr++;
				} else {
					breakage.add(exp.substring(i, i+1));
				}
			}else {
				breakage.add(exp.substring(i, i+1));
			}
		}
		HashMap<Character, Integer> pr = new HashMap<>();
		pr.put('@',-1);
		pr.put('+',1);
		pr.put('-',1);
		pr.put('/',2);
		pr.put('*',2);
		while(breakage.size() > 3) {
			int jyada = -1;
			char jyadaWala = '@';
			for(int i=0; i<breakage.size(); i++) {
				if(pr.containsKey( breakage.get(i).charAt(0) )){
					if(pr.get( breakage.get(i).charAt(0) ) > jyada) {
						jyada = pr.get( breakage.get(i).charAt(0) );
						jyadaWala =  breakage.get(i).charAt(0);
					}
				}
			}
			int k = 0;
			while(breakage.get(k).charAt(0) != jyadaWala) {
				k++;
			}		
			address.put("t"+curr, breakage.get(k-1)+ breakage.get(k)+ breakage.get(k+1));
			breakage.add(k-1, "t"+curr);
			breakage.remove(k);
			breakage.remove(k);
			breakage.remove(k);
			curr++;
		}
		address.put(breakage.get(0), breakage.get(2));
		System.out.println(address);
	}
}