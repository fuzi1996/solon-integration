package org.noear.solon.extend.luffy.impl;

import org.noear.luffy.model.AFileModel;
import org.noear.solon.Solon;
import org.noear.solon.core.util.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * @author noear
 * @since 1.3
 */
public class JtResouceLoaderFile implements JtResouceLoader {
    static final Logger log = LoggerFactory.getLogger(JtResouceLoaderFile.class);

    private File _baseDir;

    public JtResouceLoaderFile() {
        this("./luffy/");
    }

    public JtResouceLoaderFile(String baseDir) {
        _baseDir = new File(baseDir);
        if (!_baseDir.exists()) {
            _baseDir.mkdir();
        }
    }

    @Override
    public AFileModel fileGet(String path) throws Exception {
        AFileModel file = new AFileModel();

        file.content = fileContentGet(path);
        if (file.content != null) {
            //如果有找到文件内容，则完善信息
            //
            File file1 = new File(path);
            String fileName = file1.getName();

            file.path = path;
            file.tag = "luffy";

            if (fileName.indexOf('.') > 0) {
                String suffix = fileName.substring(fileName.indexOf('.') + 1);
                file.edit_mode = JtMapping.getActuator(suffix);
            } else {
                file.edit_mode = JtMapping.getActuator("");
            }
        }

        return file;
    }

    protected String fileContentGet(String path) {
        if (_baseDir == null) {
            return null;
        } else {
            File file = new File(_baseDir, path);

            if (file.exists()) {
                try {
                    try (InputStream ins = new FileInputStream(file)) {
                        return IoUtil.transferToString(ins, Solon.encoding());
                    }
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}
