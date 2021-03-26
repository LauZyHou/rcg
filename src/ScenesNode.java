// 场景结点
public class ScenesNode extends Node {
    // 每个场景可靠性si，出现概率qi
    public ScenesNode(String name, double[] s, double[] q) {
        super(name, 1);
        assert s.length == q.length;
        int k = s.length; // 场景数量
        double sum = 0; // 计算期望概率
        for (int i = 0; i < k; i ++ ) {
            sum += s[i] * q[i];
        }
        self_val = sum; // sum即为自身概率
    }
}
