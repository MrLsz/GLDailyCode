package gldailycode.double_point;

public class Leetcode_344 {
    public void reverseString(char[] s) {
        if (s == null || s.length <= 1) {
            return;
        }

        int i = 0, j = s.length - 1;
        while (i < j) {
            char tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
            i++;
            j--;
        }
    }
}
