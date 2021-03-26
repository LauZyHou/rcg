// 合成构型的输出结点
public class FusionNode extends ConvNode {
    // 合成类型：相互促进、不促进
    public enum FusionType {
        PROMOTE, NOPROMOTE
    }

    public FusionType type;

    public FusionNode(String name, FusionType type) {
        super(name);
        this.type = type;
    }

    // 计算输出值
    @Override
    public void cal_out_val() {
        // 不促进，取min
        if (type == FusionType.NOPROMOTE) {
            out_val = pre_vals.get(0);
            for (double val :
                    pre_vals) {
                out_val = Math.min(out_val, val);
            }
        }
        // 促进，取平均
        else if (type == FusionType.PROMOTE) {
            out_val = 0;
            for (double val :
                    pre_vals) {
                out_val += val;
            }
            out_val /= pre_vals.size();
        }
    }
}
