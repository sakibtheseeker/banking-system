// Node for AVL Tree
package System;

public class AVLTree {
    private Node root;

    private static class Node {
        BankAccount account;
        Node left, right;
        int height;

        public Node(BankAccount account) {
            this.account = account;
            this.height = 1;
        }
    }

    public AVLTree() {
        root = null;
    }

    // Insert method and balancing code here...
    public void insertAccount(BankAccount account) {
        root = insert(root, account);
    }

    private Node insert(Node node, BankAccount account) {
        if (node == null) return new Node(account);

        // Insert logic
        if (account.getAccountNumber() < node.account.getAccountNumber()) {
            node.left = insert(node.left, account);
        } else if (account.getAccountNumber() > node.account.getAccountNumber()) {
            node.right = insert(node.right, account);
        } else {
            return node;
        }

        // Update height and balance tree
        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && account.getAccountNumber() < node.left.account.getAccountNumber()) {
            return rightRotate(node);
        }

        if (balance < -1 && account.getAccountNumber() > node.right.account.getAccountNumber()) {
            return leftRotate(node);
        }

        if (balance > 1 && account.getAccountNumber() > node.left.account.getAccountNumber()) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && account.getAccountNumber() < node.right.account.getAccountNumber()) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Method to display accounts
    public void displayAccounts(Node node) {
        if (node != null) {
            displayAccounts(node.left);
            System.out.println("Account Number: " + node.account.getAccountNumber() +
                    " | Name: " + node.account.getName() +
                    " | Balance: " + node.account.getBalance());
            displayAccounts(node.right);
        }
    }

    public void displayAllAccounts() {
        displayAccounts(root);
    }
}
