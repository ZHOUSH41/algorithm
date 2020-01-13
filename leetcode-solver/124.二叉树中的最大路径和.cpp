/*
 * @lc app=leetcode.cn id=124 lang=cpp
 *
 * [124] 二叉树中的最大路径和
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 */
// #include <iostream>
// using namespace std;
// struct TreeNode {
//     int val;
//     TreeNode *left;
//     TreeNode *right;
//     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
// };
using namespace std;
class Solution {
   public:
    int sum;
    int maxPathSum(TreeNode* root) {
        sum = root->val;
        dfs(root);
        return sum;
    }

    int dfs(TreeNode* node) {
        if (!node) return 0;
        int left_sum = max(dfs(node->left), 0);
        int right_sum = max(dfs(node->right), 0);
        sum = max(sum, left_sum + right_sum + node->val);
        return max(left_sum, right_sum) + node->val;
    }
};
// @lc code=end
