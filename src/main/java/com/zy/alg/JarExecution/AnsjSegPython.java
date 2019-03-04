package com.zy.alg.JarExecution;

import com.zy.alg.domain.Result;
import com.zy.alg.domain.Term;
import com.zy.alg.library.StopLibrary;
import com.zy.alg.service.AnsjSeg;
import com.zy.alg.service.AnsjSegImpl;
import com.zy.alg.splitword.DicAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.logging.Log;
import org.nlpcn.commons.lang.util.logging.LogFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangycqupt@163.com
 * @date 2019/02/22 10:56
 */
public class AnsjSegPython {

    private static final Log logger = LogFactory.getLog();

    /**
     * ansj init
     *
     * @return
     */
    public static AnsjSeg init() {
        AnsjSeg ansjSeg = null;
        try {
            ansjSeg = AnsjSegImpl.getSingleton();
        } catch (IOException e) {
            logger.error("load ansj seg model failed." + e);
        }
        return ansjSeg;
    }

    /**
     * init user define dictionary
     *
     * @param path the path of user dic
     * @return
     */
    public static Forest insertUserDic(String path) {
        Forest userDefineDic = new Forest();
        BufferedReader ar;
        try {
            ar = new BufferedReader(new InputStreamReader(new FileInputStream(path),
                    "utf-8"));
            String line;
            while ((line = ar.readLine()) != null) {
                String[] seg = line.split("\t");
                String word = seg[0];
                if (seg.length == 3) {
                    Library.insertWord(userDefineDic, new Value(word, seg[1], seg[2]));
                } else if (seg.length == 2) {
                    Library.insertWord(userDefineDic, new Value(word, seg[1], "2000"));
                } else if (seg.length == 1) {
                    Library.insertWord(userDefineDic, new Value(word, "zbj", "2000"));
                }
            }
            ar.close();
        } catch (IOException e) {
            logger.error("load user dic failed. " + e);
        }
        return userDefineDic;
    }

    /**
     * split methods
     *
     * @param ansjSeg
     * @param text
     * @param type    1-ToAnalysis-distinct 2-ToAnalysis-not distinct 3-indexAnalysis 4-DicAnalysis
     * @return
     */
    public static List<List<String>> textTokenizer(AnsjSeg ansjSeg, String text, String type) {
        Result terms;
        if (type.equals("4")) {
            terms = DicAnalysis.parse(text);
        } else {
            terms = ansjSeg.textTokenizer(text, type);
        }
        List<List<String>> list = new ArrayList<List<String>>();
        for (Term t : terms) {
            List<String> tmpList = new ArrayList<String>();
            tmpList.add(t.getName());
            tmpList.add(t.getNatureStr());
            list.add(tmpList);
        }
        return list;
    }

    /**
     * activate stop dictionary
     *
     * @param ansjSeg
     * @param text
     * @param type    1-ToAnalysis-distinct 2-ToAnalysis-not distinct 3-indexAnalysis 4-DicAnalysis
     * @return
     */
    public static List<List<String>> textTokenizerStop(AnsjSeg ansjSeg, String text, String type) {
        Result terms;
        if (type.equals("4")) {
            terms = DicAnalysis.parse(text).recognition(StopLibrary.get());
        } else {
            terms = ansjSeg.textTokenizer(text, type).recognition(StopLibrary.get());
        }
        List<List<String>> list = new ArrayList<List<String>>();
        for (Term t : terms) {
            List<String> tmpList = new ArrayList<String>();
            tmpList.add(t.getName());
            tmpList.add(t.getNatureStr());
            list.add(tmpList);
        }
        return list;
    }

    /**
     * split methods-add user dic
     *
     * @param ansjSeg
     * @param text
     * @param type    1-ToAnalysis-distinct 2-ToAnalysis-not distinct 3-indexAnalysis 4-DicAnalysis
     * @return
     */
    public static List<List<String>> textTokenizerUser(AnsjSeg ansjSeg, String text, String type,
                                                   Forest... userDic) {
        Result terms;
        if (type.equals("4")) {
            terms = DicAnalysis.parse(text, userDic);
        } else {
            terms = ansjSeg.textTokenizerUser(text, type, userDic);
        }
        List<List<String>> list = new ArrayList<List<String>>();
        for (Term t : terms) {
            List<String> tmpList = new ArrayList<String>();
            tmpList.add(t.getName());
            tmpList.add(t.getNatureStr());
            list.add(tmpList);
        }
        return list;
    }

    /**
     * activate stop dictionary
     *
     * @param ansjSeg
     * @param text
     * @param type    1-ToAnalysis-distinct 2-ToAnalysis-not distinct 3-indexAnalysis 4-DicAnalysis
     * @return
     */
    public static List<List<String>> textTokenizerUserStop(AnsjSeg ansjSeg, String text, String
            type, Forest... userDic) {
        Result terms;
        if (type.equals("4")) {
            terms = DicAnalysis.parse(text, userDic).recognition(StopLibrary.get());
        } else {
            terms = ansjSeg.textTokenizerUser(text, type, userDic).recognition(StopLibrary.get());
        }
        List<List<String>> list = new ArrayList<List<String>>();
        for (Term t : terms) {
            List<String> tmpList = new ArrayList<String>();
            tmpList.add(t.getName());
            tmpList.add(t.getNatureStr());
            list.add(tmpList);
        }
        return list;
    }
}
