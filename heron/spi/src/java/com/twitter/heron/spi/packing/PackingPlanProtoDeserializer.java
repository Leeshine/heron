// Copyright 2016 Twitter. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.twitter.heron.spi.packing;

import java.util.HashMap;
import java.util.Map;

import com.twitter.heron.proto.system.PackingPlans;

/**
 * Converts to a com.twitter.heron.spi.packing.PackingPlan object it's protobuf equivalent
 */
public class PackingPlanProtoDeserializer {

  public PackingPlan fromProto(PackingPlans.PackingPlan packingPlan) {
    Map<String, PackingPlan.ContainerPlan> containers = new HashMap<>();
    for (PackingPlans.ContainerPlan containerPlan : packingPlan.getContainerPlansList()) {
      containers.put(containerPlan.getId(), convert(containerPlan));
    }

    return new PackingPlan(
        packingPlan.getId(),
        containers,
        convert(packingPlan.getResource()));
  }

  private PackingPlan.ContainerPlan convert(PackingPlans.ContainerPlan containerPlan) {
    Map<String, PackingPlan.InstancePlan> instances = new HashMap<>();
    for (PackingPlans.InstancePlan instancePlan : containerPlan.getInstancePlansList()) {
      instances.put(instancePlan.getId(), convert(instancePlan));
    }

    return new PackingPlan.ContainerPlan(
        containerPlan.getId(),
        instances,
        convert(containerPlan.getResource()));
  }

  private PackingPlan.InstancePlan convert(PackingPlans.InstancePlan instancePlan) {
    return new PackingPlan.InstancePlan(
        instancePlan.getId(),
        instancePlan.getComponentName(),
        convert(instancePlan.getResource()));
  }

  private Resource convert(PackingPlans.Resource resource) {
    return new Resource(resource.getCpu(), resource.getRam(), resource.getDisk());
  }
}
