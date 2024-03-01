import java.util.Scanner;

class AVLNode {
    int value;
    AVLNode left;
    AVLNode right;
    int height;

    public AVLNode(int value) {
        this.value = value;
        this.height = 1;
    }
}
  
class AVLTree {
    AVLNode root;

    public void insert(int value) {
        root = insertHelper(root, value);
    }

    private AVLNode insertHelper(AVLNode node, int value) {
        if (node == null) {
            return new AVLNode(value);
        }

        if (value < node.value) {
            node.left = insertHelper(node.left, value);
        } else if (value > node.value) {
            node.right = insertHelper(node.right, value);
        } else {
            return node; // Value ga boleh sama
        }

        // Update height
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Check balance factor node
        int balance = countBalanceFactor(node);

        // Jenis rotasi kalau tree tidak balance :
        // Rotasi Left Left
        if (balance > 1 && value < node.left.value) {
            return rotateRight(node);
        }
        // Rotasi Right Right
        if (balance < -1 && value > node.right.value) {
            return rotateLeft(node);
        }
        // Rotasi Left Right
        if (balance > 1 && value > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // Rotasi Right Left
        if (balance < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private int height(AVLNode node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    public void getLevel() {
        System.out.println("Jumlah Level : " + height(root));
        System.out.println();
    }

    private int countBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }

        //Return balance
        return height(node.left) - height(node.right); //Kalo result negatif berarti ga stabil di kanan dan sebaliknya
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode newRoot = node.left;
        AVLNode temp = newRoot.right;

        // Rotasi
        newRoot.right = node;
        node.left = temp;

        // Update heights
        node.height = 1 + Math.max(height(node.left), height(node.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));

        return newRoot;
    }

    private AVLNode rotateLeft(AVLNode node) {
        AVLNode newRoot = node.right;
        AVLNode temp = newRoot.left;

        // Rotasi
        newRoot.left = node;
        node.right = temp;

        // Update height
        node.height = 1 + Math.max(height(node.left), height(node.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));

        return newRoot;
    }

    // Preorder traversal
    void printPreorder(AVLNode node) {
        if (node == null)
            return;

        System.out.print(node.value + " ");
        printPreorder(node.left);
        printPreorder(node.right);
    }

    // Inorder traversal
    void printInorder(AVLNode node) {
        if (node == null)
            return;

        printInorder(node.left);
        System.out.print(node.value + " ");
        printInorder(node.right);
    }

    // Postorder traversal
    void printPostorder(AVLNode node) {
        if (node == null)
            return;

        printPostorder(node.left);
        printPostorder(node.right);
        System.out.print(node.value + " ");
    }

    private void printGivenLevel(AVLNode root, int level) {
        if (root == null)
            return;
        if (level == 1)
            System.out.print(root.value + " ");
        else if (level > 1) {
            printGivenLevel(root.left, level - 1);
            printGivenLevel(root.right, level - 1);
        }
    }

    public void inorderTraversal() {
        System.out.println("Inorder Traversal : ");
        printInorder(root);
        System.out.println();
        System.out.println();
    }

    public void postorderTraversal() {
        System.out.println("Postorder Traversal : ");
        printPostorder(root);
        System.out.println();
        System.out.println();
    }

    public void preorderTraversal() {
        System.out.println("Preorder Traversal : ");
        printPreorder(root);
        System.out.println();
        System.out.println();
    }

    // Level order traversal
    public void levelorderTraversal() {
        int h = height(root);
        System.out.println("Levelorder Traversal : ");
        for (int i = 1; i <= h; i++)
            printGivenLevel(root, i);
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        AVLTree avl = new AVLTree();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine())
        {
            String in = sc.nextLine();
            String[] splits = in.split(" ");
            if (splits[0].toLowerCase().equals("insert")) {
                avl.insert( Integer.parseInt(splits[1]) );
            } else if (splits[0].toLowerCase().equals("print")) {
                switch (splits[1].toLowerCase()) {
                    case "preorder" : avl.preorderTraversal(); break;
                    case "inorder" : avl.inorderTraversal(); break;
                    case "postorder" : avl.postorderTraversal(); break;
                    case "levels" : avl.getLevel(); break;
                }
            } else if (splits[0].toLowerCase().equals("exit")) {
                break;
            }
        }

    }
}