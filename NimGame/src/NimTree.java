public class NimTree {
	private NimTreeNode root;

	public NimTree(int coins) {
		this.root = new NimTreeNode(coins, 1);
	}

	public int getCurrentCoins() {
		return root.coins;
	}

	public int getWinner() {
		if (root.coins != 0)
			return 0;
		return root.player;
	}

	public int nextBestMove() {
		return root.getBestMove();
	}

	public void removeOne() {
		root = root.oneLess;
	}

	public void removeTwo() {
		root = root.twoLess;
	}

	public int getHeight() {
		return getHeight(root);
	}

	public boolean canRemoveTwo() {
		return root.twoLess != null;
	}

	private int getHeight(NimTreeNode node) {
		if (node == null)
			return 0;
		int lH = getHeight(node.oneLess);
		int rH = getHeight(node.twoLess);
		return Math.max(lH, rH) + 1;

	}

	public int getNumNodes() {
		return getNumNodes(root);
	}

	private int getNumNodes(NimTreeNode node) {
		if (node == null)
			return 0;
		int lN = getNumNodes(node.oneLess);
		int rN = getNumNodes(node.twoLess);
		return lN + rN + 1;

	}

	private class NimTreeNode {

		int coins;
		int player;
		private NimTreeNode oneLess;
		private NimTreeNode twoLess;

		public NimTreeNode(int coins, int player) {
			this.coins = coins;
			this.player = player;
			if (coins - 1 >= 0) {
				oneLess = new NimTreeNode(coins - 1, -player);
				if (coins - 2 >= 0)
					twoLess = new NimTreeNode(coins - 2, -player);

			}
		}

		private int MinMax(NimTreeNode node) {
			if (node.coins == 0)
				return node.player;
			if (node.player == -1) {
				int cur = MinMax(node.oneLess);
				if (node.twoLess != null)
					cur = Math.max(cur, MinMax(node.twoLess));
				return cur;
			} else {
				int cur = MinMax(node.oneLess);
				if (node.twoLess != null)
					cur = Math.min(cur, MinMax(node.twoLess));
				return cur;
			}
		}

		private int getBestMove() {
			if (player == 1) {
				if (MinMax(oneLess) > MinMax(twoLess))
					return 2;
				return 1;
			} else {
				if (MinMax(oneLess) < MinMax(twoLess))
					return 2;
				return 1;
			}

		}

	}

}