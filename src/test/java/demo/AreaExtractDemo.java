package demo;

import com.zy.alg.infoextra.service.PositionExtraction;
import com.zy.alg.infoextra.service.PositionExtractionEnhancer;
import com.zy.alg.infoextra.utils.OutputPosiInfo;

import java.io.IOException;
import java.util.List;

/**
 * @author zhangycqupt@163.com
 * @date 2019/03/05 14:02
 */
public class AreaExtractDemo {
    public static void main (String[] args) throws IOException {
        String path = "G:\\project\\知识图谱2.0\\";
        PositionExtraction pe = new PositionExtractionEnhancer(path);
        String query = "綦江和渝中区都是一个好地方";
        List<OutputPosiInfo> posiMap = pe.uniPosiExtra(query);
        for (OutputPosiInfo o : posiMap){
            System.out.println("地域：" + o.getPositionName() + "\t" + o.getScore());
        }
    }
}
