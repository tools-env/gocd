/*************************GO-LICENSE-START*********************************
 * Copyright 2014 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.go.config.validation;

import com.thoughtworks.go.config.EnvironmentsConfig;
import com.thoughtworks.go.config.CruiseConfig;

/**
 * @understands guarding against unknown pipeline under environments
 */
public class EnvironmentPipelineValidator implements GoConfigValidator {
    public void validate(CruiseConfig cruiseConfig) {
        EnvironmentsConfig environments = cruiseConfig.getEnvironments();
        environments.validateContainOnlyPiplines(cruiseConfig.getAllPipelineNames());
    }
}
