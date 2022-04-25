import java.util.*;
class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int cur = 1;
		while(true) {
			System.out.print("Enter the grammer: (-1 to exit): ");
			String g = sc.nextLine();
			if(g.equals("-1")) {
				break;
			}
			ArrayList<Character> nts = new ArrayList<>();
			HashMap<Character, List<String>> grm = new HashMap<>();
			char nt = g.charAt(0);
			nts.add(nt);
			g = g.substring(3);
			String[] ts = g.split("\\|");
			System.out.print(nt + " " + Arrays.toString(ts));
			grm.put(nt,Arrays.asList(ts));
			Collections.sort(grm.get(nt));
			System.out.print(grm);
			for(char n: nts) {
				List<String> pr = grm.get(n);
				if(pr.size() < 1) continue;
				char ch = pr.get(0).charAt(0);
				int start = 0;
				int end = 0;
				int i = 0;
				
				for(i=start+1; i<pr.size(); i++) {
					if(pr.get(i).charAt(0) != ch) {
						end = i-1;
						break;
					}
				}
				if(i == pr.size()) end = pr.size()-1;
				if(start == end) continue;
				int a = 0; //start
				int b = 0; //end 
				StringBuilder cp = new StringBuilder();
				while(a< pr.get(start).length() && b< pr.get(end).length() && (pr.get(start).charAt(a) == pr.get(end).charAt(b))) {
					cp.append(pr.get(start).charAt(a));
					a++;
					b++;
				}
				System.out.print(cp);
				ArrayList<String> remaining = new ArrayList<String>();
				for(int z=0; z<pr.size(); z++) {
					String p = pr.get(z);
					int idx = p.indexOf(cp.toString());
					if( idx != -1){
						StringBuilder temp = new StringBuilder(p);
						String tempp = temp.substring(idx+cp.length()).length() == 0 ? "9" : p.substring(idx+cp.length());
						remaining.add(tempp);
						temp.replace(idx, idx+cp.length(), n + "" + cur);
						grm.get(n).remove(z);
					}
				}
				grm.get(n).add(cp.toString()+n+""+cur);
				System.out.println(grm);
				System.out.print(n + "" + cur +"-> " );
				for(String rem: remaining) {
					System.out.print(rem + " | ");
				}
			}
		}
	}
}
