import java.util.LinkedList;

// 抽象的结点类
public abstract class Node {
    public int du; // 入度
    public String name;
    public double self_val; // 结点本身取值
    protected double out_val; // 可变的输出值
    public LinkedList<Node> nexts;

    public Node(String name, double self_val) {
        this.du = 0;
        this.name = name;
        this.out_val = this.self_val = self_val;
        this.nexts = new LinkedList<>();
    }

    // 添加后件结点
    public void addNext(Node n) {
        if (n == null) return;
        n.du++;
        this.nexts.add(n);
    }
}
