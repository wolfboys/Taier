/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.taier.develop.service.develop.saver;

import com.dtstack.taier.common.enums.EScheduleJobType;
import com.dtstack.taier.common.enums.ResourceRefType;
import com.dtstack.taier.develop.dto.devlop.TaskResourceParam;
import com.dtstack.taier.develop.dto.devlop.TaskVO;
import com.dtstack.taier.develop.enums.develop.FlinkVersion;
import com.dtstack.taier.develop.service.develop.impl.DevelopTaskResourceService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhichen
 * @Date: 2022/05/29/5:14 PM
 */
@Component
public class FlinkTaskSaver extends AbstractTaskSaver {

    @Autowired
    private DevelopTaskResourceService developTaskResourceService;

    public static Logger LOGGER = LoggerFactory.getLogger(FlinkTaskSaver.class);


    @Override
    public TaskResourceParam beforeProcessing(TaskResourceParam taskResourceParam) {
        taskResourceParam.setTaskParams(taskResourceParam.getTaskParams() == null ? taskTemplateService.getTaskTemplate(taskResourceParam.getTaskType(), FlinkVersion.FLINK_112.getType()).getParams() : taskResourceParam.getTaskParams());
        return taskResourceParam;
    }

    @Override
    public void afterProcessing(TaskResourceParam taskResourceParam, TaskVO taskVO) {
        if (CollectionUtils.isEmpty(taskResourceParam.getResourceIdList())) {
            return;
        }
        developTaskResourceService.save(taskVO, taskResourceParam.getResourceIdList(), ResourceRefType.MAIN_RES.getType());
    }

    @Override
    public List<EScheduleJobType> support() {
        return Lists.newArrayList(EScheduleJobType.FLINK_MR);
    }

}
