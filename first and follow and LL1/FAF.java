import java.util.*;
class FAF{
	public static Set<Character> FIRST(HashMap<Character, ArrayList<String>> g, Character ch, HashMap<Character, Set<Character>> memo) {
		if(memo.containsKey(ch)){
			return memo.get(ch);
		}
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
				Set<Character> temp = FIRST(g, first, memo);
				result.addAll(temp);
				i = i + 1;
				while(temp.contains('9') && i < rule.length()) {
					result.remove('9');
					char next = rule.charAt(i);
					temp = FIRST(g, next, memo);
					result.addAll(temp);
					i = i + 1;
				}
				if(i == rule.length()) {
					result.add('9');
				}
			}else{
				result.add(first);
			}
		}
	
		memo.put(ch, result);
		
		return result;
	}

	public static Set<Character> FOLLOW(HashMap<Character, ArrayList<String>> g, HashMap<Character, Set<Character>> firsts, HashMap<Character, Set<Character>> pnts, Character ch, char start, HashMap<Character, Set<Character>> memo) {
		if(memo.containsKey(ch)){
			return memo.get(ch);
		}
	
	
		Set<Character> result = new HashSet<>();

		if(!pnts.containsKey(ch)){
			if(ch == start){
				result.add('$');
			}
			return result;
		}
		
		Set<Character> gothere = pnts.get(ch);
		for(Character p: gothere) {
			ArrayList<String> gm = g.get(p);
			for(String str: gm) {
				int idx = str.indexOf(ch);
				if(idx == -1) continue;	
				idx = idx + 1;

				if(idx == str.length()){
					//System.out.println(firsts);
					//System.out.println(pnts);
					//System.out.println(p);
					//System.out.println(start);
					//System.out.println(FOLLOW(g, firsts, pnts, p, start));
					result.addAll(FOLLOW(g, firsts, pnts, p, start, memo));
				}else{
					char next = str.charAt(idx);
					if(Character.isUpperCase(next)){
						Set<Character> temp = firsts.get(next);
						result.addAll(temp);
						idx = idx + 1;
						while(temp.contains('9') && idx < str.length()) {
							result.remove('9');
							temp.remove('9');
							result.addAll(temp);
							if(!Character.isUpperCase(str.charAt(idx))){
								result.add(str.charAt(idx));
								break;
							}else{
								temp = firsts.get(str.charAt(idx));
							}
							idx = idx + 1;
						}
						
						result.addAll(temp);
						if(idx == str.length()){
							result.addAll(FOLLOW(g, firsts, pnts, p, start, memo));
						}
					}else{
						result.add(next);
					}
				}
			}
		}
		if(ch == start) {
			result.add('$');
		}

		if(result.contains('9')) result.remove('9');

		memo.put(ch, result);
		return result;
	}

	public static HashMap<Character, String> LL1(HashMap<Character, Set<Character>> firsts, HashMap<Character, Set<Character>> follows, HashMap<Character, ArrayList<String>> g, Character nt) {
		HashMap<Character, String> result = new HashMap<>();
		ArrayList<Character> first = new ArrayList<>(firsts.get(nt));
		ArrayList<Character> follow = new ArrayList<>(follows.get(nt));
		ArrayList<String> grammer = g.get(nt);
		for(Character f: first) {
			for(String pr: grammer) {
				char ch = pr.charAt(0);
				if(ch == f && f != '9') {
					result.put(f, String.format("%c->%s",nt, pr));
					break;
				} else if(Character.isUpperCase(ch)) {
					if(firsts.get(ch).contains(f)) {
						result.put(f, String.format("%c->%s",nt,pr));
					}
				} else if(ch == '9') {
					for(Character fo: follow) {
						result.put(fo, String.format("%c->%s",nt,pr));
					}
				}
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
		HashMap<Character, Set<Character>> pnts = new HashMap<>();
		for(char n: nts) {
			System.out.println("Enter the number of production rule for " + n);
			int k = sc.nextInt();
			sc.nextLine();
			for(int i=0; i<k; i++) {
				ArrayList<String> pr = g.getOrDefault(n, new ArrayList<String>());
				System.out.print(String.format("Enter the %dth production rule for %c :  ", i+1, n));
				String rule = sc.nextLine();
				pr.add(rule);
				g.put(n, pr);

				for(int s = 0; s < rule.length(); s++) {
					char ch = rule.charAt(s);
					if(Character.isUpperCase(ch) && ch != n) {
						Set<Character> gothere = pnts.getOrDefault(ch, new HashSet<Character>());
						gothere.add(n);
						pnts.put(ch, gothere);
					}
				}
			}
		}
		HashMap<Character, Set<Character>> firsts = new HashMap<Character, Set<Character>>();
		HashMap<Character, Set<Character>> memo = new HashMap<Character, Set<Character>>();
		for(Character ch: nts) {
			firsts.put(ch, FIRST(g, ch, memo));
		}
		System.out.println("CFG: ");
		System.out.println(g);
		System.out.println("Firsts:");
		System.out.println(firsts);
		System.out.println("Go there pointers");
		System.out.println(pnts);
		System.out.print("Enter starting non-terminal character: ");
		char start = sc.nextLine().charAt(0);
		HashMap<Character, Set<Character>> follows = new HashMap<Character, Set<Character>>();
		memo = new HashMap<Character, Set<Character>>();
		for(Character ch: nts) {
			follows.put(ch, FOLLOW(g, firsts, pnts, ch, start, memo));
		}
		System.out.println("Follows:");
		System.out.println(follows);
		for(char c: nts) {
			System.out.println(c+ " -> " +LL1(firsts, follows, g, c));
		}
	}
}

