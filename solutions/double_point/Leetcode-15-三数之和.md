# LeetCode 15 - 3Sum（三数之和）

| 项目 | 内容 |
|------|------|
| 难度 | Medium |
| 链接 | https://leetcode.cn/problems/3sum/ |
| 类别 | 排序 · 双指针 |
| 关联题目 | #1 两数之和, #16 最接近的三数之和, #18 四数之和 |

---

## 题目描述

给你一个整数数组 `nums`，判断是否存在三元组 `[nums[i], nums[j], nums[k]]` 满足 `i != j`、`i != k` 且 `j != k`，同时还满足 `nums[i] + nums[j] + nums[k] == 0`。

返回所有和为 `0` 且**不重复**的三元组。答案中不可以包含重复的三元组。

### 示例

```
输入：nums = [-1,0,1,2,-1,-4]
输出：[[-1,-1,2],[-1,0,1]]

输入：nums = [0,1,1]
输出：[]

输入：nums = [0,0,0]
输出：[[0,0,0]]
```

### 约束

- `3 <= nums.length <= 3000`
- `-10^5 <= nums[i] <= 10^5`

---

## 解题思路

### 核心思想：排序 + 固定一个数 + 双指针

把三数之和降维成「固定一个数 + 两数之和」：

```
1. 排序（便于双指针和去重）
2. 固定第一个数 nums[index]
3. 双指针 leftIndex（index+1）、rightIndex（末尾）找 nums[left] + nums[right] = -nums[index]
   - 和太大 → rightIndex--
   - 和太小 → leftIndex++
   - 正好 → 记录，去重后同时收缩
```

**去重是本题关键**，共三处：

- 固定数去重：`nums[index] == nums[index-1]` 时跳过
- 左指针去重：`nums[left] == nums[left+1]` 时 left 右移
- 右指针去重：`nums[right-1] == nums[right]` 时 right 左移

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(n²) — 外层 O(n) × 内层双指针 O(n) |
| 空间 | O(1) — 排序外不额外占用（不计返回结果） |

---

## 代码实现

### Java

```java
public class Leetcode_15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();

        Arrays.sort(nums);
        for (int index = 0; index < nums.length; index++) {
            // 固定数去重
            if (index > 0 && nums[index] == nums[index - 1]) {
                continue;
            }

            int leftIndex = index + 1;
            int rightIndex = nums.length - 1;
            while (leftIndex < rightIndex) {
                int sum = nums[leftIndex] + nums[rightIndex] + nums[index];
                if (sum > 0) {
                    rightIndex--;
                } else if (sum < 0) {
                    leftIndex++;
                } else {
                    resultList.add(Arrays.asList(nums[index], nums[leftIndex], nums[rightIndex]));
                    // 左右指针去重
                    while (leftIndex < rightIndex && nums[rightIndex - 1] == nums[rightIndex]) {
                        rightIndex--;
                    }
                    while (leftIndex < rightIndex && nums[leftIndex] == nums[leftIndex + 1]) {
                        leftIndex++;
                    }
                    rightIndex--;
                    leftIndex++;
                }
            }
        }
        return resultList;
    }
}
```

**写法要点**：
- **先排序**：排序是双指针和去重的前提
- **固定数去重在循环开头**：`nums[index] == nums[index-1]` 跳过，避免同一数字作为固定数重复
- **找到结果后先去重再收缩**：跳过重复的 left/right，然后同时 `rightIndex--; leftIndex++`，避免重复三元组
- **去重时注意边界**：所有 `while` 都要带 `leftIndex < rightIndex` 条件，防止越界

---

## 关键点总结

1. **降维**：三数之和 → 固定一数 + 两数之和（双指针）
2. **排序 + 双指针**：排序后双指针可 O(n) 找两数之和，总复杂度 O(n²)
3. **三处去重**：固定数、左指针、右指针，缺一不可
4. **去重后再收缩**：找到结果后先去重，再同时移动左右指针

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #1 | 两数之和 | 无序数组 + HashMap，O(n) |
| #16 | 最接近的三数之和 | 找最接近 target 的三数之和 |
| #18 | 四数之和 | 固定两个数 + 双指针 |
