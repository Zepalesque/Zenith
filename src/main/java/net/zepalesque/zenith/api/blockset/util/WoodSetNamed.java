package net.zepalesque.zenith.api.blockset.util;

import javax.annotation.Nullable;

public interface WoodSetNamed {

    public String logSuffix(LangType type);

    public String woodSuffix(LangType type);

    public String treesName(LangType type);

    public String processName(String s);

    public static enum LangType {
        ID, LANG, LANG_PLURAL
    }
}
