package com.zy.alg.JarExecution;

import com.zy.alg.infoextra.service.PositionExtraction;
import com.zy.alg.infoextra.service.PositionExtractionEnhancer;
import com.zy.alg.infoextra.utils.OutputPosiInfo;
import org.nlpcn.commons.lang.util.logging.Log;
import org.nlpcn.commons.lang.util.logging.LogFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author zhangycqupt@163.com
 * @date 2019/02/22 15:52
 */
public class LocationPython {
    private static final Log logger = LogFactory.getLog();

    public static PositionExtraction init(String modelPath) {
        String path = modelPath;
        PositionExtraction pe = null;
        try {
            pe = new PositionExtractionEnhancer(path);
        } catch (IOException e) {
            logger.error("load area model failed." + e);
        }
        return pe;
    }

    /**
     * just area info
     *
     * @param pe
     * @param text
     * @return
     */
    public static String getFormatArea(PositionExtraction pe, String text) {
        List<OutputPosiInfo> posiMap = pe.uniPosiExtra(text);
        String formatArea = "";
        int num = 1;
        for (OutputPosiInfo o : posiMap) {
            if (num > 1) {
                formatArea += "#";
            }
            // 重庆/直辖市/渝
            String privince = o.getPositionName().split("&")[0];
            // 重庆/直辖市
            String prinvinceNew = privince.split("/")[0] + "/" + privince.split("/")[1];
            formatArea += o.getPositionName().replace(privince, prinvinceNew);
            num++;
        }
        return formatArea;
    }

    /**
     * full info
     *
     * @param pe
     * @param text
     * @return
     */
    public static String areaExtract(PositionExtraction pe, String text) {
        List<OutputPosiInfo> posiMap = pe.uniPosiExtra(text);
        String formatArea = "";
        int num = 1;
        for (OutputPosiInfo o : posiMap) {
            if (num > 1) {
                formatArea += "#";
            }
            formatArea += o.getPositionName();
            num++;
        }
        return formatArea;
    }
}
