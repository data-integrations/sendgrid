/*
 * Copyright © 2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.cdap.plugin.sendgrid.source.batch;

import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.sendgrid.common.config.BaseConfigValidator;

/**
 * Validates configuration
 */
public class SendGridBatchConfigValidator extends BaseConfigValidator {
  public SendGridBatchConfigValidator(FailureCollector failureCollector, SendGridBatchConfig config) {
    super(failureCollector, config);
  }

  @Override
  public void validate() {
    super.validate();
  }

}
