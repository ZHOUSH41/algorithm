#include <algorithm>
#include <queue>
#include <vector>
using std::queue;
using std::reverse;
using std::vector;
/**
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. 
 * (ie, from left to right, then right to left for the next level and alternate between).
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
 
 return its zigzag level order traversal as:
[
  [3],
  [20,9],
  [15,7]
]
*/
struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};
class Solution {
   public:
    // 思路：广度搜索有先的，用队列实现，然后计数 ,这是自己的解法
    vector<vector<int>> zigzagLevelOrder(TreeNode *root) {
        if (!root) return {};
        vector<int> cur;
        vector<vector<int>> res;
        queue<TreeNode *> q;
        q.push(root);
        int count = 1;
        int pre_count;
        int zig_count = 1;
        while (!q.empty()) {
            pre_count = count;
            count = 0;
            while (pre_count != 0) {
                TreeNode *tmp = q.front();
                q.pop();  //?删除栈顶元素之后呢
                cur.push_back(tmp->val);
                // 向队列里面添加元素
                if (tmp->left != NULL) {
                    q.push(tmp->left);
                    count++;
                }
                if (tmp->right != NULL) {
                    q.push(tmp->right);
                    count++;
                }
                pre_count--;
            }
            // 反转就行
            if (zig_count % 2 == 0) {
                std::reverse(cur.begin(), cur.end());
            }
            res.push_back(cur);
            cur.clear();  // 清除内容
            zig_count++;
        }
        return res;
    }
    vector<vector<int>> zigzagLevelOrder_better_code(TreeNode *root) {
        if (!root) return {};
        vector<vector<int>> res;
        queue<TreeNode *> q;
        int cnt = 0;
        while (!q.empty()) {
            vector<int> oneLevel;
            for (int i = q.size(); i > 0; --i) {
                TreeNode *t = q.front();
                q.pop();
                oneLevel.push_back(t->val);
                if (t->left) q.push(t->left);
                if (t->right) q.push(t->right);
            }
            if (cnt % 2 == 1) reverse(oneLevel.begin(), oneLevel.end());
            res.push_back(oneLevel);
            ++cnt;
        }
    }
};