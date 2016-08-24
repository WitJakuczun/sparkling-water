/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.spark.h2o.util

import org.apache.spark.SparkContext
import org.apache.spark.h2o.H2OContext
import org.apache.spark.sql.SparkSession
import org.scalatest.Suite

/** This fixture create a Spark context once and share it over whole run of test suite. */
trait SharedSparkTestContext extends SparkTestContext { self: Suite =>

  def createSparkContext:SparkContext
  def createH2OContext(sc:SparkContext):H2OContext = H2OContext.getOrCreate(sc)

  override def beforeAll(): Unit = {
    super.beforeAll()
    sc = createSparkContext
    sqlc = SparkSession.builder().getOrCreate().sqlContext
    hc = createH2OContext(sc)
  }

  override protected def afterAll(): Unit = {
    resetContext()
    super.afterAll()
  }
}
