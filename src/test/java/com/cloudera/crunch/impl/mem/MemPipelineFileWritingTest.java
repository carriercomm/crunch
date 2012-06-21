/**
 * Copyright (c) 2012, Cloudera, Inc. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */
package com.cloudera.crunch.impl.mem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import com.cloudera.crunch.PCollection;
import com.cloudera.crunch.Pipeline;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

import org.junit.Test;

public class MemPipelineFileWritingTest {
  @Test
  public void testMemPipelineFileWriter() throws Exception {
    File tmpDir = Files.createTempDir();
    tmpDir.delete();
    Pipeline p = MemPipeline.getInstance();
    PCollection<String> lines = MemPipeline.collectionOf("hello", "world");
    p.writeTextFile(lines, tmpDir.getAbsolutePath());
    p.done();
    assertTrue(tmpDir.exists());
    File[] files = tmpDir.listFiles();
    assertTrue(files != null && files.length > 0);
    for (File f : files) {
      if (!f.getName().startsWith(".")) {
        List<String> txt = Files.readLines(f, Charsets.UTF_8);
        assertEquals(ImmutableList.of("hello", "world"), txt);
      }
    }
  }
}
