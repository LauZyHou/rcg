import java.util.LinkedList;

// 汇聚结点抽象类，汇聚结点一定是一个虚拟点
public abstract class ConvNode extends VirtualNode {
    public LinkedList<Double> pre_vals; // 存所有前件结点计算出的取值

    public ConvNode(String name) {
        super(name);
        pre_vals = new LinkedList<>();
    }

    // 汇聚结点单独计算输出值
    public abstract void cal_out_val();
}
