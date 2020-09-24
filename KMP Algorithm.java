/*
KMP有两种解法/说法：
一种是利用 部分匹配表PMT(Partial Match Table)，本文代码则参考该项。
另一种则是采用 确定有限状态机(Deterministic finite state automation) 来建造 二维Array以确定进退的方向。


解读参考：
http://www.ruanyifeng.com/blog/2013/05/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm.html
http://jakeboxer.com/blog/2009/12/13/the-knuth-morris-pratt-algorithm-in-my-own-words/
https://www.coursera.org/learn/algorithms-part2/lecture/TAtDr/knuth-morris-pratt

更多习题：
https://algs4.cs.princeton.edu/53substring/
*/


/*以下参考代码来源：
作者：灵茶山艾府
链接：https://www.zhihu.com/question/21923021/answer/37475572
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


// 构造模式串 pattern 的最大匹配数表
int[] calculateMaxMatchLengths(String pattern) {
    int[] maxMatchLengths = new int[pattern.length()];
    int maxLength = 0;
    for (int i = 1; i < pattern.length(); i++) {
        //return to last matched
        while (maxLength > 0 && pattern.charAt(maxLength) != pattern.charAt(i)) {
            maxLength = maxMatchLengths[maxLength - 1]; // ①
        }
        if (pattern.charAt(maxLength) == pattern.charAt(i)) {
            maxLength++; // ②
        }
        maxMatchLengths[i] = maxLength;
    }
    return maxMatchLengths;
}

// 在文本 text 中寻找模式串 pattern，返回所有匹配的位置开头
List<Integer> search(String text, String pattern) {
    List<Integer> positions = new ArrayList<>();
    int[] maxMatchLengths = calculateMaxMatchLengths(pattern);
    int count = 0;
    for (int i = 0; i < text.length(); i++) {
        while (count > 0 && pattern.charAt(count) != text.charAt(i)) {
            count = maxMatchLengths[count - 1];
        }
        if (pattern.charAt(count) == text.charAt(i)) {
            count++;
        }
        if (count == pattern.length()) {
            positions.add(i - pattern.length() + 1);
            count = maxMatchLengths[count - 1];
        }
    }
    return positions;
}
*/


//Re-Rewritten / Practice:
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Main{
    //construct Partial Match Table
    static int[] calculateMaxMatchLengths(String pattern) {
        //initiate PMT
        int[] pmt = new int[pattern.length()];
        int maxMatched = 0;
        //loop in the whole string for pattern
        for (int i = 1;i<pattern.length();i++){
            //when not matched, return to last match position
            while(maxMatched > 0 && pattern.charAt(maxMatched) != pattern.charAt(i)){
                maxMatched = pmt[maxMatched -1];
            }
            //when match, maxMatch ++
            if(pattern.charAt(i)==pattern.charAt(maxMatched)){
                maxMatched++;
            }
            //record the maxMatch at separete index
            pmt[i]=maxMatched;
        }
        return pmt;
    }
    
    //construct search function
    static List<Integer> search(String text, String pattern){
        //initiate found_table and prepare PMT
        List<Integer> positions = new ArrayList<>();
        int [] PMT = calculateMaxMatchLengths(pattern);
        int cnt = 0;
        
        //loop through whole text searching for pattern
        for (int i = 0;i<text.length();i++){
            //when text[index] not matched with pattern[cnt], cnt return to last matched position
            while(cnt > 0 && text.charAt(i) != pattern.charAt(cnt)){
                cnt = PMT[cnt -1];
            }
            //when text[index] continuously matached, cnt++
            if(text.charAt(i)==pattern.charAt(cnt)){
                cnt++;
            }
            //when cnt == pattern length, the whole pattern is found at [i-cnt+1], 
            //then return the cnt to last matched position for furthur searching.
            if(cnt == pattern.length()){
                positions.add(i-cnt+1);
                cnt = PMT[cnt-1];
            }
        }
        return positions;
    }
    public static void main(String[] args){
        System.out.println(Arrays.toString(Main.calculateMaxMatchLengths("abababzabababa")));
        int[] hey = new int[10];
        System.out.println(Arrays.toString(hey));
        System.out.println(search("abababababbabababzabababababzabababa","abababzabababa"));
    }
}
