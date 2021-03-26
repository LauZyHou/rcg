import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        /*
         * Apollo系统架构模型
         * */
        // 声明结点
        VirtualNode camera =
                new VirtualNode("camera");
        VirtualNode imageProcess =
                new VirtualNode("imageProcess");
        PureNode trafficLightDetection =
                new PureNode("trafficLightDetection",
                        0.99);
        ScenesNode trafficLightRecognition =
                new ScenesNode("trafficLightRecognition",
                        new double[]{0.992, 0.994, 0.995},
                        new double[]{0.65, 0.25, 0.1});
        ILNode laneDetection =
                new ILNode("laneDetection",
                        new double[]{0.97, 0.95});
        PureNode cameraObstacleDetection =
                new PureNode("cameraObstacleDetection",
                        0.96);
        VirtualNode liDAR = new VirtualNode("liDAR");
        ScenesNode liDARObstacleDetection =
                new ScenesNode("liDARObstacleDetection",
                        new double[]{0.94, 0.96, 0.98},
                        new double[]{0.45, 0.25, 0.30});
        PureNode radarDetectionResult =
                new PureNode("radarDetectionResult", 0.992);
        FusionNode cameraPostprocess =
                new FusionNode("cameraPostprocess",
                        FusionNode.FusionType.NOPROMOTE);
        FusionNode fusion =
                new FusionNode("fusion",
                        FusionNode.FusionType.PROMOTE);
        FusionNode predictionContainer =
                new FusionNode("predictionContainer",
                        FusionNode.FusionType.NOPROMOTE);
        // 加边
        camera.addNext(imageProcess);
        imageProcess.addNext(trafficLightDetection);
        imageProcess.addNext(laneDetection);
        imageProcess.addNext(cameraObstacleDetection);
        trafficLightDetection.addNext(trafficLightRecognition);
        trafficLightRecognition.addNext(predictionContainer);
        laneDetection.addNext(predictionContainer);
        laneDetection.addNext(cameraPostprocess);
        cameraObstacleDetection.addNext(cameraPostprocess);
        cameraPostprocess.addNext(fusion);
        liDAR.addNext(liDARObstacleDetection);
        liDARObstacleDetection.addNext(fusion);
        radarDetectionResult.addNext(fusion);
        fusion.addNext(predictionContainer);
        // 拓扑排序计算结果
        Queue<Node> q = new LinkedList<>();
        q.offer(camera);
        q.offer(liDAR);
        q.offer(radarDetectionResult);
        while (!q.isEmpty()) {
            Node front = q.poll();
            System.out.println("当前是 " + front.name + " " + front.out_val);
            // 对所有后继结点
            for (Node nd :
                    front.nexts) {
                // 入度减少
                nd.du--;
                // 合成类结点，将当前front点的输出值添加进来
                if (nd instanceof ConvNode) {
                    ConvNode convNode = (ConvNode) nd;
                    convNode.pre_vals.add(front.out_val);
                    // 如果入度已经是0了，要计算一下out_val然后入队
                    if (convNode.du == 0) {
                        convNode.cal_out_val();
                        q.offer(convNode);
                        System.out.println("\t" + convNode.name + " " + convNode.out_val);
                    }
                }
                // 非合成类结点，实际上就只有这一个前驱，乘一下可靠度就是输出可靠度
                else {
                    nd.out_val = front.out_val * nd.self_val;
                    // 如果入度已经是0了，也要入队
                    if (nd.du == 0) {
                        q.offer(nd);
                        System.out.println("\t" + nd.name + " " + nd.out_val);
                    }
                }
            }
        }
        // 输出关心的最终位置的结果
        // System.out.println(predictionContainer.out_val);
    }
}
