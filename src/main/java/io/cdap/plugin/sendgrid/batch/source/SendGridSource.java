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
package io.cdap.plugin.sendgrid.batch.source;

import com.google.common.base.Preconditions;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.batch.Input;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.dataset.lib.KeyValue;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.batch.BatchSource;
import io.cdap.cdap.etl.api.batch.BatchSourceContext;
import io.cdap.plugin.common.IdUtils;
import io.cdap.plugin.common.LineageRecorder;
import io.cdap.plugin.sendgrid.common.config.BaseConfig;
import io.cdap.plugin.sendgrid.common.helpers.IBaseObject;
import org.apache.hadoop.io.NullWritable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Batch Source plugin
 */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name(BaseConfig.PLUGIN_NAME)
@Description("Reads data from SendGrid API")
public class SendGridSource extends BatchSource<NullWritable, IBaseObject, StructuredRecord> {
  private final SendGridSourceConfig config;

  public SendGridSource(SendGridSourceConfig config) {
    this.config = config;
  }

  @Override
  public void prepareRun(BatchSourceContext batchSourceContext) {
    validateConfiguration(batchSourceContext.getFailureCollector());

    LineageRecorder lineageRecorder = new LineageRecorder(batchSourceContext, config.referenceName);
    lineageRecorder.createExternalDataset(config.getSchema());
    lineageRecorder.recordRead("Read", "Reading SendGrid Objects",
      Preconditions.checkNotNull(config.getSchema().getFields())
        .stream()
        .map(Schema.Field::getName)
        .collect(Collectors.toList()));

    batchSourceContext.setInput(Input.of(config.referenceName, new SendGridInputFormatProvider(config)));
  }

  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) {
    FailureCollector failureCollector = pipelineConfigurer.getStageConfigurer().getFailureCollector();

    IdUtils.validateReferenceName(config.referenceName, failureCollector);
    validateConfiguration(failureCollector);
    pipelineConfigurer.getStageConfigurer().setOutputSchema(config.getSchema());
  }

  @Override
  public void transform(KeyValue<NullWritable, IBaseObject> input, Emitter<StructuredRecord> emitter) {
    Schema schema;
    if (config.isMultiObjectMode()) {
      List<String> fetchedDataSources = new ArrayList<>(input.getValue().asMap().keySet());
      schema = config.getSchema(fetchedDataSources);
    } else {
      schema = config.getSchema();
    }
    emitter.emit(SendGridSourceTransformer.transform(input.getValue(), schema));
  }

  @SuppressWarnings("ThrowableNotThrown")
  private void validateConfiguration(FailureCollector failureCollector) {
    config.validate(failureCollector);
    failureCollector.getOrThrowException();
  }

}
