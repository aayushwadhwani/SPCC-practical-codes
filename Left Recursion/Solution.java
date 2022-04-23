import java.util.*;
class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of non terminals characters: ");
		int numberOfNonTerminals = sc.nextInt();
		sc.nextLine();
		ArrayList<Character> nonTerminalCharacters = new ArrayList<>();
		for(int i=0; i<numberOfNonTerminals; i++) {
			System.out.print("Enter the "+ i+1 + "th non terminal character: ");
			nonTerminalCharacters.add(sc.nextLine().charAt(0));
		}

		System.out.print("Enter the number of terminal characters: ");
		int numberOfTerminals = sc.nextInt();
		sc.nextLine();
		ArrayList<Character> terminalCharacters = new ArrayList<>();
		for(int i=0; i<numberOfTerminals; i++) {
			System.out.print("Enter the "+ i+1 + "th non terminal character: ");
			terminalCharacters.add(sc.nextLine().charAt(0));
		}
		System.out.println(nonTerminalCharacters);
		System.out.println(terminalCharacters);

		HashMap<Character, ArrayList<String>> gm = new HashMap<>();
		for(Character s: nonTerminalCharacters) {
			System.out.print("Enter the number of production of rules for " + s + ": ");
			int n = sc.nextInt();
			sc.nextLine();
			for(int i=0; i<n; i++) {
				System.out.print("Enter the production rule: ");
				ArrayList<String> temp = gm.getOrDefault(s,new ArrayList<String>());
				temp.add(sc.nextLine());
				gm.put(s, temp);
			}			
		}
		
		for(Character n: nonTerminalCharacters) {
			char cur = n;
			ArrayList<String> p = gm.get(n);
			boolean lr = false;
			for(String s: p) {
				if(s.charAt(0) == cur) {
					System.out.println("Left recursion exists in " + n + " -> " + s);
					lr = true;
				}
			}
			if(lr) {
				ArrayList<String> alphas = new ArrayList<>();
				ArrayList<String> betas = new ArrayList<>();
				for(String s: p) {
					if(s.charAt(0) == cur) {
						alphas.add(s.substring(1));
					}else {
						betas.add(s);
					}	
				}
				System.out.println(alphas + " " + betas);
				System.out.print(n + " -> ");
				for(String b: betas) {
					System.out.print(b + n + "' | ");	
				}
				System.out.println();
				System.out.print(n + "' -> ");
				for(String a: alphas) {
					System.out.print(a + n + "' | ");
				}
				System.out.print(" 9");
				System.out.println();
			}

		}
	}
}