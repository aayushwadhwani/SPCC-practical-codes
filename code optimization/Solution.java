import java.util.*;
class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of 3 address codes: ");
		int n = sc.nextInt();
		sc.nextLine();
		ArrayList<StringBuilder> ac = new ArrayList<>();
		for(int i=0; i<n; i++) {
			System.out.printf("Enter the %d address code: ", i+1);
			ac.add(new StringBuilder(sc.nextLine().replaceAll(" ","")));
		}
		System.out.println(ac);


		HashMap<String, ArrayList<String>> eq = new HashMap<>();
		for(StringBuilder s: ac) {
			String[] eqs = s.toString().split("=");
			ArrayList<String> temp = eq.getOrDefault(eqs[1], new ArrayList<String>());
			temp.add(eqs[0]);
			eq.put(eqs[1], temp);
		}
		System.out.println(eq);
		String f="";
		ArrayList<String> ff = new ArrayList<>();
		for(String rhs: eq.keySet()) {
			if(ff.size() < eq.get(rhs).size()) {
				f=rhs;
				ff=eq.get(rhs);
			} 
		}
		while(ff.size() > 1) {
			String replaceWith = ff.get(0);
			for(StringBuilder a: ac) {
				for(int i=1; i<ff.size(); i++) {
					String aa = ff.get(i);
					while(a.indexOf(aa) != -1) {
						a.replace(a.indexOf(aa), a.indexOf(aa)+aa.length(), replaceWith);
					}
				}
			}
			HashSet<String> set = new HashSet<>();
			for(StringBuilder a: ac) {
				set.add(a.toString());	
			}
		
			ac =  new ArrayList<>();
			for(String ss: set) {
				ac.add(new StringBuilder(ss));
			}
		
			 eq = new HashMap<>();
			for(StringBuilder s: ac) {
				String[] eqs = s.toString().split("=");
				ArrayList<String> temp = eq.getOrDefault(eqs[1], new ArrayList<String>());
				temp.add(eqs[0]);
				eq.put(eqs[1], temp);
			}

			f="";
			ff = new ArrayList<>();
			for(String rhs: eq.keySet()) {
				if(ff.size() < eq.get(rhs).size()) {
					f=rhs;
					ff=eq.get(rhs);
				} 
			}
		}
		System.out.println(ac);
	}
}