package gldailycode.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode_56 {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // ① 按左端点升序，相同时按右端点
        Arrays.sort(intervals, (a, b) ->
            a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]
        );

        // ② 遍历合并
        List<int[]> res = new ArrayList<>();
        int[] cur = intervals[0];                        // 当前合并区间

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= cur[1]) {             // 有重叠
                cur[1] = Math.max(cur[1], intervals[i][1]);
            } else {                                     // 无重叠：收口 + 起新
                res.add(cur);
                cur = intervals[i];
            }
        }
        res.add(cur);                                    // ③ 最后一个区间收口

        return res.toArray(new int[0][]);
    }
}
