package org.noear.solon.extend.luffy.impl;

import org.noear.luffy.model.AFileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author noear
 * @since 2.6
 */
public class JtFunctionLoaderManager implements JtFunctionLoader {
    private final List<JtFunctionLoader> resouceLoaders = new ArrayList<>();

    public void add(int index, JtFunctionLoader resouceLoader) {
        resouceLoaders.add(index, resouceLoader);
    }

    public void clear(){
        resouceLoaders.clear();
    }

    @Override
    public AFileModel fileGet(String path) throws Exception {
        AFileModel aFile = null;
        for (JtFunctionLoader rl : resouceLoaders) {
            aFile = rl.fileGet(path);

            if (aFile != null) {
                return aFile;
            }
        }
        return null;
    }
}
