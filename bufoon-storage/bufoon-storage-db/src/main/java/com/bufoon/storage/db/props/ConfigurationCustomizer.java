package com.bufoon.storage.db.props;

import org.apache.ibatis.session.Configuration;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/4/7 21:28
 * @Desc: as follows.
 */
public interface ConfigurationCustomizer {

    /**
     * Customize the given a {@link Configuration} object.
     *
     * @param configuration the configuration object to customize
     */
    void customize(Configuration configuration);

}
