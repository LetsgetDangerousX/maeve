INSERT pseudocode
When inserting a number:

If root is full (63 keys):
a. Split the root into two nodes.
b. Promote the middle key to a new root.
c. Continue inserting into the correct child node.

If root is not full:
a. Traverse down the tree.
b. Always make sure we're inserting into a non-full node.

If a child about to be inserted into is full:
a. Split that child before going deeper.

Splitting pushes a key up to the parent, and divides the full node into two half-full nodes.

SPLIT pseudocode
When splitting a node:

Create a new sibling node.

Move the right half of the keys from the full node into the sibling.

Promote the middle key up to the parent.

If the split node had children, split children between the nodes too.

Insert the new sibling into the parent's list of children.

REMOVE pseudocode
When removing a number:

Search for the key like normal.

If the key is in a leaf node:
a. Just delete it.

If the key is in an internal node:
a. Find its successor or predecessor.
b. Replace the key with its successor.
c. Delete the successor.

Before going down into a child during delete:

If the child has only minimum keys, you must merge or borrow to guarantee you don't underflow.

Merge with sibling if needed.

GET pseudocode
Finding a number:

Start at root.

Search within the current node (binary search).

If not found, follow the correct child pointer.

Repeat until you find it or hit a null leaf.

MAX/MIN DEPTH pseudocode
Traverse the tree from root to every leaf.

Track how many edges traveled.

Max depth = deepest leaf path.

Min depth = shallowest leaf path.