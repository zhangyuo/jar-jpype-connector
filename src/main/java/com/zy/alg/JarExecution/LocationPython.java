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

    public static String getFormatArea(PositionExtraction pe, String text) {
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
