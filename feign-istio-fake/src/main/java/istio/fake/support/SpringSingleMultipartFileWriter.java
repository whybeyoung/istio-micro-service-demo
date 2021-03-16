/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package istio.fake.support;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import istio.fake.FakeException;
import lombok.val;

/**
 *
 * @author Artem Labazin
 */
public class SpringSingleMultipartFileWriter extends AbstractWriter {

  @Override
  public boolean isApplicable (Object value) {
    return value instanceof MultipartFile;
  }

  @Override
  protected void write (Output output, String key, Object value) throws FakeException {
    val file = (MultipartFile) value;
    writeFileMetadata(output, key, file.getOriginalFilename(), file.getContentType());

    byte[] bytes;
    try {
      bytes = file.getBytes();
    } catch (IOException ex) {
      throw new FakeException("Getting multipart file's content bytes error", ex);
    }
    output.write(bytes);
  }
}
