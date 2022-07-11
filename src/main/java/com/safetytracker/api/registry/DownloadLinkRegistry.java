package com.safetytracker.api.registry;

import java.util.HashMap;
import java.util.Map;

public class DownloadLinkRegistry {
    public static Map<String, String> DATASET_LINK = new HashMap<>();
    public static Map<String, String> MODEL_LINK = new HashMap<>();

    public static void setup() {
        DATASET_LINK.put("AC", "https://www.dropbox.com/s/7lwv7gpjokc7wt8/AC.arff?dl=1");
        DATASET_LINK.put("AL", "https://www.dropbox.com/s/2gh4qsbik7g27pm/AL.arff?dl=1");
        DATASET_LINK.put("AM", "https://www.dropbox.com/s/mvufpz8lrc1q4eb/AM.arff?dl=1");
        DATASET_LINK.put("AP", "https://www.dropbox.com/s/ow1gi7mozc8tqql/AP.arff?dl=1");
        DATASET_LINK.put("BA", "https://www.dropbox.com/s/5a410wh2600wyut/BA.arff?dl=1");
        DATASET_LINK.put("CE", "https://www.dropbox.com/s/e42hphujwqat488/CE.arff?dl=1");
        DATASET_LINK.put("DF", "https://www.dropbox.com/s/25bwuqmupai7bbs/DF.arff?dl=1");
        DATASET_LINK.put("ES", "https://www.dropbox.com/s/n4t4f0dpipycojt/ES.arff?dl=1");
        DATASET_LINK.put("GO", "https://www.dropbox.com/s/yb0t85vs2v7yb4w/GO.arff?dl=1");
        DATASET_LINK.put("MA", "https://www.dropbox.com/s/5ujjyiii12d6345/MA.arff?dl=1");
        DATASET_LINK.put("MG", "https://drive.google.com/uc?export=download&id=1oAzl9Znx7aVXex0XUqJohqk9HWQLcAb0");
        DATASET_LINK.put("MS", "https://www.dropbox.com/s/6zrd7j6kff2gn9k/MS.arff?dl=1");
        DATASET_LINK.put("MT", "https://www.dropbox.com/s/r7d0rjhv3ajka0a/MT.arff?dl=1");
        DATASET_LINK.put("PA", "https://www.dropbox.com/s/ddp06hyd3qc7d1k/PA.arff?dl=1");
        DATASET_LINK.put("PB", "https://www.dropbox.com/s/3maplav9ymmk2z2/PB.arff?dl=1");
        DATASET_LINK.put("PE", "https://www.dropbox.com/s/efiekgosuhe1rsl/PE.arff?dl=1");
        DATASET_LINK.put("PI", "https://www.dropbox.com/s/z4qew4z3coq5wts/PI.arff?dl=1");
        DATASET_LINK.put("PR", "https://www.dropbox.com/s/8up4ojdai6fph0y/PR.arff?dl=1");
        DATASET_LINK.put("RJ", "https://www.dropbox.com/s/vodnapsfmuihp8e/RJ.arff?dl=1");
        DATASET_LINK.put("RN", "https://www.dropbox.com/s/9yilqnajbf48pxw/RN.arff?dl=1");
        DATASET_LINK.put("RO", "https://www.dropbox.com/s/qetk7z2mgigpemj/RO.arff?dl=1");
        DATASET_LINK.put("RR", "https://www.dropbox.com/s/nui4tvsl0pqkv69/RR.arff?dl=1");
        DATASET_LINK.put("RS", "https://www.dropbox.com/s/eotpfsdgtz1mbj3/RS.arff?dl=1");
        DATASET_LINK.put("SC", "https://www.dropbox.com/s/bedvhcl9msjt8wd/SC.arff?dl=1");
        DATASET_LINK.put("SE", "https://www.dropbox.com/s/c850w8jycyu5zro/SE.arff?dl=1");
        DATASET_LINK.put("SP", "https://www.dropbox.com/s/i3p6tqdu81gif5k/SP.arff?dl=1");
        DATASET_LINK.put("TO", "https://www.dropbox.com/s/bci3isifo0jkxwn/TO.arff?dl=1");

        MODEL_LINK.put("AC", "https://www.dropbox.com/s/k6h7odx9khk0ek6/AC.model?dl=1");
        MODEL_LINK.put("AL", "https://www.dropbox.com/s/hhkaxvyayvo7xmz/AL.model?dl=1");
        MODEL_LINK.put("AM", "https://www.dropbox.com/s/sexgjv63aesl9kl/AM.model?dl=1");
        MODEL_LINK.put("AP", "https://www.dropbox.com/s/irmm73w0gakg8e4/AP.model?dl=1");
        MODEL_LINK.put("BA", "https://www.dropbox.com/s/w8m6na765ljejly/BA.model?dl=1");
        MODEL_LINK.put("CE", "https://www.dropbox.com/s/8ux9izup65gb00q/CE.model?dl=1");
        MODEL_LINK.put("RN", "https://www.dropbox.com/s/476iw4x5eqwdnfe/RN.model?dl=1");
        MODEL_LINK.put("RO", "https://www.dropbox.com/s/5fap7d349d3ferr/RO.model?dl=1");
        MODEL_LINK.put("RR", "https://www.dropbox.com/s/snoxb275vx0v6q9/RR.model?dl=1");
        MODEL_LINK.put("RS", "https://www.dropbox.com/s/ybsti8vsnvn9eos/RS.model?dl=1");
        MODEL_LINK.put("SC", "https://www.dropbox.com/s/bo4ivmt9khdhqcv/SC.model?dl=1");
        MODEL_LINK.put("SE", "https://www.dropbox.com/s/46e032ghtflmvgy/SE.model?dl=1");
        MODEL_LINK.put("SP", "https://www.dropbox.com/s/j0fkf0fi1389k6t/SP.model?dl=1");
        MODEL_LINK.put("TO", "https://www.dropbox.com/s/by8sunp9a50u1yo/TO.model?dl=1");
        MODEL_LINK.put("DF", "https://www.dropbox.com/s/7k0i9oviprc7qy3/DF.model?dl=1");
        MODEL_LINK.put("ES", "https://www.dropbox.com/s/b1ymqqm3c2t42w3/ES.model?dl=1");
        MODEL_LINK.put("GO", "https://www.dropbox.com/s/xhj8m1cd2lvlg05/GO.model?dl=1");
        MODEL_LINK.put("MA", "https://www.dropbox.com/s/j6gvkwmnexfgeb4/MA.model?dl=1");
        MODEL_LINK.put("MS", "https://www.dropbox.com/s/jbngdcxdbnq1k1i/MS.model?dl=1");
        MODEL_LINK.put("MT", "https://www.dropbox.com/s/8lzzvowcb55ib3r/MT.model?dl=1");
        MODEL_LINK.put("PA", "https://www.dropbox.com/s/fuj8jbygrypuaxq/PA.model?dl=1");
        MODEL_LINK.put("PB", "https://www.dropbox.com/s/ephutx2s8j8ukl3/PB.model?dl=");
        MODEL_LINK.put("PI", "https://www.dropbox.com/s/3qnwv18a63vz80s/PI.model?dl=1");
        MODEL_LINK.put("PR", "https://www.dropbox.com/s/i7ils4ec7dyclrl/PR.model?dl=0");
        MODEL_LINK.put("MG", "https://drive.google.com/uc?export=download&id=1o6UHrBuafk_S3AUWvsnQdI6tLA3cn2Qn");
        MODEL_LINK.put("RJ", "https://www.dropbox.com/s/flmkc2j8je6r3mf/RJ.model?dl=1");
        MODEL_LINK.put("PE", "https://www.dropbox.com/s/m25o6cwgrs4owbu/PE.model?dl=1");


    }
}
