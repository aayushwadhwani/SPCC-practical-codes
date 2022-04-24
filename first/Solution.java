import java.util.*;
class FAF{
	public static Set<Character> FIRST(HashMap<Character, ArrayList<String>> g, Character ch) {
		Set<Character> result = new HashSet<>();
		if(!Character.isUpperCase(ch)) {
			result.add(ch);
			return result;
		}
			
		ArrayList<String> rules = g.get(ch);
		for(String rule: rules) {
			int i = 0;
			char first = rule.charAt(i);
			if(Character.isUpperCase(first)) {
				Set<Character> temp = FIRST(g, first);
				result.addAll(temp);
				while(temp.contains('9') && i < rule.length()) {
					result.remove('9');
					i = i + 1;
					char next = rule.charAt(i);
					temp = FIRST(g, next);
					result.addAll(temp);
				}
				if(i == rule.length()) {
					result.add('9');
				}
			}else{
				result.add(first);
			}
		}
		
		return result;
	} 
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of non terminals symbol: ");
		int nt = sc.nextInt();
		sc.nextLine();
		System.out.println();
		
		ArrayList<Character> nts = new ArrayList<>();
		for(int i=0; i<nt; i++) {
			System.out.print(String.format("Enter the %dth non terminal symbol: ", i+1));
			nts.add(sc.nextLine().charAt(0));
		}
		System.out.println();
		System.out.print("Enter the number of terminal symbols:  ");
		int t = sc.nextInt();
		sc.nextLine();
		
		System.out.println();
		ArrayList<Character> ts = new ArrayList<>();
		for(int i=0; i<t; i++){
			System.out.print(String.format("Enter the %dth terminal symbol: ", i+1));
			ts.add(sc.nextLine().charAt(0));
		}
		
		System.out.println(nts);
		System.out.println(ts);

		System.out.println();
		HashMap<Character, ArrayList<String>> g = new HashMap<>();
		for(char n: nts) {
			System.out.println("Enter the number of production rule for " + n);
			int k = sc.nextInt();
			sc.nextLine();
			for(int i=0; i<k; i++) {
				ArrayList<String> pr = g.getOrDefault(n, new ArrayList<String>());
				System.out.print(String.format("Enter the %dth production rule for %c :  ", i+1, n));
				pr.add(sc.nextLine());
				g.put(n, pr);
			}
		}
		System.out.println(g);
		System.out.println("Firsts:");
		for(Character ch: nts) {
			System.out.println(ch + "->" + FIRST(g, ch));
		}
	}
}