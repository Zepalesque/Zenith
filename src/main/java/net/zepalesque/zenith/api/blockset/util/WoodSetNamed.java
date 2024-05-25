package net.zepalesque.zenith.api.blockset.util;

import javax.annotation.Nullable;

public interface WoodSetNamed {

    public String logSuffix(LangType type);

    public String woodSuffix(LangType type);

    public String treesName(boolean isPlural);

    public static enum LangType {
        ID, PLURAL, LANG, LANG_PLURAL
    }
}
