/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.common.tracer.core.appender.self;

import com.alipay.common.tracer.core.appender.TracerLogRootDeamon;
import com.alipay.common.tracer.core.base.AbstractTestBase;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * SelfLog Tester.
 *
 * @author <guanchao.ygc>
 * @version 1.0
 * @since <pre>七月 26, 2017</pre>
 */
public class SelfLogTest extends AbstractTestBase {

    @Before
    public void before() throws Exception {
        File file = new File(logDirectoryPath + File.separator + SelfLog.SELF_LOG_FILE);
        if (file.exists()) {
            FileUtils.writeStringToFile(file, "");
        }
    }

    @After
    public void after() throws Exception {
        Thread.sleep(3000);
        File file = new File(logDirectoryPath + File.separator + SelfLog.SELF_LOG_FILE);
        if (file.exists()) {
            FileUtils.writeStringToFile(file, "");
        }
    }

    /**
     * Method: error(String log, Throwable e)
     */
    @Test
    public void testErrorForLogE() throws Exception {
        SelfLog.error("Error info", new RuntimeException("RunTimeException"));
        Thread.sleep(4000);

        List<String> logs = FileUtils.readLines(new File(TracerLogRootDeamon.LOG_FILE_DIR
                                                         + File.separator + SelfLog.SELF_LOG_FILE));
        assertTrue(logs.size() > 0);
    }

    /**
     * Method: errorWithTraceId(String log, Throwable e)
     */
    @Test
    public void testErrorWithTraceIdForLogE() throws Exception {
        SelfLog.errorWithTraceId("error Info ", "traceid");
        Thread.sleep(4000);
        List<String> logs = FileUtils.readLines(new File(TracerLogRootDeamon.LOG_FILE_DIR
                                                         + File.separator + SelfLog.SELF_LOG_FILE));
        assertTrue(logs.toString(), logs.size() > 0);
    }

    /**
     * Method: error(String log)
     */
    @Test
    public void testErrorLog() throws Exception {
        SelfLog.error("Error info");
        SelfLog.error("Error", new RuntimeException("error"));

        Thread.sleep(4000);

        List<String> logs = FileUtils.readLines(new File(TracerLogRootDeamon.LOG_FILE_DIR
                                                         + File.separator + SelfLog.SELF_LOG_FILE));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < logs.size(); i++) {
            stringBuilder.append("\n" + logs.get(i));
        }
        assertTrue(stringBuilder.toString(), logs.size() > 0);
    }
}
