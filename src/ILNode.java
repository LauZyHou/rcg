// 集成学习结点
public final class ILNode extends Node {
    public ILNode(String name, double[] inner_vals) {
        super(name, 0);
        // 取最大值作为自身的值
        for (double val :
                inner_vals) {
            this.self_val = Math.max(this.self_val, val);
        }
    }
}
