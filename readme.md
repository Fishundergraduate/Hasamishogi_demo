#はさみ将棋 DFS/BFS実装AI

Javaで実装しました．

DFSで探索爆発が起きそうだったので異常なまでに制限してます．
---
1. 盤上の駒のうちランダムに１つを選択し，縦横３マス幅で相手駒を探しそこの付近に移動させます．
2. 1が達成できない場合，ランダムに１つ選択して１マス動かします．

実装していないはさみ将棋のルールがあります
---
1. 四隅周りで一方の駒をもう片方の駒で囲むとその駒を得ることができますがこのプログラムではできません．

---
既知のバグです
1. １一から一定確率で相手駒「と」が生成されます．
2. 不明な文字が表示されます．

Written by Sakana https://twitter.com/xeT1T
© 2020 Sakana