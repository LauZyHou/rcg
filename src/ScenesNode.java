// 场景结点
public class ScenesNode extends Node {
    // 场景判别器概率p，每个场景可靠性si，出现概率qi
    public ScenesNode(String name, double p, double[] s, double[] q) {
        super(name, p);
        assert s.length == q.length;
        int k = s.length; // 场景数量
        double sum = 0; // 计算期望概率
        for (int i = 0; i < k; i ++ ) {
            sum += s[i] * q[i];
        }
        out_val = p * sum; // 乘以判别器概率即为输出概率
    }
}
