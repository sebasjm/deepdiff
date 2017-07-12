package diff.equality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author sebasjm <smarchano@primary.com.ar>
 */

public class LongestCommonSubsequence<T> {
	private static final int NEITHER     = 0;
	private static final int UP          = 1;
	private static final int LEFT        = 2;
	private static final int UP_AND_LEFT = 3;

    final Comparator<T> comparator;

    public LongestCommonSubsequence() {
        this.comparator = new Comparator<T>() {
            @Override public int compare(T o1, T o2) {
                return o1 == o2 ? 0 : 1;
            }
        };
    }
    public LongestCommonSubsequence(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    
	public void solve(List<T> a, List<T> b, Listener<T> lis) {
        List<T> A = new ArrayList<>();
        List<T> B = new ArrayList<>();
        int min = 0;
        for (; min < a.size() && min < b.size() && comparator.compare(a.get(min), b.get(min)) == 0; min++) {
//            lis.same(min, min, a.get(min), b.get(min));
        }
        if (min == a.size()) {
            for (int i = min-1; i >= 0; i--) {
                lis.same(i, i, a.get(i), b.get(i));
            }
            return;
        }
        
        int max  = a.size()-1;
        int diff = a.size()-b.size();
        for (; max >= min && max >= diff+min && comparator.compare(a.get(max), b.get(max-diff)) == 0; max--) {
            lis.same(max, max-diff, a.get(max), b.get(max-diff));
        }
        for (int i = min; i <= max; i++) {
            A.add(a.get(i));
        }
        for (int i = min; i <= max-diff; i++) {
            B.add(b.get(i));
        }
        
//        a = A; b = B;
        
        int[][] m = matrix(A,B);
		walk(m,A,B,lis,min);
        
        for (int i = min-1; i >= 0; i--) {
            lis.same(i, i, a.get(i), b.get(i));
        }
    }
    
	public List<Match<T>> solve(List<T> a, List<T> b) {
        int[][] m = matrix(a,b);
		return backtrack(m,a.size(),b.size(),a);
    }
    
	public List<Match<T>> solve(T[] a, T[] b) {
        int[][] m = matrix(a,b);
		return backtrack(m,a.length,b.length,a);
	}
    
    public static class Match<T> {
        final int x, y;
        final T element;

        public Match(int x, int y, T element) {
            this.x = x;
            this.y = y;
            this.element = element;
        }
    }
    
    public int[][] matrix(List<T> a, List<T> b) {
		int S[][] = new int[a.size()+1][b.size()+1];
		int R[][] = new int[a.size()+1][b.size()+1];

		for(int x = 0; x <= a.size(); ++x) {
			S[x][0] = 0;
			R[x][0] = UP;
		}
		for(int y = 0; y <= b.size(); ++y) {
			S[0][y] = 0;
			R[0][y] = LEFT;
		}
        
		for(int x = 1; x <= a.size(); ++x) {
			for(int y = 1; y <= b.size(); ++y) {
				if( comparator.compare(a.get(x-1), b.get(y-1)) == 0 ) {
					S[x][y] = S[x-1][y-1] + 1;
					R[x][y] = UP_AND_LEFT;
				} else {
					S[x][y] = S[x-1][y-1] + 0;
					R[x][y] = NEITHER;
				}
				if( S[x-1][y] >= S[x][y] ) {
					S[x][y] = S[x-1][y];
					R[x][y] = UP;
				}
				if( S[x][y-1] >= S[x][y] ) {
					S[x][y] = S[x][y-1];
					R[x][y] = LEFT;
				}
			}
		}
        return R;
    }
    
    int[][] matrix(T[] a, T[] b) {
		int S[][] = new int[a.length+1][b.length+1];
		int R[][] = new int[a.length+1][b.length+1];

		for(int x = 0; x <= a.length; ++x) {
			S[x][0] = 0;
			R[x][0] = UP;
		}
		for(int y = 0; y <= b.length; ++y) {
			S[0][y] = 0;
			R[0][y] = LEFT;
		}
        
		for(int x = 1; x <= a.length; ++x) {
			for(int y = 1; y <= b.length; ++y) {
				if( comparator.compare(a[x-1], b[y-1]) == 0 ) {
					S[x][y] = S[x-1][y-1] + 1;
					R[x][y] = UP_AND_LEFT;
				} else {
					S[x][y] = S[x-1][y-1] + 0;
					R[x][y] = NEITHER;
				}
				if( S[x-1][y] >= S[x][y] ) {
					S[x][y] = S[x-1][y];
					R[x][y] = UP;
				}
				if( S[x][y-1] >= S[x][y] ) {
					S[x][y] = S[x][y-1];
					R[x][y] = LEFT;
				}
			}
		}
        return R;
    }
    
    public static interface Listener<T> {
        void same(int x, int y, T before, T after);
        void add(int x, T after);
        void del(int x, T before);
    }
    
    void walk(int[][] matrix, List<T> before, List<T> after, Listener<T> listener, int min) {
        int x = before.size();
        int y = after.size();
        
		while( x > 0 || y > 0 ) {
            switch (matrix[x][y]) {
                case UP_AND_LEFT: 
                    x--;
                    y--;
                    listener.same(x+min, y+min, before.get(x), after.get(y));
                    continue;
                case UP:
                    x--;
                    listener.del(x+min, before.get(x));
                    continue;
                case LEFT:
                    y--;
                    listener.add(y+min, after.get(y));
                    continue;
            }
		}
    }
    
    List<Match<T>> backtrack(int[][] R, int x, int y, List<T> a) {
		ArrayList<Match<T>> result = new ArrayList<>();
		while( x > 0 || y > 0 ) {
            switch (R[x][y]) {
                case UP_AND_LEFT: 
                    x--;
                    y--;
                    result.add(new Match(x,y,a.get(x)));
                    continue;
                case UP:
                    x--;
                    continue;
                case LEFT:
                    y--;
                    continue;
            }
		}
        return result;
    }
    
    List<Match<T>> backtrack(int[][] R, int x, int y, T[] a) {
		ArrayList<Match<T>> result = new ArrayList<>();
		while( x > 0 || y > 0 ) {
            switch (R[x][y]) {
                case UP_AND_LEFT: 
                    x--;
                    y--;
                    result.add(new Match(x,y,a[x]));
                    continue;
                case UP:
                    x--;
                    continue;
                case LEFT:
                    y--;
                    continue;
            }
		}
        return result;
    }

    public static Character[] toCharacterArray(String s) {
        char[] cs = s.toCharArray();
        Character[] result = new Character[cs.length];
        for (int index = 0; index < cs.length; index++) {
            result[index] = cs[index];
        }
        return result;
    }
    
    final static LongestCommonSubsequence<Character> lcs = new LongestCommonSubsequence();
    
    public static String run1(String before, String after) {
        System.out.println(String.format("compraing: %1s - %2s", before, after));
        final StringBuilder builder = new StringBuilder();
        final List<Character> _before = Arrays.asList(toCharacterArray(before));
        final List<Character> _after  = Arrays.asList(toCharacterArray(after));
        
        lcs.solve(_before, _after, new Listener<Character>() {
            @Override
            public void same(int x, int y, Character before, Character after) {
                System.out.println(x + " "+ y +" same: " + before + " | " + after);
                builder.append(x).append(",").append(y).append(": ").append(before).append("|");
            }
            @Override public void add(int x, Character after) {
                System.out.println("- " + x + " add: " + after);
            }
            @Override public void del(int y, Character before) {
                System.out.println(y + " - del: " + before);
            }
        });
        System.out.println("\""+ builder.toString()+ "\"");
        return builder.toString();
    }
    
    public static String run2(String before, String after) {
        System.out.println(String.format("compraing: %1s - %2s", before, after));
        StringBuilder builder = new StringBuilder();
        for (Match<Character> m : lcs.solve( toCharacterArray(before), toCharacterArray(after) )) {
            final String search = String.format("%s,%s: %s", m.x, m.y, m.element.toString());
            System.out.println(search);
            builder.append(search).append("|");
        }
        System.out.println("\""+ builder.toString()+ "\"");
        return builder.toString();
    }
    
    public static void run(String before, String after, String expected) {
        final String result = run1(before,after);
        if (!result.equals(expected)) throw new RuntimeException("not as expected: \nres:" + result + " | \nexp:" + expected);
    }
    
    public static void test() {
        run("sebastian","javier"   ,"6,3: i|3,1: a|");
        run("sebastian","sebastian","8,8: n|7,7: a|6,6: i|5,5: t|4,4: s|3,3: a|2,2: b|1,1: e|0,0: s|");
        run("sebastian","sebastia" ,"7,7: a|6,6: i|5,5: t|4,4: s|3,3: a|2,2: b|1,1: e|0,0: s|");
        run("sebastian","javi"     ,"6,3: i|3,1: a|");
        run("sebastian","stian"    ,"8,4: n|7,3: a|6,2: i|5,1: t|0,0: s|");
        
        run("abcd","abd" ,"3,2: d|1,1: b|0,0: a|");
        run("abcd","abdc","3,2: d|1,1: b|0,0: a|");
        run("abcd","ac"  ,"2,1: c|0,0: a|");
        
        run("hola como te va?","hola te va?"        ,"15,10: ?|14,9: a|13,8: v|12,7:  |11,6: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|");
        run("hola como te va?","hola como te re va?","15,18: ?|14,17: a|13,16: v|12,12:  |11,11: e|10,10: t|9,9:  |8,8: o|7,7: m|6,6: o|5,5: c|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|");
        run("hola como te va?","hola te re va?"     ,"15,13: ?|14,12: a|13,11: v|12,10:  |11,9: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|");
        run("hola como te va?","hola te re va?"     ,"15,13: ?|14,12: a|13,11: v|12,10:  |11,9: e|10,5: t|4,4:  |3,3: a|2,2: l|1,1: o|0,0: h|");
    }
    
    public static void main(String args[]) {
        if (args.length == 2) {
            run1(args[0],args[1]);
        } else {
            test();
        }
	}
}