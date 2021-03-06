/*
 * Copyright © 2020 Cask Data, Inc.
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
package io.cdap.plugin.sendgrid.common.objects.mail;

import com.google.gson.annotations.SerializedName;

/**
 * Tracking settings.
 */
public class SendGridTrackingSettings {
  @SerializedName("click_tracking")
  private SendGridSwitch clickTracking;

  @SerializedName("open_tracking")
  private SendGridSwitch openTracking;

  @SerializedName("subscription_tracking")
  private SendGridSwitch subscriptionTracking;

  /**
   * Constructor for SendGridTrackingSettings object.
   * @param clickTracking the click tracking
   * @param openTracking the open tracking
   * @param subscriptionTracking the subscription tracking
   */
  public SendGridTrackingSettings(SendGridSwitch clickTracking, SendGridSwitch openTracking,
                                  SendGridSwitch subscriptionTracking) {
    this.clickTracking = clickTracking;
    this.openTracking = openTracking;
    this.subscriptionTracking = subscriptionTracking;
  }

  public SendGridSwitch getClickTracking() {
    return clickTracking;
  }

  public SendGridSwitch getOpenTracking() {
    return openTracking;
  }

  public SendGridSwitch getSubscriptionTracking() {
    return subscriptionTracking;
  }
}
